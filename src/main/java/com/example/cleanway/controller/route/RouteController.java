package com.example.cleanway.controller.route;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/routes")
@RequiredArgsConstructor
@Tag(name = "Routes", description = "루트추천 API")
public class RouteController {

    @GetMapping("/recommend")
    public String recommendRoute(){
        String apiUrl="http://openapi.seoul.go.kr:8088/456d42476f6a656739325664504950/xml/walkDulaeInfo/1/21/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl,String.class);

        return "Recommended route";
    }

    // 인증키는 프로젝트 내에 환경 변수 등을 통해 관리하는 것이 좋습니다.
    private static final String API_KEY = "456d42476f6a656739325664504950";
    private static final String BASE_URL = "http://openapi.seoul.go.kr:8088/";
// 한강 지천길
    @GetMapping("/hangang/info")
    public ResponseEntity<String> getHangangInfo(
            @RequestParam("start") int startIndex,
            @RequestParam("end") int endIndex,
            @RequestParam("type") String fileType
    ) {
        String apiUrl = BASE_URL + API_KEY + "/" + fileType + "/walkHangangInfo/" + startIndex + "/" + endIndex;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
//    한강 지천길 위도 경도


}
