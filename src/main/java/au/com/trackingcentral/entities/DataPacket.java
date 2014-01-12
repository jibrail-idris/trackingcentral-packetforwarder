package au.com.trackingcentral.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Version;

import org.hibernate.annotations.Type;

@Entity
@SequenceGenerator(name = "DataPacketGenerator")
public class DataPacket {
	@Id
	@GeneratedValue(generator = "DataPacketGenerator", strategy = GenerationType.AUTO)
	private Integer id;

	@Column(nullable = false)
	@Type(type = "string")
	private String raw;

	@Column(nullable = true)
	@Type(type = "string")
	private String imei;
	
	@Column(nullable = false)
	@Type(type = "integer")
	private Integer imeiPosition;
	
	@Column(nullable = false)
	@Type(type = "boolean")
	private Boolean isError;
	
	@Column(nullable = false)
	@Type(type = "timestamp")
	private Date timestamp;

	@Version
	private Integer version;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRaw() {
		return raw;
	}

	public void setRaw(String raw) {
		this.raw = raw;
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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Boolean isError() {
		return isError;
	}

	public void setIsError(Boolean isError) {
		this.isError = isError;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

}
