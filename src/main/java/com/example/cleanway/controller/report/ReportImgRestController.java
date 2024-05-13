package com.example.cleanway.controller.report;

import com.example.cleanway.domain.dto.report.ReportImgDto;
import com.example.cleanway.service.report.ReportImgService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reportImg")
@Tag(name = "ReportImg", description = "제보 이미지 API")
public class ReportImgRestController {
    private final ReportImgService reportImgService;

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/img")
    public ReportImgDto img(@RequestParam("reportNumber") Long reportNumber){
        return reportImgService.findReportImg(reportNumber);
    }

    @GetMapping("/display")
   public ResponseEntity<byte[]> display(@RequestParam("fileFullName") String fileFullName)throws IOException{
        File file = new File(fileDir, fileFullName);
        byte[] fileContent = FileUtils.readFileToByteArray(file);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(fileContent);
    }

}
