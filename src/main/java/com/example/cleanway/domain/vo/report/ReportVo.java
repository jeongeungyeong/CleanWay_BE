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
    private String reportContent;
    private String reportDate;
    private Long mySpotNumber;
    private Long reportImgNumber;
    private String reportImgName;
    private String reportImgPath;
    private String reportImgUuid;
    private Double spotLat;
    private Double spotIng;
    private String spotName;
    private String keywordName;
}
