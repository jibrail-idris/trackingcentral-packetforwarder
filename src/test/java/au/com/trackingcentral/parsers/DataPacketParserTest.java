package au.com.trackingcentral.parsers;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.exceptions.DataPacketParserException;
import au.com.trackingcentral.helpers.DataPacketDTOFactory;

public class DataPacketParserTest {

	private DataPacketParser dataPacketParser;
	private DataPacketDTOFactory dataPacketDTOFactory;

	@Before
	public void setUp() throws Exception {
		dataPacketParser = new DataPacketParser();
		dataPacketDTOFactory = new DataPacketDTOFactory();
		dataPacketParser.setDataPacketDTOFactory(dataPacketDTOFactory);
	}

	@Test
	public void parse_Example1() throws DataPacketParserException {
		String data = "+RESP:GTBATR,010000,012207000000015,vl2000,,,100,4220,,,20120331070925,0010$";
		int imeiPosition = 3;
		String imei = "012207000000015";
		DataPacketDTO dataPacket = dataPacketParser.parse(data, imeiPosition);

		Assert.assertEquals("+RESP:GTBATR", dataPacket.getCommand());
		Assert.assertEquals(imei, dataPacket.getImei());
		Assert.assertFalse(dataPacket.isError());
		Assert.assertEquals(data, dataPacket.getRaw());
	}

	@Test
	public void parse_Example2() throws DataPacketParserException {
		String data = "+RESP:GTCONF,010000,,012207000000015,vl2000,116.236.221.75,9090,15821885163,3,10,1FE0,60,5,4,0,100,200,1,2,1,1,3,20120331070951,0011$";
		int imeiPosition = 4;
		String imei = "012207000000015";

		DataPacketDTO dataPacket = dataPacketParser.parse(data, imeiPosition);

		Assert.assertEquals("+RESP:GTCONF", dataPacket.getCommand());
		Assert.assertEquals(imei, dataPacket.getImei());
		Assert.assertFalse(dataPacket.isError());
		Assert.assertEquals(data, dataPacket.getRaw());
	}

	@Test(expected = DataPacketParserException.class)
	public void parse_Example3_NullData() throws DataPacketParserException {
		String data = null;
		int imeiPosition = 4;
		String imei = "012207000000015";
		DataPacketDTO dataPacket = dataPacketParser.parse(data, imeiPosition);
	}

	@Test
	public void parse_GarbageData() throws DataPacketParserException {
		String data = "asdasdasdasd122131232423432";
		int imeiPosition = 5;
		DataPacketDTO dataPacket = dataPacketParser.parse(data, imeiPosition);

		Assert.assertTrue(dataPacket.isError());
	}

	@Test
	public void parse_GTSOS_v12000() throws DataPacketParserException {

		int expectedImeiPosition = 3;
		String expectedCommand = "+RESP:GTSOS";
		String expectedImei = "012207000000015";

		String data = "+RESP:GTSOS,010000,012207000000015,vl2000,,,1,19,1.9,0,0,121.354540,31.222002, 20120331054244,460,001,144F,0E93,00,18,460,001,144F,0E94,20120331063654,0005$";
		// Determine the IMEI position first.
		DataPacketDTO dataPacket = dataPacketParser.parse(data, 1);
		Assert.assertEquals(expectedCommand, dataPacket.getCommand());

		DataPacketDTO dataPacket2 = dataPacketParser.parse(data,
				expectedImeiPosition);
		
		Assert.assertEquals(expectedCommand, dataPacket2.getCommand());
		Assert.assertEquals(expectedImei, dataPacket2.getImei());
		Assert.assertEquals(expectedImeiPosition, dataPacket2.getImeiPosition().intValue());

	}

	@Test
	public void parse_GTSOS_Without_v12000() throws DataPacketParserException {

		int expectedImeiPosition = 2;
		String expectedCommand = "+RESP:GTSOS";
		String expectedImei = "359231030000010";

		String data = "+RESP:GTSOS,359231030000010,0,0,0,1,4.3,92,70.0,1,121.354335,31.222073,20090101000000,0460,0000,18d8,6141,00,11F0,0102120204";

		DataPacketDTO dataPacket = dataPacketParser.parse(data, 1);
		Assert.assertEquals(expectedCommand, dataPacket.getCommand());
		
		DataPacketDTO dataPacket2 = dataPacketParser.parse(data,
				expectedImeiPosition);
		
		Assert.assertEquals(expectedCommand, dataPacket2.getCommand());
		Assert.assertEquals(expectedImei, dataPacket2.getImei());
		Assert.assertEquals(expectedImeiPosition, dataPacket2.getImeiPosition().intValue());
	}
	
	@Test
	public void parse_GTSOS() throws DataPacketParserException {
		String expectedCommand = "+RESP:GTSOS";
		
		String data = "+RESP:GTSOS";
		DataPacketDTO dataPacket = dataPacketParser.parse(data, 1);
		Assert.assertEquals(expectedCommand, dataPacket.getCommand());
	}
}
