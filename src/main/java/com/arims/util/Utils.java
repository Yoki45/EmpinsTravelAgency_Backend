package com.arims.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
	private Utils() {

	}

	public static Date getCurrentDate() {
		return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

	}

}
