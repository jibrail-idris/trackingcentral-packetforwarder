package au.com.trackingcentral.dao.queries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;

import au.com.trackingcentral.dto.LiveTrackingDeviceMetaDTO;

public class GetLiveTrackingDeviceMetaByCommand extends
		MappingSqlQuery<LiveTrackingDeviceMetaDTO> {

	private static final Logger logger = Logger
			.getLogger(GetLiveTrackingDeviceMetaByCommand.class);
	
	public GetLiveTrackingDeviceMetaByCommand(DataSource dataSource) {
		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("Tracker_Model, ");
		sql.append("Command_Title, ");
		sql.append("IMEI_Position, ");
		sql.append("Trailing_Character, ");
		sql.append("Fwd_To_Sec_Server, ");
		sql.append("Sec_Server_IP, ");
		sql.append("Sec_Server_Port ");
		sql.append("from Tracking_Device_Commands ");
		sql.append("where Command_Title = :command ");
		declareParameter(new SqlParameter("command", Types.VARCHAR));
		setDataSource(dataSource);
		setSql(sql.toString());
		compile();
	}

	public LiveTrackingDeviceMetaDTO _execute(String command) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("command", command);
		logger.info(paramMap);
		List<LiveTrackingDeviceMetaDTO> results = executeByNamedParam(paramMap);
		if (results.size() > 0) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	protected LiveTrackingDeviceMetaDTO mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		LiveTrackingDeviceMetaDTO liveTrackingDeviceMetaDTO = new LiveTrackingDeviceMetaDTO();
		liveTrackingDeviceMetaDTO
				.setTrackerModel(rs.getString("Tracker_Model"));
		liveTrackingDeviceMetaDTO
				.setCommandTitle(rs.getString("Command_Title"));
		liveTrackingDeviceMetaDTO.setImeiPosition(rs.getInt("IMEI_Position"));
		liveTrackingDeviceMetaDTO.setTrailingCharacter(rs
				.getString("Trailing_Character"));
		liveTrackingDeviceMetaDTO.setForwardToServer(rs.getBoolean("Fwd_To_Sec_Server"));
		liveTrackingDeviceMetaDTO.setSecondaryServerIP(rs.getString("Sec_Server_IP"));
		liveTrackingDeviceMetaDTO.setSecondaryServerPort(rs.getInt("Sec_Server_Port"));
		return liveTrackingDeviceMetaDTO;
	}

}
