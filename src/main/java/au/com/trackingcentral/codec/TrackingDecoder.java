package au.com.trackingcentral.codec;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class TrackingDecoder extends ProtocolDecoderAdapter {

	private static final Logger logger = Logger
			.getLogger(TrackingDecoder.class);

	private final CharsetDecoder decoder = Charset.defaultCharset().newDecoder();

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		logger.info(session);
		out.write(in.getString(decoder));
	}

	@Override
	public void finishDecode(IoSession session, ProtocolDecoderOutput out)
			throws Exception {
		logger.info(session);
	}
}
