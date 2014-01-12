package au.com.trackingcentral.dto;

public class LiveTrackingDeviceMetaDTO {
	private String trackerModel;
	private String trackerModelDescription;
	private String commandTitle;
	private String commandNotes;
	private Integer imeiPosition;
	private String trailingCharacter;
	private boolean isForwardToServer; 
	private String secondaryServerIP;
	private int secondaryServerPort;
	
	public String getTrackerModel() {
		return trackerModel;
	}
	public void setTrackerModel(String trackerModel) {
		this.trackerModel = trackerModel;
	}
	public String getTrackerModelDescription() {
		return trackerModelDescription;
	}
	public void setTrackerModelDescription(String trackerModelDescription) {
		this.trackerModelDescription = trackerModelDescription;
	}
	public String getCommandTitle() {
		return commandTitle;
	}
	public void setCommandTitle(String commandTitle) {
		this.commandTitle = commandTitle;
	}
	public String getCommandNotes() {
		return commandNotes;
	}
	public void setCommandNotes(String commandNotes) {
		this.commandNotes = commandNotes;
	}
	public Integer getImeiPosition() {
		return imeiPosition;
	}
	public void setImeiPosition(Integer imeiPosition) {
		this.imeiPosition = imeiPosition;
	}
	public String getTrailingCharacter() {
		return trailingCharacter;
	}
	public void setTrailingCharacter(String trailingCharacter) {
		this.trailingCharacter = trailingCharacter;
	}
	public boolean isForwardToServer() {
		return isForwardToServer;
	}
	public void setForwardToServer(boolean isForwardToServer) {
		this.isForwardToServer = isForwardToServer;
	}
	public String getSecondaryServerIP() {
		return secondaryServerIP;
	}
	public void setSecondaryServerIP(String secondaryServerIP) {
		this.secondaryServerIP = secondaryServerIP;
	}
	public int getSecondaryServerPort() {
		return secondaryServerPort;
	}
	public void setSecondaryServerPort(int secondaryServerPort) {
		this.secondaryServerPort = secondaryServerPort;
	}
}
