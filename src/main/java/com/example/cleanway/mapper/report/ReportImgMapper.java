package com.example.cleanway.mapper.report;
import com.example.cleanway.domain.dto.report.ReportImgDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface ReportImgMapper {
//이미지 사진 파일 등록
     void insertImg(ReportImgDto reportImgDto);

//    이미지
     ReportImgDto selectReportImg(Long reportNumber);
}
