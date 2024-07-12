package com.nagarro.SlotBooking.service.impl;

import com.nagarro.SlotBooking.dto.*;
import com.nagarro.SlotBooking.exception.ApiException;
import com.nagarro.SlotBooking.service.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class ApiServiceImpl implements ApiService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${api.authorization}")
    private String authorization;

    @Value("${api.clientId}")
    private String clientId;

    @Value("${api.clientVersion}")
    private String clientVersion;

    @Value("${api.accountId}")
    private String accountId;

    @Value("${api.accountName}")
    private String accountName;

    @Value("${api.companyId}")
    private String companyId;

    @Value("${api.companyName}")
    private String companyName;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map<String, Object> forwardRequest(UserRequestDto userRequest) {
        try {
            HttpHeaders headers = buildHeaders();
            Map<String, Object> requestBody = buildRequestBody(userRequest);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Map.class);
            return response.getBody();
        } catch (Exception e) {
            throw new ApiException("Failed to forward the request: " + e.getMessage());
        }
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.set("X-Client-Id", clientId);
        headers.set("X-Client-Version", clientVersion);
        headers.set("X-Account-Id", accountId);
        headers.set("X-Account-Name", accountName);
        headers.set("X-Company-Id", companyId);
        headers.set("X-Company-Name", companyName);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private Map<String, Object> buildRequestBody(UserRequestDto userRequest) {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("policy", "Distance");

        Map<String, Object> job = new HashMap<>();
        job.put("durationMinutes", 60);
        Map<String, Object> location = new HashMap<>();
        location.put("latitude", userRequest.getLocation().getLatitude());
        location.put("longitude", userRequest.getLocation().getLongitude());
        job.put("location", location);
        job.put("mandatorySkills", new String[]{});
        job.put("optionalSkills", new String[]{});
        Map<String, String> udfValues = new HashMap<>();
        udfValues.put("UDF_MAX_RATING", "100");
        udfValues.put("UDF_MIN_RATING", "3");
        job.put("udfValues", udfValues);
        requestBody.put("job", job);

        Map<String, Object> resources = new HashMap<>();
        Map<String, Object> filters = new HashMap<>();
        filters.put("includeInternalPersons", true);
        filters.put("includeCrowdPersons", false);
        filters.put("includeMandatorySkills", false);
        resources.put("filters", filters);
        requestBody.put("resources", resources);

        requestBody.put("slots", userRequest.getSlots());

        Map<String, Object> options = new HashMap<>();
        options.put("maxResultsPerSlot", 1);
        requestBody.put("options", options);

        return requestBody;
    }

}
