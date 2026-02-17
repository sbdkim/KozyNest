package com.ezen.biz.service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class PasswordResetTokenService {

	private static final Duration TOKEN_TTL = Duration.ofMinutes(10);
	private final ConcurrentHashMap<String, ResetToken> tokenStore = new ConcurrentHashMap<>();

	public String issueToken(String accountType, String accountId) {
		String token = UUID.randomUUID().toString().replace("-", "");
		ResetToken resetToken = new ResetToken(accountType, accountId, Instant.now().plus(TOKEN_TTL));
		tokenStore.put(token, resetToken);
		return token;
	}

	public boolean consumeToken(String token, String accountType, String accountId) {
		ResetToken stored = tokenStore.get(token);
		if (stored == null) {
			return false;
		}
		boolean valid = stored.accountType.equals(accountType) && stored.accountId.equals(accountId)
				&& stored.expiresAt.isAfter(Instant.now());
		if (valid) {
			tokenStore.remove(token);
		}
		return valid;
	}

	private static final class ResetToken {
		private final String accountType;
		private final String accountId;
		private final Instant expiresAt;

		private ResetToken(String accountType, String accountId, Instant expiresAt) {
			this.accountType = accountType;
			this.accountId = accountId;
			this.expiresAt = expiresAt;
		}
	}
}
