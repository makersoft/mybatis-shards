package org.makersoft.shards.strategy.access.impl;

import java.util.List;
import java.util.Random;

import org.makersoft.shards.Shard;
import org.makersoft.shards.utils.Iterables;

/**
 * A SequentialShardAccessStrategy starts with the first Shard in the list every
 * time. If the ExitStrategy with which the AccessStrategy is paired supports
 * early exit (keep searching until you have 100 results), the first shard in
 * the list may receive a disproportionately high percentage of the queries. In
 * order to combat this we have a load balanced approach that adjusts that
 * provides a rotated view of the list of shards. The list is rotated by a
 * different amount each time. The amount by which we rotate is random because
 * doing a true round-robin would require that we know the shards we're rotating
 * in advance, but the shards passed to a ShardAccessStrategy can vary between
 * invocations.
 * 
 * @author maxr@google.com (Max Ross)
 */
public class LoadBalancedSequentialShardAccessStrategy extends
		SequentialShardAccessStrategy {

	private final Random rand;

	public LoadBalancedSequentialShardAccessStrategy() {
		this.rand = new Random(System.currentTimeMillis());
	}

	@Override
	protected Iterable<Shard> getNextOrderingOfShards(List<Shard> shards) {
		return Iterables.rotate(shards, rand.nextInt() % shards.size());
	}

}
