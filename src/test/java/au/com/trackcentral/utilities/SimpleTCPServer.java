package au.com.trackcentral.utilities;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import au.com.trackingcentral.codec.TrackingCodecFactory;

public class SimpleTCPServer {

	private NioSocketAcceptor acceptor = new NioSocketAcceptor();

	private String data;

	public SimpleTCPServer(int port) throws IOException {

		DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
		chain.addFirst("codec", new ProtocolCodecFilter(
				new TrackingCodecFactory()));

		acceptor.setHandler(new IoHandlerAdapter() {
			@Override
			public void messageReceived(IoSession session, Object message)
					throws Exception {
				super.messageReceived(session, message);
				data = message.toString();
			}
		});

		acceptor.setReuseAddress(true);
		acceptor.bind(new InetSocketAddress(port));
	}

	public void shutdown() {
		Map<Long, IoSession> sessions = acceptor.getManagedSessions();
		for (IoSession session : sessions.values()) {
			session.close(true);
		}
		acceptor.dispose(true);
	}

	public String getData() {
		return data;
	}
}
