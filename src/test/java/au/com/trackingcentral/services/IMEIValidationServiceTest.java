package au.com.trackingcentral.services;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

import au.com.trackingcentral.BaseTest;
import au.com.trackingcentral.dto.DataPacketDTO;
import au.com.trackingcentral.dto.LiveTrackingDeviceMetaDTO;

public class IMEIValidationServiceTest extends BaseTest {

	@Autowired
	private IMEIValidationService imeiValidationService;

	private LiveTrackingDeviceMetaService liveTrackingDeviceMetaService;

	@DirtiesContext
	@Test
	public void validate_true() {

		String command = "ASDASD";

		// doesn't really matter what the content of mocked meta is
		// as long as valid object is return, it is considered a match.
		LiveTrackingDeviceMetaDTO mockMeta = new LiveTrackingDeviceMetaDTO();

		// mock up results from client DB server.
		liveTrackingDeviceMetaService = EasyMock
				.createMock(LiveTrackingDeviceMetaService.class);
		liveTrackingDeviceMetaService.getMetaByCommand(command);
		EasyMock.expectLastCall().andReturn(mockMeta).once();
		EasyMock.replay(liveTrackingDeviceMetaService);

		imeiValidationService
				.setLiveTrackingDeviceMetaService(liveTrackingDeviceMetaService);

		DataPacketDTO dataPacketDTO = new DataPacketDTO();
		dataPacketDTO.setCommand(command);

		Assert.assertTrue(imeiValidationService.validate(dataPacketDTO));
	}

	@Test
	public void validate_false() {
		String command = "LLLDASD";

		// suppose if there' no match and getMetaByCommand returns null.
		LiveTrackingDeviceMetaDTO mockMeta = null;

		// mock up results from client DB server.
		liveTrackingDeviceMetaService = EasyMock
				.createMock(LiveTrackingDeviceMetaService.class);
		liveTrackingDeviceMetaService.getMetaByCommand(command);
		EasyMock.expectLastCall().andReturn(mockMeta).once();

		imeiValidationService
				.setLiveTrackingDeviceMetaService(liveTrackingDeviceMetaService);

		DataPacketDTO dataPacketDTO = new DataPacketDTO();

		// Expect validate to return false if getMetaByCommand returns false.
		Assert.assertFalse(imeiValidationService.validate(dataPacketDTO));
	}
}
