package com.util.entityGeneratorV2.util;

public class StringUtil {

	public static String toCamelCase(String str) {
		String[] parts = str.split("_");
		String camelCaseString = "";
		for (String part : parts) {
			if (!"".equals(part)) {
				camelCaseString = camelCaseString + toProperCase(part);
			}
		}
		return camelCaseString;
	}

	public static String toProperCase(String str) {
		String result = str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
		return result;
	}

	public static String toHeadLowerCase(String str) {
		String result = str.substring(0, 1).toLowerCase() + str.substring(1);
		return result;
	}
}
