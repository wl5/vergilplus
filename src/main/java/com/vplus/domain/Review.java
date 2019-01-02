package com.vplus.domain;

public class Review{
	private String number;
	private String professor;
	private String review;
	private double score;
	private double magnitude;
	private String words;
		
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public double getMagnitude() {
		return magnitude;
	}

	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}

	public String getWords() {
		return words;
	}

	public void setWords(String words) {
		this.words = words;
	}

	@Override
	public String toString() {
		return "Review [number=" + number + ", professor=" + professor + ", review=" + review + ", score=" + score
				+ ", magnitude=" + magnitude + ", words=" + words + "]";
	}
}
