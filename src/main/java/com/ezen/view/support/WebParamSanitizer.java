package com.ezen.view.support;

public final class WebParamSanitizer {

	private WebParamSanitizer() {
	}

	public static int parseInt(String value, int defaultValue, int min, int max) {
		int parsed = defaultValue;
		try {
			parsed = Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
		if (parsed < min) {
			return min;
		}
		if (parsed > max) {
			return max;
		}
		return parsed;
	}

	public static String normalizeKeyword(String value, int maxLength) {
		if (value == null) {
			return "";
		}
		String normalized = value.trim();
		if (normalized.length() > maxLength) {
			return normalized.substring(0, maxLength);
		}
		return normalized;
	}
}
