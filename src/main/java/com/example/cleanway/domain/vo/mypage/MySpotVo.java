package com.example.cleanway.domain.vo.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MySpotVo {
    private Long reportNumber;
    private Long userNumber;
    private Long spotNumber;
    private Double spotLat;
    private Double spotLng;
    private String spotName;
}
