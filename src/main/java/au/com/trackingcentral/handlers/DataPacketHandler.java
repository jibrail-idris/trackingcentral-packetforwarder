package au.com.trackingcentral.handlers;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.util.StringUtils;

import au.com.trackingcentral.clients.DataPacketForwarder;
import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.dto.LiveTrackingDeviceMetaDTO;
import au.com.trackingcentral.helpers.DataPacketForwarderHelper;
import au.com.trackingcentral.parsers.DataPacketParser;
import au.com.trackingcentral.services.DataPacketService;
import au.com.trackingcentral.services.IMEIValidationService;
import au.com.trackingcentral.services.LiveTrackingDeviceMetaService;
import au.com.trackingcentral.utilities.ProcessHttpCall;

/**
 * Consumes mobile devices' data packets and forwards them to destination
 * server.
 * 
 * @author Jibrail Idris
 * 
 */
public class DataPacketHandler extends IoHandlerAdapter {

	private static final Logger logger = Logger
			.getLogger(DataPacketHandler.class);

	private DataPacketParser dataPacketParser;
	private IMEIValidationService imeiValidationService;
	private DataPacketService dataPacketService;
	private DataPacketForwarderHelper dataPacketForwarderHelper;
	private LiveTrackingDeviceMetaService liveTrackingDeviceMetaService;
	private ProcessHttpCall processHttpCall;

	public void setDataPacketParser(DataPacketParser dataPacketParser) {
		this.dataPacketParser = dataPacketParser;
	}

	public void setImeiValidationService(
			IMEIValidationService imeiValidationService) {
		this.imeiValidationService = imeiValidationService;
	}

	public void setDataPacketService(DataPacketService dataPacketService) {
		this.dataPacketService = dataPacketService;
	}

	public void setDataPacketForwarderHelper(
			DataPacketForwarderHelper dataPacketForwarderHelper) {
		this.dataPacketForwarderHelper = dataPacketForwarderHelper;
	}

	public void setLiveTrackingDeviceMetaService(
			LiveTrackingDeviceMetaService liveTrackingDeviceMetaService) {
		this.liveTrackingDeviceMetaService = liveTrackingDeviceMetaService;
	}

	public void setProcessHttpCall(ProcessHttpCall processHttpCall) {
		this.processHttpCall = processHttpCall;
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		logger.info(cause.getMessage(), cause);
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		logger.info(new StringBuilder(session.getRemoteAddress().toString())
				.append("-").append(message));
		if (message != null) {
			// get imei position based on message. Use parser to get command.

			// give imeiPosition any positive non-zero number because the
			// command
			// is always the first token and anything else is not needed.
			String messageString = message.toString();
			if (StringUtils.hasLength(messageString)) {
				DataPacketDTO dataPacketDTO = dataPacketParser.parse(
						messageString, 1);
				String command = dataPacketDTO.getCommand();

				// Now get imei position.
				LiveTrackingDeviceMetaDTO meta = liveTrackingDeviceMetaService
						.getMetaByCommand(command);

				// if there's meta information for device, use IMEI position to
				// extract
				// IMEI.
				// Else flag dataPacketDTO as error.
				if (meta != null) {
					// pass command against meta to determine IMEI position.
					// parse message into DataPacketDTO
					dataPacketDTO = dataPacketParser.parse(messageString,
							meta.getImeiPosition());

					// Validate packet against IMEI list.
					if (!imeiValidationService.validate(dataPacketDTO)) {
						// If packet is invalid, flag as error.
						dataPacketDTO = dataPacketParser.parse(messageString,
								DataPacketDTO.ERROR);
					} else {
						// if packet is valid, create a client connection to
						// secondary
						// server.
						// Handover the packet to forwarder.
						DataPacketForwarder dataPacketForwarder = dataPacketForwarderHelper
								.createDataPacketForwarder();
						dataPacketForwarder.forward(
								meta.getSecondaryServerIP(),
								meta.getSecondaryServerPort(),
								dataPacketDTO.getRaw());

						processHttpCall.triggerProcess(dataPacketDTO);
					}
				} else {
					dataPacketDTO = dataPacketParser.parse(messageString,
							DataPacketDTO.ERROR);
				}

				// Save packet.
				dataPacketDTO = dataPacketService.save(dataPacketDTO);
				logger.info(dataPacketDTO);
			}
		}
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("IDLE " + session.getIdleCount(status));

	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		logger.info("CLOSE " + session.getRemoteAddress());
	}
}
