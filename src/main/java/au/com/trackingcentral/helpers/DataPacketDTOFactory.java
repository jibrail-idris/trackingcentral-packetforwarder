package au.com.trackingcentral.helpers;

import java.util.Date;

import au.com.trackingcentral.dto.DataPacketDTO;

public class DataPacketDTOFactory {

	/**
	 * Helper factory DataPacketDTO class
	 * 
	 * @return
	 */
	public DataPacketDTO createDataPacket() {
		return new DataPacketDTO();
	}

	public DataPacketDTO createDataPacket(String command, String imei,
			Integer imeiPosition, Boolean isError, String raw) {
		DataPacketDTO dataPacketDTO = createDataPacket();
		dataPacketDTO.setCommand(command);
		dataPacketDTO.setImei(imei);
		dataPacketDTO.setImeiPosition(imeiPosition);
		dataPacketDTO.setError(isError);
		dataPacketDTO.setRaw(raw);
		dataPacketDTO.setTimeStamp(new Date());
		return dataPacketDTO;
	}

	public DataPacketDTO createDataPacket(String command, String imei,
			Integer imeiPosition, Boolean isError, String raw, Date timestamp) {
		DataPacketDTO dataPacketDTO = createDataPacket(command, imei,
				imeiPosition, isError, raw);
		dataPacketDTO.setTimeStamp(timestamp);
		return dataPacketDTO;
	}

	public DataPacketDTO createInvalidDataPacket(String raw) {
		DataPacketDTO dataPacketDTO = createDataPacket();
		dataPacketDTO.setRaw(raw);
		dataPacketDTO.setError(true);
		dataPacketDTO.setTimeStamp(new Date());
		dataPacketDTO.setImeiPosition(DataPacketDTO.ERROR);
		return dataPacketDTO;
	}
}
