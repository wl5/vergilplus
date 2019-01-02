package com.vplus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.vplus.domain.Review;

/**
 * Mapper between database sentiment table and com.vplus.domain.Review
 */
@Mapper
public interface ReviewMapper {
	/** Select all reviews **/
	public List<Review> selectAllReviews();
	
	/** Select the sentimental words corresponding to professor **/
	public String selectWordsByProfessor(String professor);
	
	/** Select course rating by course number **/
	public double selectRatingByCourseNumber(String courseNumber);
}
