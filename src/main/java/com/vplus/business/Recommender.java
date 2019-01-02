package com.vplus.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.vplus.domain.Course;
import com.vplus.mapper.CourseMapper;
import com.vplus.mapper.ReviewMapper;

/**
 * Recommend courses using the selected functionalities 
 */
@Service
public class Recommender implements IRecommender {
	/**
	 * Injected mappers and userConsole
	 */
	private final CourseMapper courseMapper;
	private final ReviewMapper reviewMapper;
	private final IUserConsole userConsole;

	/**
	 * Number of recommendations to give
	 */
	private final int NUM_REC = 4;

	/**
	 * Constructor to help with injection
	 */
	public Recommender(CourseMapper courseMapper, ReviewMapper reviewMapper, IUserConsole userConsole) {
		this.courseMapper = courseMapper;
		this.reviewMapper = reviewMapper;
		this.userConsole = userConsole;
	}

	/**
	 * Filter courses by taken courses and prerequisites
	 * @param takenCourses List of taken courses
	 * @return filtered courses
	 */
	public List<Course> filterCourses(List<String> takenCourses) {
		List<Course> allCourses = courseMapper.selectAllCourses();
		List<Course> filteredCourses = processTakenCourses(takenCourses, allCourses);
		filteredCourses = filterByPrerequisites(filteredCourses);
		return filteredCourses;
	}

	/**
	 * Acknowledge taken courses, e.g, remove taken courses, make courses available
	 * @param takenCourses Courses the user has taken
	 * @param allCourses Set of all courses
	 * @return Subset of allCourses that is available for the student to take
	 */
	public List<Course> processTakenCourses(List<String> takenCourses, List<Course> allCourses) {
		List<Course> filteredCourses=new ArrayList<>();
		if (takenCourses == null || takenCourses.isEmpty()) return allCourses;
		for(Course course : allCourses){
			// if takenCourses and prereqs are not disjoint (i.e., there is overlap),
			// prereq becomes empty, because all prereqs are joined by OR.
			if(!Collections.disjoint(takenCourses, Arrays.asList(course.getPrerequisite().split(",")))){
				course.setPrerequisite("");
			}

			// remove course from all courses if that course is already taken.
			if(!takenCourses.contains(course.getCourseNumber())) {
				filteredCourses.add(course);
			}
		}
		return filteredCourses;
	}

	/**
	 * Remove courses for which prerequisites are not fulfilled
	 * @param courses List of courses for consideration
	 * @return courses that have null for prerequisites
	 */	
	public List<Course> filterByPrerequisites(List<Course> courses) {
		List<Course> filteredCourses = new ArrayList<>();
		if(courses == null || courses.isEmpty()){
			return filteredCourses;
		}
		// if prerequisite empty, then able to take the course
		for (Course course : courses) {
			if (course.getPrerequisite().isEmpty()) {
				filteredCourses.add(course);
			}
		}
		return filteredCourses;
	}

	/**
	 * Sort courses by review scores
	 * @param courses input courses
	 * @return list of courses sorted by review scores
	 */
	private List<Course> sortCoursesByReviewScores(List<Course> courses) {
		courses.sort(Comparator.comparing(a -> reviewMapper.selectRatingByCourseNumber(a.getCourseNumber()),
				Comparator.reverseOrder()));
		return courses;
	}

	/**
	 * Recommend by previous taken courses
	 * @return list of course recommendations
	 * @throws IOException
	 */
	private List<Course> recommendByPrevCourses() throws IOException {
		List<String> takenCourses = userConsole.getTakenCourses();
		List<Course> recCourses = new ArrayList<Course>();
		
		// check irregularities 
		if (takenCourses == null) {
			System.out.println("Taken courses null!");
			return recCourses;
		}
		if (takenCourses.size() >= 12) {
			return recCourses;
		}
		
		// filter and sort courses
		List<Course> filteredCourses = filterCourses(takenCourses);
		List<Course> result = sortCoursesByReviewScores(filteredCourses);
		recCourses = result.subList(0, NUM_REC);

		return recCourses;
	}

	/**
	 * Recommend by course topic
	 * @return list of course recommendations
	 * @throws IOException
	 */
	private List<Course> recommendByTopic() throws IOException {
		// get user input
		String topic = userConsole.getTopic();
		List<String> takenCourses = userConsole.getTakenCourses();
		List<Course> topicCourses = courseMapper.selectCourseByKeyword(topic);

		// remove taken courses
		List<Course> tmp = new ArrayList<>(topicCourses);
		for (Course c : tmp) {
			if (takenCourses.contains(c.getCourseNumber())) {
				topicCourses.remove(c);
			}
		}

		return topicCourses;
	}

	/**
	 * Recommend by professor
	 * @return list of course recommendations
	 * @throws IOException
	 */
	private List<Course> recommendByProfessor() throws IOException {
		// get user input
		String professor = userConsole.getProfessor();
		String words = reviewMapper.selectWordsByProfessor(professor);
		System.out.println(words);

		// remove taken courses
		List<String> takenCourses = userConsole.getTakenCourses();
		List<Course> profCourses = courseMapper.selectCourseByKeyword(professor);
		List<Course> tmp = new ArrayList<>(profCourses);
		for (Course c : tmp) {
			if (takenCourses.contains(c.getCourseNumber())) {
				profCourses.remove(c);
			}
		}

		return profCourses;
	}

	/**
	 * Recommend courses
	 * @throws IOException
	 */
	public void recommend() throws IOException {
		// get user input to know which functionality user wants
		userConsole.promptWelcomeMessage();
		int functionality = userConsole.getFunctionality();

		List<Course> recommendations = null;
		switch (functionality) {
		case 1:
			recommendations = recommendByProfessor();
			break;
		case 2:
			recommendations = recommendByTopic();
			break;
		case 3:
			recommendations = recommendByPrevCourses();
			break;
		}

		if (recommendations.isEmpty()) {
			System.out.println("We don't have an recommendation for you right now!");
		} else {
			System.out.println("Here are our recommendations!");
			recommendations.forEach(l -> System.out.println(l + "\n-------------------------------------"));
		}

	}
}
