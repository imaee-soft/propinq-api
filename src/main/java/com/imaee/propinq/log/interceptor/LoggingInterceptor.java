package com.imaee.propinq.log.interceptor;

import com.imaee.propinq.log.data.Log;
import com.imaee.propinq.log.data.LogRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ua_parser.Parser;

@Component
@AllArgsConstructor
public class LoggingInterceptor implements HandlerInterceptor {

    private final LogRepository logRepository;
    private final Parser parser;

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ) {
        final var userAgent = request.getHeader("User-Agent");
        final var client = parser.parse(userAgent);

        logRepository.save(
                Log.builder()
                        .httpMethod(request.getMethod())
                        .requestURI(request.getRequestURI())
                        .browser(client.userAgent.family)
                        .os(client.os.family)
                        .device(client.device.family)
                        .build()
        );
    }
}