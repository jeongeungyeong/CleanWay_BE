package com.example.cleanway.controller.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${openapi.base-url}")
    private String baseUrl;

    @Value("${openapi.api-key}")
    private String apiKey;

    @GetMapping("/recommend")
    @Operation(summary = "한강 지천길 코스", description = "한강 지천길 코스 api")
    public String recommendRoute(){
        String apiUrl="http://openapi.seoul.go.kr:8088/456d42476f6a656739325664504950/xml/walkHangangInfo/1/33/";
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(apiUrl,String.class);

        return "Recommended route";
    }

// 한강 지천길
    @GetMapping("/hangang/info")
    @Operation(summary = "한강 지천길 코스", description = "한강 지천길 코스 api")
    public ResponseEntity<String> getHangangInfo() {
        String apiUrl = baseUrl + "/" + apiKey + "/" + "json" + "/walkHangangInfo/" + 1 + "/" + 33;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


    //    한강 지천길 위도 경도
    @GetMapping("/hangang/pointInfo")
    @Operation(summary = "한강 지천길 포인트 장소 좌표", description = "한강 지천길 포인트 장소 좌표 api")
    public ResponseEntity<String> getHangangPoint() {
    String apiUrl = baseUrl + "/" + apiKey + "/" + "json" + "/SdeDoDreamWay04PW/" + 1 + "/" + 380;

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);

    return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
}




}
