package poke.server.management;

import java.net.SocketAddress;

import eye.Comm.Management;

public interface ChannelQueue {

	public abstract void shutdown(boolean hard);

	public abstract void enqueueRequest(Management req, SocketAddress sa);

	public abstract void enqueueResponse(Management reply);
	

}
