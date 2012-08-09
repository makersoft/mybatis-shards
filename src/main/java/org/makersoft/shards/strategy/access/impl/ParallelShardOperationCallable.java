/*
 * @(#)ParallelShardOperationCallable.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.access.impl;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.makersoft.shards.Shard;
import org.makersoft.shards.ShardId;
import org.makersoft.shards.ShardOperation;
import org.makersoft.shards.strategy.exit.ExitStrategy;

/**
 * Runs a single operation on a single shard, collecting the result of the
 * operation with an ExitStrategy.  The interesting bit here is that
 * if the ExitStrategy indicates that there is no more work to be performed,
 * this object has the ability to cancel the work being performed by all the
 * other threads.
 *
 */
class ParallelShardOperationCallable<T> implements Callable<Void> {

  private static final boolean INTERRUPT_IF_RUNNING = false;

  private final Log log = LogFactory.getLog(getClass());

  private final CountDownLatch startSignal;

  private final CountDownLatch doneSignal;

  private final ExitStrategy<T> exitStrategy;

  private final ShardOperation<T> operation;

  private final Shard shard;
  
  private final ShardId shardId;

  private final List<StartAwareFutureTask> futureTasks;

  public ParallelShardOperationCallable(
      CountDownLatch startSignal,
      CountDownLatch doneSignal,
      ExitStrategy<T> exitStrategy,
      ShardOperation<T> operation,
      Shard shard,
      ShardId shardId,
      List<StartAwareFutureTask> futureTasks) {
    this.startSignal = startSignal;
    this.doneSignal = doneSignal;
    this.exitStrategy = exitStrategy;
    this.operation = operation;
    this.shard = shard;
    this.shardId = shardId;
    this.futureTasks = futureTasks;
  }

  public Void call() throws Exception {
    try {
      waitForStartSignal();
      log.debug(String.format("Starting execution of %s against shard %s",  operation.getOperationName(), shard));
      /**
       * If addResult() returns true it means there is no more work to be
       * performed.  Cancel all the outstanding tasks.
       */
      if(exitStrategy.addResult(operation.execute(shard.establishSqlSession(), shardId), shard)) {
        log.debug(
            String.format(
                "Short-circuiting execution of %s on other threads after execution against shard %s",
                operation.getOperationName(),
                shard));
        /**
         * It's ok to cancel ourselves because StartAwareFutureTask.cancel()
         * will return false if a task has already started executing, and we're
         * already executing.
         */

        log.debug(String.format("Checking %d future tasks to see if they need to be cancelled.", futureTasks.size()));
        for(StartAwareFutureTask ft : futureTasks) {
          log.debug(String.format("Preparing to cancel future task %d.", ft.getId()));
          /**
           * If a task was successfully cancelled that means it had not yet
           * started running.  Since the task won't run, the task won't be
           * able to decrement the CountDownLatch.  We need to decrement
           * it on behalf of the cancelled task.
           */
          if(ft.cancel(INTERRUPT_IF_RUNNING)) {
            log.debug("Task cancel returned true, decrementing counter on its behalf.");
            doneSignal.countDown();
          } else {
            log.debug("Task cancel returned false, not decrementing counter on its behalf.");
          }
        }
      } else {
        log.debug(
            String.format(
                "No need to short-cirtcuit execution of %s on other threads after execution against shard %s",
                operation.getOperationName(),
                shard));
      }
    } finally {
      // counter must get decremented no matter what
      log.debug(String.format("Decrementing counter for operation %s on shard %s", operation.getOperationName(), shard));
      doneSignal.countDown();
    }
    return null;
  }

  private void waitForStartSignal() {
    try {
      startSignal.await();
    } catch (InterruptedException e) {
      // I see no reason why this should happen
      final String msg = String.format("Received interrupt while waiting to begin execution of %s against shard %s", operation.getOperationName(), shard);
      log.error(msg);
      throw new RuntimeException(msg);
    }
  }
}

