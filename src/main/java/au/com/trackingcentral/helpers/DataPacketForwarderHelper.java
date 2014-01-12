package au.com.trackingcentral.helpers;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import au.com.trackingcentral.clients.DataPacketForwarder;

public class DataPacketForwarderHelper implements 
	ApplicationContextAware {
	private ApplicationContext applicationContext;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	public DataPacketForwarder createDataPacketForwarder() {
		DataPacketForwarder dataPacketForwarder = applicationContext
				.getBean(DataPacketForwarder.class);
		return dataPacketForwarder; 
	}
}
