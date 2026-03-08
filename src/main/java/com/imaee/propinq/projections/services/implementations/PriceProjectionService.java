package com.imaee.propinq.projections.services.implementations;

import com.imaee.propinq.projections.responses.ProjectionWrapper;
import com.imaee.propinq.projections.services.IPriceProjectionService;
import com.imaee.propinq.rents.data.enums.RaiseIndex;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

@Service
@RequiredArgsConstructor
public class PriceProjectionService implements IPriceProjectionService {

    @Value("${arquiler.calculation-url}")
    private String url;

    @Value("${arquiler.api-key}")
    private String key;

    @Value("${arquiler.api-host}")
    private String host;

    private final RestClient restClient;

    @Override
    public ProjectionWrapper calculateProjection(
            Double price,
            LocalDate initialDate,
            Integer months,
            RaiseIndex index
    ) {
        final var body = buildBody(price, initialDate, months, index);
        return restClient.post()
                .uri(url)
                .contentType(APPLICATION_FORM_URLENCODED)
                .body(body)
                .header("X-RapidAPI-Key", key)
                .header("X-RapidAPI-Host", host)
                .retrieve()
                .body(ProjectionWrapper.class);
    }

    private MultiValueMap<String, String> buildBody(
            Double price,
            LocalDate initialDate,
            Integer months,
            RaiseIndex index
    ) {
        final MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("amount", price.toString());
        body.add("date", initialDate.toString());
        body.add("months", months.toString());
        body.add("rate", index.getRate());
        return body;
    }
}