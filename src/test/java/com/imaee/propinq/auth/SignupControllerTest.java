package com.imaee.propinq.auth;

import com.imaee.propinq.auth.controllers.implementations.AuthController;
import com.imaee.propinq.auth.controllers.requests.SignUpRequest;
import com.imaee.propinq.auth.services.interfaces.IAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAuthService authService;

    @MockBean
    private com.imaee.propinq.log.data.LogRepository logRepository;

    // Provide a real ua_parser.Parser bean and LoggingInterceptor to avoid Mockito inline mock issues on newer JVMs
    @org.springframework.boot.test.context.TestConfiguration
    static class UaParserConfig {
        @org.springframework.context.annotation.Bean
        public ua_parser.Parser uaParser() {
            return new ua_parser.Parser();
        }

        @org.springframework.context.annotation.Bean
        public com.imaee.propinq.log.interceptor.LoggingInterceptor loggingInterceptor(
                com.imaee.propinq.log.data.LogRepository logRepository,
                ua_parser.Parser uaParser
        ) {
            return new com.imaee.propinq.log.interceptor.LoggingInterceptor(logRepository, uaParser);
        }
    }

    @Test
    void signupWithoutDniOrCuitAccepted() throws Exception {
        String payload = "{\n" +
                "  \"birthDate\": \"1990-01-01\",\n" +
                "  \"password\": \"secret1\",\n" +
                "  \"firstName\": \"Test\",\n" +
                "  \"lastName\": \"User\",\n" +
                "  \"email\": \"test.signup@example.com\",\n" +
                "  \"address\": \"Calle 1\",\n" +
                "  \"phoneNumber\": \"+541112345678\"\n" +
                "}";

        mockMvc.perform(post("/api/v1/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated());

        ArgumentCaptor<SignUpRequest> captor = ArgumentCaptor.forClass(SignUpRequest.class);
        verify(authService).signUp(captor.capture());
        SignUpRequest req = captor.getValue();
        assertThat(req.email()).isEqualTo("test.signup@example.com");
    }
}
