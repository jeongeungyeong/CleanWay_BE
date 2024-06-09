package com.example.cleanway.service.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import com.example.cleanway.domain.dto.crew.CrewRecommendDto;
import com.example.cleanway.domain.dto.crew.ProjectRequestDto;
import com.example.cleanway.domain.vo.crew.CrewMemberVo;
import com.example.cleanway.domain.vo.crew.CrewTeamVo;
import com.example.cleanway.domain.vo.crew.CrewTop3Vo;
import com.example.cleanway.domain.vo.crew.ProjectMemberVo;
import com.example.cleanway.mapper.crew.CrewProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CrewProjectService {
    private final CrewProjectMapper crewProjectMapper;

//    크루방 상세 조회 (예정 프로젝트)
    public List<CrewTeamVo> findCrewProjectList(Long crewNumber){
        return crewProjectMapper.selectCrewProject(crewNumber);
    }

//    크루원 top3 조회
    public List<CrewTop3Vo> findCrewTOP3List(Long crewNumber){
        return crewProjectMapper.selectCrewTOP3(crewNumber);
    }

    // 크루원인지 확인하기
    public boolean isCrewMember(Long crewNumber, Long userNumber) {
        List<CrewMemberVo> members = crewProjectMapper.selectCrewMemberByNum(crewNumber);
        return members.stream().anyMatch(member -> member.getUserNumber().equals(userNumber));
    }
//크루장 조회하기
    public CrewMemberVo findCrewLeader(Long crewNumber){
        return crewProjectMapper.selectCrewMemberByRole(crewNumber);
    }


    
//     크루원 강퇴하기
    public void removeCrewMember(Long userNumber){
        crewProjectMapper.deleteCrewMember(userNumber);
    }
    
//    크루 삭제하기

//    크루 이름 가져오기
public String getCrewName(Long crewNumber) {
    return crewProjectMapper.selectCrewName(crewNumber);
}

    //크루 프로젝트 등록
  /*  public void crewProjectRegister(ProjectRequestDto projectRequestDto){
        CleanCrewProjectDto cleanCrewProjectDto = projectRequestDto.getCleanCrewProjectDto();

        List<Double> projectVLng = projectRequestDto.getProjectVLng();
        List<Double> projectVLat = projectRequestDto.getProjectVLat();
        List<String> projectVName = projectRequestDto.getProjectVName();
        List<String> projectTagList = projectRequestDto.getProjectTagList();

        if (projectVLng != null && projectVLat != null && projectVName != null) {
            if (projectVLng.size() > 0) cleanCrewProjectDto.setProjectVLng(projectVLng.get(0));
            if (projectVLat.size() > 0) cleanCrewProjectDto.setProjectVLat(projectVLat.get(0));
            if (projectVName.size() > 0) cleanCrewProjectDto.setProjectVName(projectVName.get(0));
            if(projectTagList.size()> 0) cleanCrewProjectDto.setProjectTag1(projectTagList.get(0));

            if (projectVLng.size() > 1) cleanCrewProjectDto.setProjectV2Lng(projectVLng.get(1));
            if (projectVLat.size() > 1) cleanCrewProjectDto.setProjectV2Lat(projectVLat.get(1));
            if (projectVName.size() > 1) cleanCrewProjectDto.setProjectV2Name(projectVName.get(1));
            if(projectTagList.size()>1) cleanCrewProjectDto.setProjectTag2(projectTagList.get(1));

            if (projectVLng.size() > 2) cleanCrewProjectDto.setProjectV3Lng(projectVLng.get(2));
            if (projectVLat.size() > 2) cleanCrewProjectDto.setProjectV3Lat(projectVLat.get(2));
            if (projectVName.size() > 2) cleanCrewProjectDto.setProjectV3Name(projectVName.get(2));
            if(projectTagList.size()>2) cleanCrewProjectDto.setProjectTag3(projectTagList.get(2));

            if(projectTagList.size()>3) cleanCrewProjectDto.setProjectTag4(projectTagList.get(3));
        } else{
            // 경유지 정보 기본값 설정
            if (cleanCrewProjectDto.getProjectVLng() == null) {
                cleanCrewProjectDto.setProjectVLng(0.0);
            }
            if (cleanCrewProjectDto.getProjectVLat() == null) {
                cleanCrewProjectDto.setProjectVLat(0.0);
            }
            if (cleanCrewProjectDto.getProjectVName() == null ||
                    cleanCrewProjectDto.getProjectVName().isEmpty()) {
                cleanCrewProjectDto.setProjectVName("경유지 없음");
            }
            if (cleanCrewProjectDto.getProjectV2Lng() == null) {
                cleanCrewProjectDto.setProjectV2Lng(0.0);
            }
            if (cleanCrewProjectDto.getProjectV2Lat() == null) {
                cleanCrewProjectDto.setProjectV2Lat(0.0);
            }
            if (cleanCrewProjectDto.getProjectV2Name() == null || cleanCrewProjectDto.getProjectV2Name().isEmpty()) {
                cleanCrewProjectDto.setProjectV2Name("경유지2 없음");
            }
            if (cleanCrewProjectDto.getProjectV3Lng() == null) {
                cleanCrewProjectDto.setProjectV3Lng(0.0);
            }
            if (cleanCrewProjectDto.getProjectV3Lat() == null) {
                cleanCrewProjectDto.setProjectV3Lat(0.0);
            }
            if (cleanCrewProjectDto.getProjectV3Name() == null || cleanCrewProjectDto.getProjectV3Name().isEmpty()) {
                cleanCrewProjectDto.setProjectV3Name("경유지3 없음");
            }
            if(cleanCrewProjectDto.getProjectTag1() == null || cleanCrewProjectDto.getProjectTag1().isEmpty()) {
                cleanCrewProjectDto.setProjectTag1("없음");
            }
            if(cleanCrewProjectDto.getProjectTag2() == null || cleanCrewProjectDto.getProjectTag2().isEmpty()) {
                cleanCrewProjectDto.setProjectTag2("없음");
            }
            if(cleanCrewProjectDto.getProjectTag3() == null || cleanCrewProjectDto.getProjectTag3().isEmpty()) {
                cleanCrewProjectDto.setProjectTag3("없음");
            }
            if(cleanCrewProjectDto.getProjectTag4() == null || cleanCrewProjectDto.getProjectTag4().isEmpty()) {
                cleanCrewProjectDto.setProjectTag4("없음");
            }
        }

        //       기본값 생성
        if (cleanCrewProjectDto.getProjectSLng() == null) cleanCrewProjectDto.setProjectSLng(0.0);
        if (cleanCrewProjectDto.getProjectSLat() == null) cleanCrewProjectDto.setProjectSLat(0.0);
        if (cleanCrewProjectDto.getProjectSName() == null || cleanCrewProjectDto.getProjectSName().isEmpty()) cleanCrewProjectDto.setProjectSName("출발지 없음");
        if (cleanCrewProjectDto.getProjectDLng() == null) cleanCrewProjectDto.setProjectDLng(0.0);
        if (cleanCrewProjectDto.getProjectDLat() == null) cleanCrewProjectDto.setProjectDLat(0.0);
        if (cleanCrewProjectDto.getProjectDName() == null || cleanCrewProjectDto.getProjectDName().isEmpty()) cleanCrewProjectDto.setProjectDName("도착지 없음");
        crewProjectMapper.crewProjectInsert(cleanCrewProjectDto);
         }*/
    public void crewProjectRegister(ProjectRequestDto projectRequestDto){
        CleanCrewProjectDto cleanCrewProjectDto = projectRequestDto.getCleanCrewProjectDto();

        List<Double> projectVLng = projectRequestDto.getProjectVLng();
        List<Double> projectVLat = projectRequestDto.getProjectVLat();
        List<String> projectVName = projectRequestDto.getProjectVName();
        List<String> projectTagList = projectRequestDto.getProjectTagList();

        // 입력값이 null이 아닌지 확인 후 처리
        if (projectVLng != null && projectVLat != null && projectVName != null && projectTagList != null) {
            if (projectVLng.size() > 0) cleanCrewProjectDto.setProjectVLng(projectVLng.get(0));
            if (projectVLat.size() > 0) cleanCrewProjectDto.setProjectVLat(projectVLat.get(0));
            if (projectVName.size() > 0) cleanCrewProjectDto.setProjectVName(projectVName.get(0));
            if (projectTagList.size() > 0) cleanCrewProjectDto.setProjectTag1(projectTagList.get(0));

            if (projectVLng.size() > 1) cleanCrewProjectDto.setProjectV2Lng(projectVLng.get(1));
            if (projectVLat.size() > 1) cleanCrewProjectDto.setProjectV2Lat(projectVLat.get(1));
            if (projectVName.size() > 1) cleanCrewProjectDto.setProjectV2Name(projectVName.get(1));
            if (projectTagList.size() > 1) cleanCrewProjectDto.setProjectTag2(projectTagList.get(1));

            if (projectVLng.size() > 2) cleanCrewProjectDto.setProjectV3Lng(projectVLng.get(2));
            if (projectVLat.size() > 2) cleanCrewProjectDto.setProjectV3Lat(projectVLat.get(2));
            if (projectVName.size() > 2) cleanCrewProjectDto.setProjectV3Name(projectVName.get(2));
            if (projectTagList.size() > 2) cleanCrewProjectDto.setProjectTag3(projectTagList.get(2));

            if (projectTagList.size() > 3) cleanCrewProjectDto.setProjectTag4(projectTagList.get(3));
        }

        // 리스트 기본값 설정
        if (cleanCrewProjectDto.getProjectVLng() == null) {
            cleanCrewProjectDto.setProjectVLng(0.0);
        }
        if (cleanCrewProjectDto.getProjectVLat() == null) {
            cleanCrewProjectDto.setProjectVLat(0.0);
        }
        if (cleanCrewProjectDto.getProjectVName() == null || cleanCrewProjectDto.getProjectVName().isEmpty()) {
            cleanCrewProjectDto.setProjectVName("경유지 없음");
        }
        if (cleanCrewProjectDto.getProjectV2Lng() == null) {
            cleanCrewProjectDto.setProjectV2Lng(0.0);
        }
        if (cleanCrewProjectDto.getProjectV2Lat() == null) {
            cleanCrewProjectDto.setProjectV2Lat(0.0);
        }
        if (cleanCrewProjectDto.getProjectV2Name() == null || cleanCrewProjectDto.getProjectV2Name().isEmpty()) {
            cleanCrewProjectDto.setProjectV2Name("경유지2 없음");
        }
        if (cleanCrewProjectDto.getProjectV3Lng() == null) {
            cleanCrewProjectDto.setProjectV3Lng(0.0);
        }
        if (cleanCrewProjectDto.getProjectV3Lat() == null) {
            cleanCrewProjectDto.setProjectV3Lat(0.0);
        }
        if (cleanCrewProjectDto.getProjectV3Name() == null || cleanCrewProjectDto.getProjectV3Name().isEmpty()) {
            cleanCrewProjectDto.setProjectV3Name("경유지3 없음");
        }
        if (cleanCrewProjectDto.getProjectTag1() == null || cleanCrewProjectDto.getProjectTag1().isEmpty()) {
            cleanCrewProjectDto.setProjectTag1("없음");
        }
        if (cleanCrewProjectDto.getProjectTag2() == null || cleanCrewProjectDto.getProjectTag2().isEmpty()) {
            cleanCrewProjectDto.setProjectTag2("없음");
        }
        if (cleanCrewProjectDto.getProjectTag3() == null || cleanCrewProjectDto.getProjectTag3().isEmpty()) {
            cleanCrewProjectDto.setProjectTag3("없음");
        }
        if (cleanCrewProjectDto.getProjectTag4() == null || cleanCrewProjectDto.getProjectTag4().isEmpty()) {
            cleanCrewProjectDto.setProjectTag4("없음");
        }

// 필수 기본값 설정
        if (cleanCrewProjectDto.getProjectSLng() == null) cleanCrewProjectDto.setProjectSLng(0.0);
        if (cleanCrewProjectDto.getProjectSLat() == null) cleanCrewProjectDto.setProjectSLat(0.0);
        if (cleanCrewProjectDto.getProjectSName() == null || cleanCrewProjectDto.getProjectSName().isEmpty()) cleanCrewProjectDto.setProjectSName("출발지 없음");
        if (cleanCrewProjectDto.getProjectDLng() == null) cleanCrewProjectDto.setProjectDLng(0.0);
        if (cleanCrewProjectDto.getProjectDLat() == null) cleanCrewProjectDto.setProjectDLat(0.0);
        if (cleanCrewProjectDto.getProjectDName() == null || cleanCrewProjectDto.getProjectDName().isEmpty()) cleanCrewProjectDto.setProjectDName("도착지 없음");

        crewProjectMapper.crewProjectInsert(cleanCrewProjectDto);
    }


//         크루 프로젝트 상세 보기
    public List<CrewTeamVo> findProjectDetailList(Long crewNumber, Long crewProjectNumber){
        return crewProjectMapper.selectProjectDetail(crewNumber,crewProjectNumber);
    }

//    크루원 정보 상세조회
    public List<CrewMemberVo> crewMemberList(Long crewNumber){
        return crewProjectMapper.selectCrewMemberByNum(crewNumber);
    }

//    크루 프로젝트 참여
    public void projectJoinRegister(CleanMyProjectDto cleanMyProjectDto) {

        int count = crewProjectMapper.selectProjectJoinCount(cleanMyProjectDto);
        if (count > 0) {
            throw new IllegalStateException("이미 가입한 프로젝트입니다!");
        }
        crewProjectMapper.projectJoinInsert(cleanMyProjectDto);
    }

//        크루 프로젝트 참여인원 상세조회
        public List<ProjectMemberVo> projectMemberList(Long crewNumber, Long crewProjectNumber){
            return crewProjectMapper.selectProjectMemberByNum(crewNumber,crewProjectNumber);
        }

//        크루 프로젝트인원 여부 조회
public boolean isCrewProjectMember(Long crewNumber, Long crewProjectNumber,Long userNumber) {
    List<ProjectMemberVo> members = crewProjectMapper.selectProjectMemberByNum(crewNumber,crewProjectNumber);
    return members.stream().anyMatch(member -> member.getUserNumber().equals(userNumber));
}


//        크루 프로젝트 참여인원 인증 삽입
    public void registerProjectRecommend(CrewRecommendDto crewRecommendDto){
        int count = crewProjectMapper.selectProjectRecommend(crewRecommendDto);

        if (count > 0){
            throw new IllegalStateException("이미 인증한 프로젝트입니다!");
        }
        crewProjectMapper.insertProjectRecommend(crewRecommendDto);
    }






}
