package com.example.cleanway.domain.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportSpotDto {
    private Long spotNumber;
    @Schema(description = "제보번호")
    private Long reportNumber;
    private Double spotLat;
    private Double spotLng;
    private String spotName;

}
