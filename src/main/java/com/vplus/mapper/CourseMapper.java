package com.vplus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vplus.domain.Course;

/**
 * Mapper between database course table and com.vplus.domain.Course
 */
@Mapper
public interface CourseMapper{
	/** Select courses by course number **/
	public List<Course> selectCourseByNumber(String courseNumber);
	
	/** 
	 * Select course by course number and section.
	 * This results in a unique course instance.
	 **/
	public Course selectCourseByNumberAndSection(String courseNumber, int section);
	
	/** Select courses by keyword **/
	public List<Course> selectCourseByKeyword(String keyword);
	
	/** Select all courses **/
	public List<Course> selectAllCourses();
	
	/** Select all instructors **/
	public List<String> selectAllInstructors();
	
	/** Check if course number corresponds to a valid course **/
	public boolean isValidCourse(String courseNumber);
	
	/** Check if instructor is valid **/
	public boolean isValidInstructor(String instructor);
}
