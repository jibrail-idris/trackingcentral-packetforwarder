package au.com.trackingcentral.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class TrackingEncoder implements ProtocolEncoder {

	private int maxLineLength = Integer.MAX_VALUE;

	private static final Logger logger = Logger
			.getLogger(TrackingEncoder.class);

	@Override
	public void dispose(IoSession session) throws Exception {
	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		logger.info(message);

		CharsetEncoder encoder = Charset.defaultCharset().newEncoder();

		String value = (message == null ? "" : message.toString());
		IoBuffer buf = IoBuffer.allocate(value.length()).setAutoExpand(true);
		buf.putString(value, encoder);

		if (buf.position() > maxLineLength) {
			throw new IllegalArgumentException("Line length: " + buf.position());
		}

		buf.flip();
		out.write(buf);
	}

}
