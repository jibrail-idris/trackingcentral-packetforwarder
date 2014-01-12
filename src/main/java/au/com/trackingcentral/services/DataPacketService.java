package au.com.trackingcentral.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.dao.DataPacketDAO;
import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.entities.DataPacket;
import au.com.trackingcentral.exceptions.DataPacketParserException;
import au.com.trackingcentral.parsers.DataPacketParser;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class DataPacketService {

	private DataPacketDAO dataPacketDAO;
	private DataPacketParser dataPacketParser;

	public void setDataPacketDAO(DataPacketDAO dataPacketDAO) {
		this.dataPacketDAO = dataPacketDAO;
	}

	public void setDataPacketParser(DataPacketParser dataPacketParser) {
		this.dataPacketParser = dataPacketParser;
	}

	/**
	 * The DataPacketService should not concern itself with the semantics of the
	 * packet itself. Allow that to be sorted out somewhere else.
	 * 
	 * @param dataPacketDTO
	 * @return
	 * @throws DataPacketParserException
	 */
	public DataPacketDTO save(DataPacketDTO dataPacketDTO)
			throws DataPacketParserException {
		DataPacket dataPacket = new DataPacket();
		dataPacket.setImei(dataPacketDTO.getImei());
		dataPacket.setImeiPosition(dataPacketDTO.getImeiPosition());
		dataPacket.setRaw(dataPacketDTO.getRaw());
		dataPacket.setIsError(dataPacketDTO.isError());
		dataPacket.setTimestamp(dataPacketDTO.getTimeStamp());
		dataPacketDAO.save(dataPacket);
		return get(dataPacket.getId());
	}

	public DataPacketDTO get(Integer dataPacketId)
			throws DataPacketParserException {
		DataPacket dataPacket = dataPacketDAO.read(dataPacketId);
		DataPacketDTO dto = dataPacketParser.parse(dataPacket.getRaw(),
				dataPacket.getImeiPosition());
		dto.setPacketId(dataPacket.getId());
		dto.setTimeStamp(dataPacket.getTimestamp());
		return dto;
	}

	public List<DataPacketDTO> gets() throws DataPacketParserException {
		List<DataPacket> all = dataPacketDAO.readAll();
		List<DataPacketDTO> allList = new ArrayList<DataPacketDTO>();
		for (DataPacket dataPacket : all) {
			allList.add(dataPacketParser.parse(dataPacket.getRaw(),
					dataPacket.getImeiPosition()));
		}
		return allList;
	}

	public List<DataPacketDTO> getDataPacketsByIMEI(String imei)
			throws DataPacketParserException {
		List<DataPacket> dataPackets = dataPacketDAO.readByIMEI(imei);
		List<DataPacketDTO> dtos = new ArrayList<DataPacketDTO>();
		for (DataPacket dataPacket : dataPackets) {
			DataPacketDTO dto = dataPacketParser.parse(dataPacket.getRaw(),
					dataPacket.getImeiPosition());
			dto.setPacketId(dataPacket.getId());
			dtos.add(dto);

		}
		return dtos;
	}
}
