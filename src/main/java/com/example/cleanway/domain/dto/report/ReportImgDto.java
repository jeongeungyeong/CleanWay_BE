package com.example.cleanway.domain.dto.report;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReportImgDto {
    @Schema(description = "이미지번호")
    private Long reportImgNumber;
    @Schema(description = "이미지이름")
    private String reportImgName;
    @Schema(description = "이미지경로")
    private String reportImgPath;
    @Schema(description = "이미지uuid")
    private String reportImgUuid;
    @Schema(description = "제보번호")
    private Long reportNumber;
}
