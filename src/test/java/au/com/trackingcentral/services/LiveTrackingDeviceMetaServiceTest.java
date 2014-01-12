package au.com.trackingcentral.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import au.com.trackingcentral.BaseTest;
import au.com.trackingcentral.dto.LiveTrackingDeviceMetaDTO;

public class LiveTrackingDeviceMetaServiceTest extends BaseTest {

	@Autowired
	private LiveTrackingDeviceMetaService liveTrackingDeviceMetaService;

	@Before
	public void setUp() {
	}

	@Test
	public void getMetaByCommand_Valid() {

		String secondaryServerIP = "127.0.0.1";
		Integer secondaryServerPort = 7777;
		Integer expectedImeiPosition = 3;

		String command = "+RESP:GTVER";

		LiveTrackingDeviceMetaDTO meta = liveTrackingDeviceMetaService
				.getMetaByCommand(command);

		Assert.assertNotNull(meta);
		Assert.assertEquals(expectedImeiPosition, meta.getImeiPosition());
		Assert.assertTrue(meta.isForwardToServer());
		Assert.assertEquals(secondaryServerIP, meta.getSecondaryServerIP());
		Assert.assertEquals(secondaryServerPort.intValue(),
				meta.getSecondaryServerPort());
	}

	@Test
	public void getMetaByCommand_Invalid() {
		Integer expectedImeiPosition = 5;

		String command = "KASJDKAHDJKAS"; // invalid command, no match in DB.

		LiveTrackingDeviceMetaDTO meta = liveTrackingDeviceMetaService
				.getMetaByCommand(command);

		Assert.assertNull(meta);
	}
}
