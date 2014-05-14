package poke.server.management;


import org.jboss.netty.channel.Channel;
import poke.server.management.ManagementHandler.ManagementClosedListener;


public class QueueFactory {

	public static ChannelQueue getInstance(Channel channel, String nodeId) {
		// if a single queue is needed, this is where we would obtain a
		// handle to it.
		ChannelQueue queue = null;

		if (channel == null)
			queue = new NoOpQueue();
		else
			queue = new ManagementQueue(channel, nodeId);

		// on close remove from queue
		channel.getCloseFuture().addListener(
				new ManagementClosedListener(queue));

		return queue;
	}

}