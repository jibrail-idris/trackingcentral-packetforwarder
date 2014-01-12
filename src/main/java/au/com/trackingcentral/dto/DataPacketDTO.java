package au.com.trackingcentral.dto;

import java.util.Date;

public class DataPacketDTO {
	
	public static final int ERROR = -1;
	
	private Integer packetId;
	private String command;
	private String imei;
	private Integer imeiPosition;
	private String raw;
	private Boolean isError;
	private Date timeStamp;

	@Override
	public String toString() {
		return new StringBuilder(super.toString()).append("::").append(raw)
				.append("::").append(packetId).append("::").append(isError)
				.toString();
	}

	public Integer getPacketId() {
		return packetId;
	}

	public void setPacketId(Integer packetId) {
		this.packetId = packetId;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getImeiPosition() {
		return imeiPosition;
	}

	public void setImeiPosition(Integer imeiPosition) {
		this.imeiPosition = imeiPosition;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
	}

	public Boolean isError() {
		return isError;
	}

	public void setError(Boolean isError) {
		this.isError = isError;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
}
