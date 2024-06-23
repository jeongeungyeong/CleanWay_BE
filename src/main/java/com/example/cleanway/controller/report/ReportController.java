package com.example.cleanway.controller.report;

import com.example.cleanway.domain.dto.report.*;
import com.example.cleanway.domain.dto.user.UserDto;
import com.example.cleanway.domain.vo.report.ReportVo;
import com.example.cleanway.service.report.ReportImgService;
import com.example.cleanway.service.report.ReportService;
import com.example.cleanway.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;



import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Report", description = "제보 API")
public class ReportController {
    private final ReportService reportService;
    private final ReportImgService reportImgService;
    private final UserService userService;

    //제보 리스트
    @GetMapping("/list")
    @Operation(summary = "제보 리스트 조회", description = "등록된 제보 목록을 조회합니다.")
    public List<ReportVo> reportList(){

        List<ReportVo> reportList = reportService.findReportList();

        return reportList;

    }



//    제보 등록
    @PostMapping("/add")
    @Operation(summary = "제보 등록", description = "새로운 제보를 등록합니다.")
    public ResponseEntity<String> addReport(@Valid @RequestBody ReportRequestDto reportRequestDto,
                                            BindingResult bindingResult,
                                            HttpServletRequest req) {

        Logger logger = LoggerFactory.getLogger(ReportController.class);

        logger.info("Received reportRequestDto: {}", reportRequestDto);

        // 토큰에서 이메일 정보 가져오기
        String userEmail = userService.getEmailFromToken(req);
        System.out.println("토큰에서 추출한 이메일: " + userEmail);

        // 토큰 검증 및 유저 정보 가져오기
        UserDto user = userService.findKakaoEmail(userEmail);


        try {
            // 사용자 번호 가져오기
            Long userNumber = user.getUserNumber();

            reportRequestDto.getCleanReportDto().setUserNumber(userNumber);
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
@PostMapping("/saveSpot/{spotNumber}")
@Operation(summary = "제보 장소 저장", description = "제보된 장소를 내 장소에 저장합니다.")
public ResponseEntity<String> saveSpot(@PathVariable Long spotNumber,
                                       HttpServletRequest req){
    // 토큰에서 이메일 정보 가져오기
    String userEmail = userService.getEmailFromToken(req);
    System.out.println("토큰에서 추출한 이메일: " + userEmail);

    // 토큰 검증 및 유저 정보 가져오기
    UserDto user = userService.findKakaoEmail(userEmail);

    // 사용자 번호 가져오기
    Long userNumber = user.getUserNumber();

    CleanSpotDto cleanSpotDto = new CleanSpotDto();
    cleanSpotDto.setSpotNumber(spotNumber);
    cleanSpotDto.setUserNumber(userNumber);

    try{reportService.mySpotRegister(cleanSpotDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("내 장소에 성공적으로 등록됐습니다.");
    } catch (Exception e){
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("내 장소에 저장을 실패했습니다.");
    }
}

}
