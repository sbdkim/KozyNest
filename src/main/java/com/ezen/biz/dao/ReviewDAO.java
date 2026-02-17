package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.ReviewVO;

import utils.Criteria;

@Repository
public class ReviewDAO {

	private static final Logger logger = LoggerFactory.getLogger(ReviewDAO.class);

	@Autowired
	private SqlSessionTemplate mybatis;

	public int insertReview(ReviewVO vo) {
		return mybatis.insert("ReviewMapper.insertReview", vo);
	}

	public List<ReviewVO> selectReviewByRseq(int rseq) {
		return mybatis.selectList("ReviewMapper.selectReviewByRseq", rseq);
	}

	public void updateReview(ReviewVO vo) {
		mybatis.update("ReviewMapper.updateReview", vo);
	}

	public void deleteReview(ReviewVO vo) {
		logger.debug("Deleting review in DAO reseq={}, email={}", vo.getReseq(), vo.getEmail());
		mybatis.delete("ReviewMapper.deleteReview", vo);
	}

	public List<ReviewVO> getListReview() {
		return mybatis.selectList("ReviewMapper.getListReview");
	}

	public void insertReply(ReviewVO vo) {
		mybatis.update("ReviewMapper.insertReply", vo);
	}

	public List<ReviewVO> reviewListwithPaging(Criteria criteria, int rseq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("rseq", rseq);
		return mybatis.selectList("ReviewMapper.reviewListwithPaging", map);
	}

	public int getCountReviewList(int rseq) {
		return mybatis.selectOne("ReviewMapper.countReviewList", rseq);
	}
}