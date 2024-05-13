package com.example.cleanway.domain.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CleanReportDto {
    @Schema(description = "제보번호")
    private Long reportNumber;
    @Schema(description = "사용자 번호")
    private Long userNumber;
    @Schema(description = "제보 일자")
    private Long keywordNumber;
    @Schema(description = "제보 일자")
    private String reportDate;
}
