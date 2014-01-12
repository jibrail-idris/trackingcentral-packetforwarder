package au.com.trackingcentral.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import au.com.trackingcentral.dao.queries.GetLiveTrackingDeviceMetaByCommand;
import au.com.trackingcentral.dto.LiveTrackingDeviceMetaDTO;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class LiveTrackingDeviceMetaService {

	private GetLiveTrackingDeviceMetaByCommand getLiveTrackingDeviceMetaByCommand;

	public void setGetLiveTrackingDeviceMetaByCommand(
			GetLiveTrackingDeviceMetaByCommand getLiveTrackingDeviceMetaByCommand) {
		this.getLiveTrackingDeviceMetaByCommand = getLiveTrackingDeviceMetaByCommand;
	}

	public LiveTrackingDeviceMetaDTO getMetaByCommand(String command) {
		return getLiveTrackingDeviceMetaByCommand._execute(command);
	}
}
