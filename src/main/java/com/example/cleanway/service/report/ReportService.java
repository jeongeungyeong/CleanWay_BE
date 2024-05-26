package com.example.cleanway.service.report;

import com.example.cleanway.domain.dto.report.*;
import com.example.cleanway.domain.vo.report.ReportVo;
import com.example.cleanway.mapper.report.ReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {
    private final ReportMapper reportMapper;
    private final ReportImgService reportImgService;

    // 제보 리스트 조회
    public List<ReportVo> findReportList() {
        List<ReportVo> reportList = reportMapper.selectReportList();

        // 각 제보 항목에 대한 이미지 URL 설정
        for (ReportVo report : reportList) {
            ReportImgDto reportImgDto = reportImgService.findReportImg(report.getReportNumber());
            if (reportImgDto != null) {
                String imageUrl = reportImgService.generateImgUrl(reportImgDto);
                report.setImageUrl(imageUrl);
            }
        }
        return reportList;}

//    제보 등록
public void reportRegister(CleanReportDto cleanReportDto) {
        reportMapper.reportInsert(cleanReportDto);
}

//    제보 스팟 등록
        public void spotRegister(ReportSpotDto reportSpotDto){
        reportMapper.spotInsert(reportSpotDto);}

//    내 장소 등록
    public void mySpotRegister(CleanSpotDto cleanSpotDto){
        reportMapper.mySpotInsert(cleanSpotDto);
    }
}

