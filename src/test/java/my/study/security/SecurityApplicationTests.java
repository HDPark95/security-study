package my.study.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SecurityApplicationTests {

    @BeforeEach
    void setUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("SecurityContextHolder Authentication Test")
    void authenticationIsSetInSecurityContextHolder() {
        //Given
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        //When
        Authentication authentication =
                new TestingAuthenticationToken("username", "password", "ROLE_USER");
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        SecurityContextHolder.getContext().getAuthentication().isAuthenticated();

        //then
        assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated(), "인증된 상태.");
    }

    @Test
    @DisplayName("Access Currently Authenticated User")
    void accessCurrentlyAuthenticatedUser() {
        //Given
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        //When
        Authentication testingAuthentication =
                new TestingAuthenticationToken("username", "password", "ROLE_USER");
        securityContext.setAuthentication(testingAuthentication);

        SecurityContextHolder.setContext(securityContext);

        //then
        Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();

        assertTrue(currentAuthentication.isAuthenticated(), "인증된 상태.");
        //사용자 명 가져오기
        assertTrue(currentAuthentication.getName().equals("username"), "사용자명 확인");
        //사용자 권한 가져오기
        assertTrue(currentAuthentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER")), "사용자 권한 확인");
    }
}