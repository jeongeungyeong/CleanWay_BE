package com.example.cleanway.mapper.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewDto;
import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.vo.crew.CrewVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewMapper {
    // 크루 모집 리스트(홈)
    public  List<CrewVo> selectCrewList();

    //크루 모집 글쓰기
//    크루 작성자 = 자동 크루장
//    크루 모집인원 = 최초 프로젝트 일치
    public void crewInsert(CleanCrewDto cleanCrewDto);

//    크루 프로젝트 모집 글쓰기
//    최초 모집은 프로젝트 모집도 함께 진행
    public void crewProjectInsert(CleanCrewProjectDto cleanCrewProjectDto);

}
