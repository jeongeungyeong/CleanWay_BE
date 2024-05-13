package com.example.cleanway.service.report;

import com.example.cleanway.domain.dto.report.ReportImgDto;
import com.example.cleanway.mapper.report.ReportImgMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ReportImgService {
    private final ReportImgMapper reportImgMapper;

    @Value("${file.dir}")
    private String fileDir;



    public void registerImg(ReportImgDto reportImgDto){
        reportImgMapper.insertImg(reportImgDto);
    }

    public ReportImgDto findReportImg(Long reportNumber){
        return reportImgMapper.selectReportImg(reportNumber);
    }

    private String getUploadPath(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        return sdf.format(new Date());
    }

    public void saveFileFromBase64(String base64Image, Long reportNumber) throws IOException {

        //Base64 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

//       이미지 파일로 저장
        String fileName = UUID.randomUUID().toString() + "_"+ reportNumber + ".png"; // 확장자가 png인 이미지 파일로 지정
        String filePath = Paths.get(fileDir, getUploadPath(), fileName).toString();

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decodedBytes);
        }catch (IOException e) {
            e.printStackTrace();
            // 예외 처리
            return;
        }
        // 이미지 정보 등록
        ReportImgDto reportImgDto = new ReportImgDto();
        reportImgDto.setReportImgName(fileName);
        reportImgDto.setReportImgPath(getUploadPath());
        reportImgDto.setReportNumber(reportNumber);
        registerImg(reportImgDto);
    }


    //    이미지 URL 생성
    public String generateImgUrl(ReportImgDto reportImgDto){
        return "/reportImg/display?fileFullName=" + reportImgDto.getReportImgPath()+"/"+reportImgDto.getReportImgName();
    }
}

