package com.example.cleanway.mapper.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CrewProjectMapper {
    //    크루 프로젝트 모집 글쓰기
//    최초 모집은 프로젝트 모집도 함께 진행
    public void crewProjectInsert(CleanCrewProjectDto cleanCrewProjectDto);
// 크루 프로젝트 참여하기
    public void projectJoinInsert(CleanMyProjectDto cleanMyProjectDto);
}
