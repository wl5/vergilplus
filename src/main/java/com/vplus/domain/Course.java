package com.vplus.domain;

public class Course{
	private String courseNumber;
	private int section;
	private String courseTitle;
	private String startTime;
	private String endTime;
	private String instructor;
	private String prerequisite;
	private int term;
	private String description;
	
	public String getCourseNumber() {
		return courseNumber;
	}
	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getInstructor() {
		return instructor;
	}
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
	public String getPrerequisite() {
		return prerequisite;
	}
	public void setPrerequisite(String prerequisite) {
		this.prerequisite = prerequisite;
	}
	public int getTerm() {
		return term;
	}
	public void setTerm(int term) {
		this.term = term;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Course [courseNumber=" + courseNumber + ", section=" + section + ", courseTitle=" + courseTitle
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", instructor=" + instructor
				+ ", prerequisite=" + prerequisite + ", term=" + term + ", description=" + description + "]";
	}

	

	
}
