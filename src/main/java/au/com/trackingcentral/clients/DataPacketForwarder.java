package au.com.trackingcentral.clients;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import au.com.trackingcentral.handlers.DataPacketForwarderHandler;

/**
 * 
 * @author Jibrail Idris
 * 
 */
public class DataPacketForwarder {

	private static final Logger logger = Logger
			.getLogger(DataPacketForwarder.class);

	private InetSocketAddress socketAddress;
	private DataPacketForwarderHandler handler;
	private NioSocketConnector connector;

	public DataPacketForwarder(InetSocketAddress socketAddress,
			DataPacketForwarderHandler handler, NioSocketConnector connector) {
		setSocketAddress(socketAddress);
		setHandler(handler);
		setConnector(connector);

		connector.setHandler(handler);
	}

	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	public void setSocketAddress(InetSocketAddress socketAddress) {
		this.socketAddress = socketAddress;
	}

	public DataPacketForwarderHandler getHandler() {
		return handler;
	}

	public void setHandler(DataPacketForwarderHandler handler) {
		this.handler = handler;
	}

	public void setConnector(NioSocketConnector connector) {
		this.connector = connector;
	}

	/**
	 * Forwards the data packet in it's raw form to secondary server.
	 * 
	 * @param rawDataPacket
	 */
	public void forward(String rawDataPacket) {
		// Establish connection secondary server
	}

	public void forward(String secondaryServerIP, Integer secondaryServerPort,
			String rawDataPacket) {
		
		logger.info(rawDataPacket);

		ConnectFuture future = connector.connect(new InetSocketAddress(
				secondaryServerIP, secondaryServerPort));
		future.awaitUninterruptibly();
		IoSession session = future.getSession();
		session.write(rawDataPacket);
	}
}
