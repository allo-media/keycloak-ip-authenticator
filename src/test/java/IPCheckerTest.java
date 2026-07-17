import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.github.uhlive.keycloak.ipauthenticator.IPChecker;

class IPCheckerTests {
	private static final String remoteIPAddress = "1.2.3.4";

	@Test
	void emptyAllowedIPs() {
		IPChecker checker = new IPChecker();
		List<String> allowedIPs = List.of();
		assertTrue(checker.is_allowed(allowedIPs, remoteIPAddress));
	}

	@Test
	void notAllowedIPs() {
		IPChecker checker = new IPChecker();
		List<String> allowedIPs = List.of("2.2.2.2");
		assertFalse(checker.is_allowed(allowedIPs, remoteIPAddress));
	}

	@Test
	void oneAllowedIP() {
		IPChecker checker = new IPChecker();
		List<String> allowedIPs = List.of("1.2.3.4");
		assertTrue(checker.is_allowed(allowedIPs, remoteIPAddress));
	}
}
