package com.example.cleanway.mapper.report;


import com.example.cleanway.domain.dto.report.*;
import com.example.cleanway.domain.vo.report.ReportVo;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ReportMapper {
//    제보 리스트 조회
     List<ReportVo> selectReportList();

    //제보 글쓰기
     void reportInsert (CleanReportDto cleanReportDto);

//    제보 reportNumber 가지고 오기
     long getNextReportNumber();

//    제보 전체 카운트
     int selectTotal();

//    제보 스팟 등록
     void spotInsert(ReportSpotDto reportSpotDto);

//    내 장소 등록
     void mySpotInsert(CleanSpotDto cleanSpotDto);


}
