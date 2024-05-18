package com.example.cleanway.mapper.mypage;

import com.example.cleanway.domain.dto.route.CleanRouteDto;
import com.example.cleanway.domain.vo.mypage.MyRouteVo;
import com.example.cleanway.domain.vo.mypage.MySpotVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MypageMapper {
    //    내 장소 보기
    public List<MySpotVo> selectMySpot(Long userNumber);
    //    내 루트 보기
    public List<CleanRouteDto> selectRouteList(Long userNumber);
}
