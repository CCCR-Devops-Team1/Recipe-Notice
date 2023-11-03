package com.recipe.recipearticle.global.service;

import static com.recipe.recipearticle.global.dto.response.ResponseStatus.FAIL_JSON_MAPPING;
import static com.recipe.recipearticle.global.dto.response.ResponseStatus.FAIL_JSON_PROCESS;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.recipe.recipearticle.global.exeption.BaseException;
import com.recipe.recipearticle.member.dto.MemberInfo;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateService {
    @Value("${recipe.member.host}")
    private String host;
    @Value("${recipe.member.port}")
    private int port;

    public MemberInfo getMemberId(HttpServletRequest request) {
        URI uri = UriComponentsBuilder
                .fromUriString(String.format("%s:%d", host, port))
                .path("/user")
                .encode()
                .build()
                .toUri();
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + request.getHeader("Authorization"));

        HttpEntity<MultiValueMap<String, String>> memberIdRequest = new HttpEntity(header);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                memberIdRequest,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        MemberInfo memberInfo = null;
        try {
            memberInfo = objectMapper.readValue(responseEntity.getBody(), MemberInfo.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
            throw new BaseException(FAIL_JSON_MAPPING);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BaseException(FAIL_JSON_PROCESS);
        }

        return memberInfo;
    }
}
