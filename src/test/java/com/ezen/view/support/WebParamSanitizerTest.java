package com.ezen.view.support;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class WebParamSanitizerTest {

	@Test
	public void parseIntReturnsDefaultOnInvalidValue() {
		assertEquals(7, WebParamSanitizer.parseInt("abc", 7, 1, 10));
	}

	@Test
	public void parseIntAppliesLowerAndUpperBounds() {
		assertEquals(1, WebParamSanitizer.parseInt("-5", 7, 1, 10));
		assertEquals(10, WebParamSanitizer.parseInt("999", 7, 1, 10));
		assertEquals(5, WebParamSanitizer.parseInt("5", 7, 1, 10));
	}

	@Test
	public void normalizeKeywordTrimsAndLimitsLength() {
		assertEquals("", WebParamSanitizer.normalizeKeyword(null, 5));
		assertEquals("abc", WebParamSanitizer.normalizeKeyword("  abc  ", 5));
		assertEquals("abcde", WebParamSanitizer.normalizeKeyword("abcdefgh", 5));
	}
}
