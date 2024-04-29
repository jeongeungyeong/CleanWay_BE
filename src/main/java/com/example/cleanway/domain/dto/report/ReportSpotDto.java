package com.example.cleanway.domain.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportSpotDto {
    private Long spotNumber;
    private Double spotLat;
    private Double spotIng;
    private String spotName;
}
