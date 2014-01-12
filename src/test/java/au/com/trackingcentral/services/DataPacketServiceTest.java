package au.com.trackingcentral.services;

import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.BaseTest;
import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.exceptions.DataPacketParserException;
import au.com.trackingcentral.helpers.DataPacketDTOFactory;
import au.com.trackingcentral.parsers.DataPacketParser;

@Transactional
public class DataPacketServiceTest extends BaseTest {

	@Autowired
	private DataPacketService dataPacketService;

	@Autowired
	private DataPacketDTOFactory dataPacketDTOFactory;

	@Autowired
	private DataPacketParser dataPacketParser;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void save_OnePacket_Invalid1() throws DataPacketParserException {
		String command = "FFFF";
		String imei = "44444";
		Integer imeiPosition = 9;
		String raw = "ewrewrwer234234";
		Date timestamp = new Date();

		DataPacketDTO dataPacketDTO = dataPacketDTOFactory.createDataPacket(
				command, imei, imeiPosition, true, raw, timestamp);

		DataPacketDTO savedDataPacket = dataPacketService.save(dataPacketDTO);

		Assert.assertNotNull(savedDataPacket.getPacketId());
		Assert.assertTrue(savedDataPacket.getPacketId() > 0);
		Assert.assertEquals(raw, dataPacketDTO.getRaw());
		Assert.assertTrue(savedDataPacket.isError());
		Assert.assertEquals(timestamp, savedDataPacket.getTimeStamp());
	}

	@Test
	public void save_OnePacket_Invalid2() {

	}

	@Test
	public void save_OnePacket_Valid() throws DataPacketParserException {
		String command = "+RESP:GTBATR";
		String data = "+RESP:GTBATR,010000,012207000000015,vl2000,,,100,4220,,,20120331070925,0010$";
		Integer imeiPosition = 3;

		DataPacketDTO dto = dataPacketParser.parse(data, imeiPosition);
		DataPacketDTO savedDataPacket = dataPacketService.save(dto);

		// make sure the packet is not flagged as error.
		Assert.assertFalse(savedDataPacket.isError());
		Assert.assertEquals(command, savedDataPacket.getCommand());
		Assert.assertEquals(data, savedDataPacket.getRaw());
	}
}
