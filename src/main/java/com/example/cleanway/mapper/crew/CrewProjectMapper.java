package com.example.cleanway.mapper.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyCrewDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import com.example.cleanway.domain.dto.crew.CrewRecommendDto;
import com.example.cleanway.domain.vo.crew.CrewMemberVo;
import com.example.cleanway.domain.vo.crew.CrewTeamVo;
import com.example.cleanway.domain.vo.crew.CrewTop3Vo;
import com.example.cleanway.domain.vo.crew.ProjectMemberVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CrewProjectMapper {

    // 크루 프로젝트 모집 글쓰기
   /* 최초 모집은 프로젝트 모집도 함께 진행*/
    public void crewProjectInsert(CleanCrewProjectDto cleanCrewProjectDto);
//    크루 이름 가져오기
String selectCrewName(Long crewNumber);

    // 크루 프로젝트 참여하기
    public void projectJoinInsert(CleanMyProjectDto cleanMyProjectDto);

    //   참여 크루 프로젝트 조회하기
    CleanMyProjectDto selectProjectJoin(Long crewProjectNumber, Long userNumber);

    // 특정 회원이 특정 크루에 참여했는지 검사 여부
    int selectProjectJoinCount (CleanMyProjectDto cleanMyProjectDto);

    //크루방 홈 리스트(예정 프로젝트)
    public List<CrewTeamVo> selectCrewProject(Long crewNumber);

//    크루방 TOP3
     public List<CrewTop3Vo> selectCrewTOP3(Long crewNumber);

    //    크루원 정보 조회
    public List<CrewMemberVo> selectCrewMemberByNum (Long crewNumber);

//    크루장 조회
    public CrewMemberVo selectCrewMemberByRole(Long crewNumber);


    // 크루원 강퇴
    public void deleteCrewMember(Long userNumber);

    //    크루 프로젝트 디테일
    public List<CrewTeamVo> selectProjectDetail(Long crewNumber, Long crewProjectNumber);

//    프로젝트 참여인원 정보 조회
    public List<ProjectMemberVo> selectProjectMemberByNum (Long crewNumber, Long crewProjectNumber);

//    프로젝트 참가자들이 특정 프로젝트 인증 추천했는지 여부 검사
    int selectProjectRecommend(CrewRecommendDto crewRecommendDto);

//    프로젝트 참가자들 인증 정보 삽입
    void insertProjectRecommend(CrewRecommendDto crewRecommendDto);

}
