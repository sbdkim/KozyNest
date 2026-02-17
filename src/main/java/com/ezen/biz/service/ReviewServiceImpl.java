package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.ReviewDAO;
import com.ezen.biz.dto.ReviewVO;

import utils.Criteria;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewDAO rDao;

	@Override
	public int insertReview(ReviewVO vo) {
		return rDao.insertReview(vo);
	}

	@Override
	public List<ReviewVO> selectReviewByRseq(int rseq) {
		return rDao.selectReviewByRseq(rseq);
	}

	@Override
	public void updateReview(ReviewVO vo) {
		rDao.updateReview(vo);
	}

	@Override
	public void deleteReview(ReviewVO vo) {
		rDao.deleteReview(vo);

	}

	@Override
	public List<ReviewVO> getListReview() {
		return rDao.getListReview();
	}

	@Override
	public void insertReply(ReviewVO vo) {
		rDao.insertReply(vo);
	}

	@Override
	public List<ReviewVO> getReviewListwithPaging(Criteria criteria, int rseq) {
		return rDao.reviewListwithPaging(criteria, rseq);
	}

	@Override
	public int getCountReviewList(int rseq) {

		return rDao.getCountReviewList(rseq);
	}

}

