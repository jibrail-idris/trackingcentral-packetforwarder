package au.com.trackingcentral.utilities;

import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import wiremock.org.apache.http.client.utils.URIBuilder;
import au.com.trackingcentral.dto.DataPacketDTO;

public class ProcessHttpCall {

	private static final Logger logger = Logger
			.getLogger(ProcessHttpCall.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private String processServer;
	private Integer processPort;
	private String processURI;

	public void setProcessServer(String processServer) {
		this.processServer = processServer;
	}

	public void setProcessPort(Integer processPort) {
		this.processPort = processPort;
	}

	public void setProcessURI(String processURI) {
		this.processURI = processURI;
	}

	public void triggerProcess(DataPacketDTO dataPacketDTO) {
		if (dataPacketDTO != null) {
			URIBuilder builder = new URIBuilder();
			builder.setScheme("http").setHost(processServer)
					.setPort(processPort).setPath(processURI);
			builder.addParameter("command", dataPacketDTO.getCommand());
			builder.addParameter("imei", dataPacketDTO.getImei());
			builder.addParameter("imeiPosition", dataPacketDTO
					.getImeiPosition().toString());
			builder.addParameter("raw", dataPacketDTO.getRaw());
			builder.addParameter("isError", dataPacketDTO.isError().toString());
			builder.addParameter("timeStamp",
					dateFormat.format(dataPacketDTO.getTimeStamp()));

			try {
				HttpGet httpGet = new HttpGet(builder.build());
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httpGet);
				logger.info(response);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
