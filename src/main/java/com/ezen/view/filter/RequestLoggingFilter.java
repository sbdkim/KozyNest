package com.ezen.view.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.ezen.biz.dto.HostVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.view.metrics.RequestMetricsRegistry;

public class RequestLoggingFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
	private static final long SLOW_REQUEST_THRESHOLD_MS = 1500L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// No-op
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		if (!(request instanceof HttpServletRequest)) {
			chain.doFilter(request, response);
			return;
		}

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = response instanceof HttpServletResponse ? (HttpServletResponse) response : null;
		String requestId = UUID.randomUUID().toString().replace("-", "");
		long startedAt = System.currentTimeMillis();
		int status = 200;

		MDC.put("requestId", requestId);
		MDC.put("method", httpRequest.getMethod());
		MDC.put("path", httpRequest.getRequestURI());
		MDC.put("userId", resolveUserId(httpRequest));

		try {
			chain.doFilter(request, response);
			if (httpResponse != null) {
				status = httpResponse.getStatus();
			}
		} catch (IOException ex) {
			status = 500;
			throw ex;
		} catch (ServletException ex) {
			status = 500;
			throw ex;
		} catch (RuntimeException ex) {
			status = 500;
			throw ex;
		} finally {
			long elapsedMs = System.currentTimeMillis() - startedAt;
			boolean slowRequest = elapsedMs >= SLOW_REQUEST_THRESHOLD_MS;
			RequestMetricsRegistry.getInstance().record(status, elapsedMs, slowRequest);
			if (slowRequest) {
				logger.warn("Slow request detected: {} {} completed in {} ms", httpRequest.getMethod(),
						httpRequest.getRequestURI(), elapsedMs);
			} else {
				logger.info("Request completed in {} ms with status {}", elapsedMs, status);
			}
			MDC.clear();
		}
	}

	@Override
	public void destroy() {
		// No-op
	}

	private String resolveUserId(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null) {
			return "anonymous";
		}

		Object loginUser = session.getAttribute("loginUser");
		if (loginUser instanceof MemberVO) {
			return ((MemberVO) loginUser).getEmail();
		}

		Object loginHost = session.getAttribute("loginHost");
		if (loginHost instanceof HostVO) {
			return ((HostVO) loginHost).getHemail();
		}

		return "anonymous";
	}
}
