package au.com.trackingcentral.parsers;

import org.springframework.util.StringUtils;

import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.exceptions.DataPacketParserException;
import au.com.trackingcentral.helpers.DataPacketDTOFactory;

public class DataPacketParser {

	private DataPacketDTOFactory dataPacketDTOFactory;

	public void setDataPacketDTOFactory(
			DataPacketDTOFactory dataPacketDTOFactory) {
		this.dataPacketDTOFactory = dataPacketDTOFactory;
	}

	public DataPacketDTO parse(String data, int imeiPosition)
			throws DataPacketParserException {
		if (!StringUtils.hasLength(data)) {
			throw new DataPacketParserException(
					"Data can not be null or empty string");
		} else {
			DataPacketDTO dataPacketDTO;
			// Break apart the raw string into tokens
			String[] dataArray = data.split(",");

			if (imeiPosition > 0 && imeiPosition <= dataArray.length) {
				// Get command, IMEI from raw string
				String command = determineCommand(dataArray);
				String imei = determineIMEI(dataArray, imeiPosition);
				// Different devices position these bits of data differently.
				// set data into DataPacketDTO
				dataPacketDTO = dataPacketDTOFactory.createDataPacket(command,
						imei, imeiPosition, false, data);
			} else {
				dataPacketDTO = dataPacketDTOFactory
						.createInvalidDataPacket(data);
			}

			// return DataPacketDTO
			return dataPacketDTO;
		}
	}

	private String determineCommand(String[] tokens) {
		return tokens[0];
	}

	private String determineIMEI(String[] tokens, int imeiPosition) {
		String command = determineCommand(tokens);
		if (command.equals("+RESP:GTSOS") && tokens.length >= 4) {
			if (tokens[3].equals("vl2000")) {
				return tokens[2];
			} else {
				return tokens[1];
			}
		} else {
			return tokens[imeiPosition - 1];
		}
	}
}
