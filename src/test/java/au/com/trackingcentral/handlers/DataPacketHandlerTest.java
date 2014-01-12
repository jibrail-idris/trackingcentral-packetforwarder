package au.com.trackingcentral.handlers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.mina.core.session.DummySession;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackcentral.utilities.SimpleTCPServer;
import au.com.trackingcentral.BaseTest;
import au.com.trackingcentral.clients.DataPacketForwarder;
import au.com.trackingcentral.dao.DataPacketDAO;
import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.dto.LiveTrackingDeviceMetaDTO;
import au.com.trackingcentral.entities.DataPacket;
import au.com.trackingcentral.formatters.DateFormatter;
import au.com.trackingcentral.formatters.DateFormatter.Type;
import au.com.trackingcentral.helpers.DataPacketDTOFactory;
import au.com.trackingcentral.helpers.DataPacketForwarderHelper;
import au.com.trackingcentral.parsers.DataPacketParser;
import au.com.trackingcentral.services.DataPacketService;
import au.com.trackingcentral.services.IMEIValidationService;
import au.com.trackingcentral.services.LiveTrackingDeviceMetaService;

import com.github.tomakehurst.wiremock.junit.WireMockRule;

@Transactional
public class DataPacketHandlerTest extends BaseTest {

	public static final int SECONDARY_SERVER_PORT = 7777;

	@Rule
	public WireMockRule wireMockRule = new WireMockRule(8089);

	@Autowired
	private DataPacketHandler dataPacketHandler;

	@Autowired
	private DataPacketDAO dataPacketDAO;

	@Autowired
	private DataPacketService dataPacketService;

	@Autowired
	private DataPacketParser dataPacketParser;

	private IMEIValidationService imeiValidationService;
	private DataPacketDTOFactory dataPacketDTOFactory;
	private DataPacketForwarderHelper dataPacketForwarderHelper;

	private LiveTrackingDeviceMetaService liveTrackingDeviceMetaService;

	private SimpleTCPServer server;

	@Before
	public void setUp() throws IOException {
		server = new SimpleTCPServer(SECONDARY_SERVER_PORT);
	}

	@After
	public void tearDown() {
		server.shutdown();
	}

	@DirtiesContext
	@Test
	public void messageReceived_ValidPacket() throws Exception {

		SimpleDateFormat dateFormatter = DateFormatter
				.getDateFormatter(Type.MYSQL);

		String secondaryServerIP = "10.0.0.1";
		int secondaryServerPort = 3333;

		String command = "+RESP:XXXXXXXXX";

		String data = command
				+ ",010000,012207000000015,vl2000,,,100,4220,,,20120331070925,0010$";
		String imei = "012207000000015";
		Integer imeiPosition = 3;
		Date timeStamp = new Date();

		DataPacketDTO mockedDetermineIMEIPosDataPacketDTO = new DataPacketDTO();
		DataPacketDTO mockedDataPacketDTO = new DataPacketDTO();

		mockedDetermineIMEIPosDataPacketDTO.setCommand(command);

		// set up the mocked dataPacket
		mockedDataPacketDTO.setCommand(command);
		mockedDataPacketDTO.setRaw(data);
		mockedDataPacketDTO.setTimeStamp(timeStamp);
		mockedDataPacketDTO.setImei(imei);
		mockedDataPacketDTO.setImeiPosition(imeiPosition);
		mockedDataPacketDTO.setError(false);

		// set up mocked Meta
		LiveTrackingDeviceMetaDTO mockedMeta = new LiveTrackingDeviceMetaDTO();
		// let's assume the command has a match in DB and the imei position for
		// this
		// command is 3.
		mockedMeta.setImeiPosition(imeiPosition);
		mockedMeta.setSecondaryServerIP(secondaryServerIP);
		mockedMeta.setSecondaryServerPort(secondaryServerPort);

		// mock imeiValidationService
		imeiValidationService = EasyMock
				.createMock(IMEIValidationService.class);
		imeiValidationService.validate(mockedDataPacketDTO);
		EasyMock.expectLastCall().andReturn(true);
		EasyMock.replay(imeiValidationService);

		// mock dataPacketDTOFactory
		dataPacketDTOFactory = EasyMock.createMock(DataPacketDTOFactory.class);
		dataPacketDTOFactory.createDataPacket();
		EasyMock.expectLastCall().andReturn(mockedDataPacketDTO);
		EasyMock.replay(dataPacketDTOFactory);

		// mock dataPacketParser
		dataPacketParser = EasyMock.createMock(DataPacketParser.class);
		dataPacketParser.parse(data, 1);
		EasyMock.expectLastCall()
				.andReturn(mockedDetermineIMEIPosDataPacketDTO);
		dataPacketParser.parse(data, imeiPosition);
		EasyMock.expectLastCall().andReturn(mockedDataPacketDTO);
		dataPacketParser.setDataPacketDTOFactory(dataPacketDTOFactory);
		EasyMock.expectLastCall();
		EasyMock.replay(dataPacketParser);

		// mock dataPacketForwarder
		DataPacketForwarder dataPacketForwarder = EasyMock
				.createMock(DataPacketForwarder.class);
		dataPacketForwarder.forward(secondaryServerIP, secondaryServerPort,
				data);
		EasyMock.expectLastCall();
		EasyMock.replay(dataPacketForwarder);

		// mock dataPacketForwarderHelper
		dataPacketForwarderHelper = EasyMock
				.createMock(DataPacketForwarderHelper.class);
		dataPacketForwarderHelper.createDataPacketForwarder();
		EasyMock.expectLastCall().andReturn(dataPacketForwarder);
		EasyMock.replay(dataPacketForwarderHelper);

		liveTrackingDeviceMetaService = EasyMock
				.createMock(LiveTrackingDeviceMetaService.class);
		liveTrackingDeviceMetaService.getMetaByCommand(command);
		EasyMock.expectLastCall().andReturn(mockedMeta);
		EasyMock.replay(liveTrackingDeviceMetaService);

		dataPacketHandler.setDataPacketParser(dataPacketParser);
		dataPacketHandler.setImeiValidationService(imeiValidationService);
		dataPacketHandler
				.setDataPacketForwarderHelper(dataPacketForwarderHelper);
		dataPacketHandler
				.setLiveTrackingDeviceMetaService(liveTrackingDeviceMetaService);

		// trigger message received event first
		dataPacketHandler.messageReceived(new DummySession(), data);

		// assert packet has been saved
		List<DataPacket> results = dataPacketDAO.readByIMEI(imei);

		Assert.assertEquals(1, results.size());

		DataPacket dataPacket = results.get(0);

		Assert.assertNotNull(dataPacket.getId());
		Assert.assertEquals(imei, dataPacket.getImei());
		Assert.assertFalse(dataPacket.isError());

		Assert.assertEquals(dateFormatter.format(timeStamp),
				dateFormatter.format(dataPacket.getTimestamp()));
		Assert.assertEquals(data, dataPacket.getRaw());

	}

