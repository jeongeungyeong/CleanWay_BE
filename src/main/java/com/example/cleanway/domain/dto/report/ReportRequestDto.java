package com.example.cleanway.domain.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportRequestDto {
    private CleanReportDto cleanReportDto;
    private ReportSpotDto reportSpotDto;
    @Schema(description = "base64 인코딩된 이미지 문자열")
    private String base64EncodedImage;
}
