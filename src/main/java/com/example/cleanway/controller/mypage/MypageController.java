package com.example.cleanway.controller.mypage;

import com.example.cleanway.domain.dto.route.CleanRouteDto;
import com.example.cleanway.domain.vo.mypage.MyRouteVo;
import com.example.cleanway.domain.vo.mypage.MySpotVo;
import com.example.cleanway.service.mypage.MypageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
@Tag(name="My Page",description = "마이페이지 API")
public class MypageController {
    final MypageService mypageService;

//    메인 화면 보기

//    내 장소 보기
    @GetMapping("/myspot")
    @Operation(summary = "내 장소 리스트 조회", description = "저장한 제보 스팟을 조회합니다.")
    public List<MySpotVo> mySpotList (@RequestParam("userNumber") Long userNumber){
        return mypageService.mySpotList(userNumber);
    }
//    내 루트 보기
@GetMapping("/myroute")
@Operation(summary = "내 루트 리스트 조회", description = "저장한 루트를 조회합니다.")
public List<CleanRouteDto> myRouteList (@RequestParam("userNumber") Long userNumber){
    return mypageService.myRouteList(userNumber);
}

// 참여한 플로깅
}
