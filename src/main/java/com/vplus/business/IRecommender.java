package com.vplus.business;

import java.util.List;

import com.vplus.domain.Course;

/**
 * Recommend courses using the selected functionalities 
 */
public interface IRecommender {
	/**
	 * Filter courses by taken courses and prerequisites
	 * @param takenCourses List of taken courses
	 * @return filtered courses
	 */
	public List<Course> filterCourses(List<String> takenCourses); 
	
	/**
	 * Acknowledge taken courses, e.g, remove taken courses, make courses available
	 * @param takenCourses Courses the user has taken
	 * @param allCourses Set of all courses
	 * @return Subset of allCourses that is available for the student to take
	 */
	public List<Course> processTakenCourses(List<String> takenCourses, List<Course> allCourses);
	
	/**
	 * Remove courses for which prerequisites are not fulfilled
	 * @param courses List of courses for consideration
	 * @return courses that have null for prerequisites
	 */
	public List<Course> filterByPrerequisites(List<Course> courses);
}
