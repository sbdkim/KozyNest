package com.ezen.view.metrics;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public final class RequestMetricsRegistry {

	private static final RequestMetricsRegistry INSTANCE = new RequestMetricsRegistry();

	private final long startedAtEpochMs = System.currentTimeMillis();
	private final LongAdder totalRequests = new LongAdder();
	private final LongAdder slowRequests = new LongAdder();
	private final LongAdder clientErrors = new LongAdder();
	private final LongAdder serverErrors = new LongAdder();
	private final LongAdder totalLatencyMs = new LongAdder();
	private final AtomicLong maxLatencyMs = new AtomicLong(0L);

	private RequestMetricsRegistry() {
	}

	public static RequestMetricsRegistry getInstance() {
		return INSTANCE;
	}

	public void record(int status, long elapsedMs, boolean slowRequest) {
		totalRequests.increment();
		totalLatencyMs.add(Math.max(0L, elapsedMs));
		if (slowRequest) {
			slowRequests.increment();
		}
		if (status >= 400 && status < 500) {
			clientErrors.increment();
		}
		if (status >= 500) {
			serverErrors.increment();
		}
		maxLatencyMs.accumulateAndGet(Math.max(0L, elapsedMs), Math::max);
	}

	public Map<String, Object> snapshot() {
		long total = totalRequests.sum();
		long totalLatency = totalLatencyMs.sum();
		long avgLatency = total == 0 ? 0L : totalLatency / total;

		Map<String, Object> metrics = new LinkedHashMap<String, Object>();
		metrics.put("startedAtEpochMs", startedAtEpochMs);
		metrics.put("uptimeMs", Math.max(0L, System.currentTimeMillis() - startedAtEpochMs));
		metrics.put("totalRequests", total);
		metrics.put("slowRequests", slowRequests.sum());
		metrics.put("clientErrors", clientErrors.sum());
		metrics.put("serverErrors", serverErrors.sum());
		metrics.put("avgLatencyMs", avgLatency);
		metrics.put("maxLatencyMs", maxLatencyMs.get());
		return metrics;
	}
}
