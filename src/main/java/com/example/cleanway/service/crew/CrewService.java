package com.example.cleanway.service.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewDto;
import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.vo.crew.CrewVo;
import com.example.cleanway.mapper.crew.CrewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CrewService {
    private final CrewMapper crewMapper;

//    크루 리스트 조회
    public List<CrewVo> findCrewList(){
        List<CrewVo> crewList = crewMapper.selectCrewList();
        return crewList;
    }
//    크루 모집 등록
    public void crewRegister(CleanCrewDto cleanCrewDto){
        crewMapper.crewInsert(cleanCrewDto);
    }
//크루 프로젝트 등록
    public void crewProjectRegister(CleanCrewProjectDto cleanCrewProjectDto){
        crewMapper.crewProjectInsert(cleanCrewProjectDto);
    }
}
