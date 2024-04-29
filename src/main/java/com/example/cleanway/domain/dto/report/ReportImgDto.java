package com.example.cleanway.domain.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportImgDto {
    private Long reportImgNumber;
    private String reportImgName;
    private String reportImgPath;
    private String reportImgUuid;
    private Long reportNumber;
}
