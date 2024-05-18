package com.example.cleanway.controller.report;

import com.example.cleanway.domain.dto.report.CleanReportDto;
import com.example.cleanway.domain.dto.report.ReportKeywordDto;
import com.example.cleanway.domain.dto.report.ReportRequestDto;
import com.example.cleanway.domain.dto.report.ReportSpotDto;
import com.example.cleanway.domain.vo.report.ReportVo;
import com.example.cleanway.service.report.ReportImgService;
import com.example.cleanway.service.report.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Tag(name = "Report", description = "제보 API")
public class ReportController {
    private final ReportService reportService;
    private final ReportImgService reportImgService;

    //제보 리스트
    @GetMapping("/list")
    @Operation(summary = "제보 리스트 조회", description = "등록된 제보 목록을 조회합니다.")
    public List<ReportVo> reportList(){

        List<ReportVo> reportList = reportService.findReportList();

        return reportList;

    }

    @PostMapping("/add")
    @Operation(summary = "제보 등록", description = "새로운 제보를 등록합니다.")
    public ResponseEntity<String> addReport(@Valid @RequestBody ReportRequestDto reportRequestDto,
                                            BindingResult bindingResult) {

        try {
            // 제보등록
            reportService.reportRegister(reportRequestDto.getCleanReportDto());
            Long reportNumber = reportRequestDto.getCleanReportDto().getReportNumber();

            // 제보 이미지 등록
            reportImgService.saveFileFromBase64(reportRequestDto.getBase64EncodedImage(), reportNumber);

            // 위치 정보 등록
            reportRequestDto.getReportSpotDto().setReportNumber(reportNumber);
            reportService.spotRegister(reportRequestDto.getReportSpotDto());

            // 성공적으로 등록
            return ResponseEntity.status(HttpStatus.CREATED).body("제보가 성공적으로 등록됐습니다!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("제보 등록에 실패했습니다!");
        }
    }

//    제보 장소 즐겨찾기
}
