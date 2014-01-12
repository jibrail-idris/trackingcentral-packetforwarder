package au.com.trackingcentral.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

/**
 * A {@link TrackingCodecFactory} that performs encoding and decoding between a
 * mobile device and this server.
 * 
 * @author Jibrail Idris
 */
public class TrackingCodecFactory implements ProtocolCodecFactory {

	private TrackingDecoder decoder;
	private TrackingEncoder encoder;

	public TrackingCodecFactory() {
		decoder = new TrackingDecoder();
		encoder = new TrackingEncoder();
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return decoder;
	}

	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return encoder;
	}
}
