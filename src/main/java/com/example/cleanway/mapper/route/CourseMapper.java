package com.example.cleanway.mapper.route;

import com.example.cleanway.domain.dto.route.CleanCourseDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper {
    void insertCourse(CleanCourseDto cleanCourseDto);
}
