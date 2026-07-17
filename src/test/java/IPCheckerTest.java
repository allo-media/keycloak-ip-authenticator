import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.uhlive.keycloak.ipauthenticator.IPChecker;

class IPCheckerTests {
	private static final String remoteIPAddress = "1.2.3.4";

	@Test
	void emptyAllowedIPs() {
		IPChecker checker = new IPChecker(List.of());
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void notAllowedIPs() {
		IPChecker checker = new IPChecker(List.of("2.2.2.2"));
		assertFalse(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void oneAllowedIP() {
		IPChecker checker = new IPChecker(List.of("1.2.3.4"));
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void multipleAllowedIPs() {
		IPChecker checker = new IPChecker(List.of("2.2.2.2", "1.2.3.4", "3.3.3.3"));
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void oneAllowedIPRange() {
		IPChecker checker = new IPChecker(List.of("1.2.3.0-5"));
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void multipleAllowedIPRange() {
		IPChecker checker = new IPChecker(List.of("5.6.7.8", "1.2.3.0-5", "9.9.9.0-120"));
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void allowedIPWildcard() {
		IPChecker checker = new IPChecker(List.of("1.2.3.*"));
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void outsideMultipleAllowedIPRange() {
		IPChecker checker = new IPChecker(List.of("5.6.7.8", "1.2.4.0-5", "9.9.9.0-120"));
		assertFalse(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void allowedIPPlusInvalidAllowedIPRestrictAccess() {
		IPChecker checker = new IPChecker(List.of("invalid", "1.2.3.4"));
		assertTrue(checker.isAllowed(remoteIPAddress));
	}

	@Test
	void invalidSingleAllowedIPRestrictAccess() {
		IPChecker checker = new IPChecker(List.of("invalid"));
		assertFalse(checker.isAllowed(remoteIPAddress));
	}
}
