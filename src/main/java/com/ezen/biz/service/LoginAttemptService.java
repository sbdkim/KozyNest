package com.ezen.biz.service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class LoginAttemptService {

	private static final int MAX_FAILED_ATTEMPTS = 5;
	private static final Duration LOCK_DURATION = Duration.ofMinutes(15);

	private final ConcurrentHashMap<String, AttemptState> attempts = new ConcurrentHashMap<>();

	public boolean isLocked(String key) {
		AttemptState state = attempts.get(key);
		if (state == null) {
			return false;
		}
		if (state.lockUntil != null && state.lockUntil.isAfter(Instant.now())) {
			return true;
		}
		if (state.lockUntil != null && state.lockUntil.isBefore(Instant.now())) {
			attempts.remove(key);
		}
		return false;
	}

	public void recordSuccess(String key) {
		attempts.remove(key);
	}

	public void recordFailed(String key) {
		attempts.compute(key, (k, state) -> {
			AttemptState next = (state == null) ? new AttemptState() : state;
			next.failedCount++;
			if (next.failedCount >= MAX_FAILED_ATTEMPTS) {
				next.lockUntil = Instant.now().plus(LOCK_DURATION);
				next.failedCount = 0;
			}
			return next;
		});
	}

	private static final class AttemptState {
		private int failedCount;
		private Instant lockUntil;
	}
}
