package com.example.cleanway.controller.route;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
