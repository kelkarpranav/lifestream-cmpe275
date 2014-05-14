package poke.server.management;

import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eye.Comm.Management;

/**
 * an implementation that does nothing with incoming requests.
 * 
 * @author gash
 * 
 */
public class NoOpQueue implements ChannelQueue {
	protected static Logger logger = LoggerFactory.getLogger("server");
	private String queueName;

	public NoOpQueue() {
		queueName = this.getClass().getName();
	}

	@Override
	public void shutdown(boolean hard) {
		logger.info(queueName + ": queue shutting down");
	}

	@Override
	public void enqueueRequest(Management req, SocketAddress sa) {
		logger.info(queueName + ": request received");
	}

	public void enqueueResponse(Management reply) {
		logger.info(queueName + ": response received");
	}

	

}
