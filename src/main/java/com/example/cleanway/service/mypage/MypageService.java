package com.example.cleanway.service.mypage;

import com.example.cleanway.domain.dto.route.CleanRouteDto;
import com.example.cleanway.domain.vo.mypage.MyRouteVo;
import com.example.cleanway.domain.vo.mypage.MySpotVo;
import com.example.cleanway.mapper.mypage.MypageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MypageService {
    private final MypageMapper mypageMapper;

    //    내 장소 목록 조회
    public List<MySpotVo> mySpotList(Long userNumber){
        return mypageMapper.selectMySpot(userNumber);
    }

    //    내 루트 목록 조회
    public List<CleanRouteDto> myRouteList(Long userNumber){
        return mypageMapper.selectRouteList(userNumber);
    }

}
