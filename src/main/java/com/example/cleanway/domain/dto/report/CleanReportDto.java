package com.example.cleanway.domain.dto.report;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CleanReportDto {
    private Long reportNumber;
    private Long userNumber;
    private Long keywordNumber;
    private Long spotNumber;
    private String reportContent;
    private String reportDate;
}
