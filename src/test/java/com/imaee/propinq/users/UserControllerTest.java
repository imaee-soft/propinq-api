package com.imaee.propinq.users;

import com.imaee.propinq.users.controllers.implementations.UserController;
import com.imaee.propinq.users.controllers.responses.UserResponse;
import com.imaee.propinq.users.services.interfaces.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @MockBean
    private com.imaee.propinq.users.services.interfaces.IUserActivationService userActivationService;
    
    // Mock logging repository used by application-wide interceptor so WebMvcTest context can start
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
    void getUserDoesNotExposeDniOrCuit() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponse resp = UserResponse.builder()
                .birthDate(LocalDate.of(1990,1,1))
                .firstName("T")
                .lastName("U")
                .email("a@b.c")
                .address("Addr")
                .phoneNumber("1234567890")
                .build();

        when(userService.getUser(id)).thenReturn(resp);

        mockMvc.perform(get("/api/v1/users/" + id.toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").doesNotExist())
                .andExpect(jsonPath("$.cuit").doesNotExist())
                .andExpect(jsonPath("$.email").value("a@b.c"));
    }
}
