package com.example.cleanway.domain.vo.report;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ReportVo {
    private Long reportNumber;
    private Long userNumber;
    private Long keywordNumber;
    private Long spotNumber;
    private String reportDate;
    private Double spotLat;
    private Double spotIng;
    private String spotName;
    private String keywordName;
    private Long reportImgNumber;
    private String reportImgName;
    private String reportImgPath;
    //이미지 url
    private String imageUrl;
}
