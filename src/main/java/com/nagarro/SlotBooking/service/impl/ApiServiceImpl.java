package com.nagarro.SlotBooking.service.impl;

import com.nagarro.SlotBooking.constants.ApiConstants;
import com.nagarro.SlotBooking.constants.SlotConstants;
import com.nagarro.SlotBooking.dto.*;
import com.nagarro.SlotBooking.exception.ApiException;
import com.nagarro.SlotBooking.service.ApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class ApiServiceImpl implements ApiService {

    @Value("${api.url}")
    private String apiUrl;

    @Value("${auth.url}")
    private String authUrl;

    @Value("${auth.clientId}")
    private String clientId;

    @Value("${auth.clientSecret}")
    private String clientSecret;

    private final String[] headerValues = {"x-client-id","x-client-version","x-account-id","x-account-name","x-company-id","x-company-name"};
    private final List<String> headerValuesList = Arrays.asList(headerValues);

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map<String, Object> forwardRequest(UserRequestDto userRequest, Map<String, String> headers) {
        try {
            String accessToken = fetchAccessToken();
            HttpHeaders httpHeaders = buildHeaders(headers, accessToken);
            Map<String, Object> requestBody = buildRequestBody(userRequest);

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, httpHeaders);
            ResponseEntity<Map> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    requestEntity,
                    Map.class
            );
            return response.getBody();
        } catch (Exception e) {
            throw new ApiException("Failed to forward the request: " + e.getMessage());
        }
    }

    private String fetchAccessToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<Map> response = restTemplate.exchange(
                authUrl,
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            return (String) responseBody.get("access_token");
        } else {
            throw new ApiException("Failed to fetch access token");
        }

    }

    private HttpHeaders buildHeaders(Map<String, String> headers, String accessToken) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            if(headerValuesList.contains(entry.getKey())){
                httpHeaders.set(entry.getKey(), entry.getValue());
            }
        }
        httpHeaders.setBearerAuth(accessToken);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    private Map<String, Object> buildRequestBody(UserRequestDto userRequest) {
        Map<String, Object> requestBody = new HashMap<>();

        requestBody.put("policy", ApiConstants.POLICY);

        Map<String, Object> job = new HashMap<>();
        job.put("durationMinutes", ApiConstants.DURATION_MINUTES);
        Map<String, Object> location = new HashMap<>();
        location.put("latitude", userRequest.getLocation().getLatitude());
        location.put("longitude", userRequest.getLocation().getLongitude());
        job.put("location", location);
        job.put("mandatorySkills", new String[]{});
        job.put("optionalSkills", new String[]{});
        Map<String, String> udfValues = new HashMap<>();
        udfValues.put("UDF_MAX_RATING", ApiConstants.UDF_MAX_RATING);
        udfValues.put("UDF_MIN_RATING", ApiConstants.UDF_MIN_RATING);
        job.put("udfValues", udfValues);
        requestBody.put("job", job);

        Map<String, Object> resources = new HashMap<>();
        Map<String, Object> filters = new HashMap<>();
        filters.put("includeInternalPersons", ApiConstants.INCLUDE_INTERNAL_PERSONS);
        filters.put("includeCrowdPersons", ApiConstants.INCLUDE_CROWD_PERSONS);
        filters.put("includeMandatorySkills", ApiConstants.INCLUDE_MANDATORY_SKILLS);
        resources.put("filters", filters);
        requestBody.put("resources", resources);

        List<Map<String, String>> slots = generateSlots();
        requestBody.put("slots", slots);

        Map<String, Object> options = new HashMap<>();
        options.put("maxResultsPerSlot", ApiConstants.MAX_RESULTS_PER_SLOT);
        requestBody.put("options", options);

        return requestBody;
    }

    private List<Map<String, String>> generateSlots() {
        List<Map<String, String>> slots = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 1; i <= SlotConstants.SLOT_DAYS; i++) {
            LocalDateTime start = LocalDateTime.now().plusDays(i).withHour(SlotConstants.SLOT_START_HOUR).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime end = start.plusHours(SlotConstants.SLOT_DURATION_HOURS);

            Map<String, String> slot = new HashMap<>();
            slot.put("start", start.format(formatter) + "Z");
            slot.put("end", end.format(formatter) + "Z");
            slots.add(slot);
        }

        return slots;
    }


}
