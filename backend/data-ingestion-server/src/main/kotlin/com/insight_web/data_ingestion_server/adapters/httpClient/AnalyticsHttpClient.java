package com.insight_web.data_ingestion_server.adapters.httpClient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.insight_web.data_ingestion_server.adapters.httpClient.dto.AnalyticsSiteResponse;
import com.insight_web.data_ingestion_server.usecase.analyticsIntegration.AnalyticsClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@RequiredArgsConstructor
public class AnalyticsHttpClient implements AnalyticsClient {
    private HttpClient client = HttpClient.newHttpClient();
    private String url = "http://localhost:8080/v1/analytics/site/";
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public Boolean isSiteExisted(String sitedId) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url + sitedId))
                .GET()
                .build();

        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            var result = mapper.readValue(response.body(), AnalyticsSiteResponse.class);
            return result.isExist();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
