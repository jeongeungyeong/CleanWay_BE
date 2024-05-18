package com.example.cleanway.service.crew;

import com.example.cleanway.domain.dto.crew.CleanCrewProjectDto;
import com.example.cleanway.domain.dto.crew.CleanMyProjectDto;
import com.example.cleanway.mapper.crew.CrewProjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class CrewProjectService {
    private final CrewProjectMapper crewProjectMapper;

    //크루 프로젝트 등록
    public void crewProjectRegister(CleanCrewProjectDto cleanCrewProjectDto){
                  crewProjectMapper.crewProjectInsert(cleanCrewProjectDto);
         }



//    크루 프로젝트 참여
    public void projectJoinRegister(CleanMyProjectDto cleanMyProjectDto){
        crewProjectMapper.projectJoinInsert(cleanMyProjectDto);
    }
}
