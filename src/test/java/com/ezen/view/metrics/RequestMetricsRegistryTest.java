package com.ezen.view.metrics;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;

public class RequestMetricsRegistryTest {

	@Test
	public void snapshotIncludesExpectedKeysAfterRecording() {
		RequestMetricsRegistry registry = RequestMetricsRegistry.getInstance();
		registry.record(200, 120, false);
		registry.record(404, 50, false);
		registry.record(500, 2000, true);

		Map<String, Object> metrics = registry.snapshot();
		assertTrue(metrics.containsKey("startedAtEpochMs"));
		assertTrue(metrics.containsKey("uptimeMs"));
		assertTrue(metrics.containsKey("totalRequests"));
		assertTrue(metrics.containsKey("slowRequests"));
		assertTrue(metrics.containsKey("clientErrors"));
		assertTrue(metrics.containsKey("serverErrors"));
		assertTrue(metrics.containsKey("avgLatencyMs"));
		assertTrue(metrics.containsKey("maxLatencyMs"));
	}
}
