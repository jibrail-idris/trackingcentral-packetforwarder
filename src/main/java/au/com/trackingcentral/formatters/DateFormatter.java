package au.com.trackingcentral.formatters;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

public class DateFormatter {

	public enum Type {
		MYSQL, GENERAL
	};

	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
			"dd-MM-yyyy HH:mm:ss");
	private static final SimpleDateFormat mysqlSimpleDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static SimpleDateFormat getDateFormatter(Type type) {
		switch (type) {
		case GENERAL:
			return simpleDateFormat;
		case MYSQL:
			return mysqlSimpleDateFormat;
		}

		return null;
	}

	public static Date format(Type type, Date date) throws ParseException {
		SimpleDateFormat dateFormatter = getDateFormatter(type);
		return dateFormatter.parse(dateFormatter.format(date));
	}

	public static Date parse(Type type, String dateString)
			throws ParseException {
		if (StringUtils.hasLength(dateString)) {
			SimpleDateFormat dateFormatter = getDateFormatter(type);
			return dateFormatter.parse(dateString);
		} else {
			return null;
		}
	}
}