	/**
	 * Integration test1
	 */
	@Test
	public void messageReceived_IntegrationTest_InvalidPacket()
			throws Exception {
		DummySession session = new DummySession();
		// suppose if parsing is ok, but command is not valid.
		String command = "+RESP:XXXXXXXXX";

		String data = command
				+ ",010000,012207000000015,vl2000,,,100,4220,,,20120331070925,0010$";

		dataPacketHandler.messageReceived(session, data);

	}

	@Test
	public void messageReceived_IntegrationTest_ValidPacket() throws Exception {

		stubFor(get(urlEqualTo("/process.cfm\\?.*")).withHeader("Accept",
				equalTo("text/html")).willReturn(aResponse().withStatus(200)));

		Integer imeiPosition = 3;
		String command = "+RESP:GTBATR";
		String data = "+RESP:GTBATR,010000,012207000000015,vl2000,,,100,4220,,,20120331070925,0010$";

		String imei = dataPacketParser.parse(data, imeiPosition).getImei();

		dataPacketHandler.messageReceived(new DummySession(), data);

		// Get datapackets by imei and assert that there's only one data packet
		// against that imei
		List<DataPacketDTO> dataPacketsByIMEI = dataPacketService
				.getDataPacketsByIMEI(imei);

		Assert.assertEquals(1, dataPacketsByIMEI.size());
		DataPacketDTO dataPacket = dataPacketsByIMEI.get(0);
		Assert.assertEquals(command, dataPacket.getCommand());
		Assert.assertEquals(data, dataPacket.getRaw());
		Assert.assertEquals(data, server.getData());

		verify(getRequestedFor(urlMatching("/process.cfm\\?.*"))
				.withRequestBody(matching("")));
	}

	@Test
	public void messageReceived_IntegrationTest_EmptyMessage() throws Exception {
		String data = "";
		dataPacketHandler.messageReceived(new DummySession(), data);
	}

	@Test
	public void messageReceived_IntegrationTest_GarbageData() throws Exception {
		String data = "akjshdakjsnksd";
		dataPacketHandler.messageReceived(new DummySession(), data);

		List<DataPacketDTO> dataPackets = dataPacketService.gets();

		Assert.assertEquals(1, dataPackets.size());
		DataPacketDTO dataPacket = dataPackets.get(0);
		Assert.assertEquals(DataPacketDTO.ERROR, dataPacket.getImeiPosition()
				.intValue());
	}
}
