package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NaverLoginController {

    @Value("${naver.client-id}")
    private String clientId;

    @Value("${naver.client-secret}")
    private String clientSecret;

    @Value("${naver.redirect-uri}")
    private String redirectUri;

    @Value("${naver.state}")
    private String state;

    @GetMapping("/login/naver")
    public String redirectToNaverLogin() {
        String naverAuthUrl = "https://nid.naver.com/oauth2.0/authorize"
                + "?response_type=code"
                + "&client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&state=" + state;

        return "<script>location.href='" + naverAuthUrl + "'</script>";
    }

    @GetMapping("/login/naver/callback")
    public String handleNaverCallback(@RequestParam String code, @RequestParam String state) throws Exception {
        // access token 요청
        String tokenUrl = "https://nid.naver.com/oauth2.0/token" +
                "?grant_type=authorization_code" +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&code=" + code +
                "&state=" + state;

        // access token 요청
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.getForEntity(tokenUrl, Map.class);
        String accessToken = (String) response.getBody().get("access_token");

        // 사용자 정보 요청
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> userInfo = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> responseMap = (Map<String, Object>) userInfo.getBody().get("response");
        String email = (String) responseMap.get("email");

        // 로그인/회원가입 처리 로직
        return "네이버 로그인 성공! 이메일: " + email;
    }

}
