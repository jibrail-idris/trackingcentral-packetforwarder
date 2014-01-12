package au.com.trackingcentral.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.dto.DataPacketDTO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class IMEIValidationService {

	private LiveTrackingDeviceMetaService liveTrackingDeviceMetaService;

	public void setLiveTrackingDeviceMetaService(
			LiveTrackingDeviceMetaService liveTrackingDeviceMetaService) {
		this.liveTrackingDeviceMetaService = liveTrackingDeviceMetaService;
	}

	/**
	 * Validates an incoming datapacket against IMEI list.
	 * 
	 * @param dataPacketDTO
	 * @return
	 */
	public boolean validate(DataPacketDTO dataPacketDTO) {		
		// query IMEI number, device against table
		return liveTrackingDeviceMetaService.getMetaByCommand(dataPacketDTO
				.getCommand()) != null;
	}
}
