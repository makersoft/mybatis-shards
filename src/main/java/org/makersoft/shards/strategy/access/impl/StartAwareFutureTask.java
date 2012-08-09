/*
 * @(#)StartAwareFutureTask.java 2012-8-1 下午10:00:00
 *
 * Copyright (c) 2011-2012 Makersoft.org all rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *
 */
package org.makersoft.shards.strategy.access.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Extension of FutureTask that provides slightly different cancel()
 * behavior.  We want cancel() to only return true if the task has not yet run.
 *
 * Multi-threaded scenario 1:
 * run() invoked in T1
 * The task hasn't been cancelled, so runCalled is set
 * to true.  This happens in a synchronized block so cancel() cannot
 * execute while the flag is being set.  Once we enter the synchronized
 * block and get past the cancelled check we are guaranteed to run, and
 * if cancel() is invoked at any point afterwards runCalled will be true, so
 * cancel() will be unable to return anything other than false, which is what
 * we want.
 *
 * Multi-threaded scenario 2:
 * cancel() invoked in T1
 * The method is synchronized, so even if T2 invokes run() it won't be able to
 * enter the synchronized block until cancel() finishes executing.
 * The cancelled flag is set to true, so we return right away.
 * cancel() returns the result of super.cancel() because runCalled is guaranteed to be false.
 *
 */
class StartAwareFutureTask extends FutureTask<Void> {

  boolean runCalled;

  boolean cancelled;

  private final int id;

  private final Log log = LogFactory.getLog(getClass());

  public StartAwareFutureTask(Callable<Void> callable, int id) {
    super(callable);
    this.id = id;
  }

  @Override
  public void run() {

    log.debug(String.format("Task %d: Run invoked.", id));
    synchronized(this) {
      if (cancelled) {
        log.debug(String.format("Task %d: Task will not run.", id));
        return;
      }
      runCalled = true;
    }
    log.debug(String.format("Task %d: Task will run.", id));
    super.run();
  }

  @Override
  public synchronized  boolean cancel(boolean mayInterruptIfRunning) {
    if(runCalled) {
      /**
       * If run has already been called we can't call super.  That's because
       * super.cancel might be called in between the time we leave the
       * synchronization block in run() and the time we call super.run().
       * super.run() checks the state of the FutureTask before actuall invoking
       * the inner task, and if that check sees that this task is cancelled it
       * won't run.  That leaves us in a position where a task actually has
       * been cancelled but cancel returns true, so we're left with a counter
       * that never gets decremented and everything hangs.
       */
      return false;
    }
    boolean result = superCancel(mayInterruptIfRunning);
    cancelled = true;
    log.debug(String.format("Task %d: Task cancelled.", id));
    return result;
  }

  public int getId() {
    return id;
  }

  boolean superCancel(boolean mayInterruptIfRunning) {
    return super.cancel(mayInterruptIfRunning);
  }
}
