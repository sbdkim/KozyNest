package com.ezen.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.ReviewVO;
import com.ezen.biz.service.ReviewService;

import utils.Criteria;
import utils.PageMaker;

@RestController
@RequestMapping("/review")
public class ReviewController {

	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@Autowired
	private ReviewService reviewService;

	@GetMapping(value = "/list", produces = "application/json; charset=UTF-8")
	public Map<String, Object> reviewList(ReviewVO reviewVO, @RequestParam(value = "rseq") int rseq, Criteria criteria,
			Model model) {

		Map<String, Object> reviewInfo = new HashMap<>();
		List<ReviewVO> reviewList = reviewService.getReviewListwithPaging(criteria, rseq);
		logger.debug("Review list requested: rseq={}, page={}, size={}", rseq, criteria.getPageNum(),
				criteria.getRowsPerPage());

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(reviewService.getCountReviewList(rseq));

		reviewInfo.put("total", reviewList.size());
		reviewInfo.put("reviewList", reviewList);
		reviewInfo.put("pageInfo", pageMaker);
		return reviewInfo;
	}

	@PostMapping(value = "/save")
	public String saveReviewAction(ReviewVO reviewVO, @RequestParam(value = "rating") int[] rating,
			@RequestParam(value = "rseq") int rseq, HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		reviewVO.setRseq(rseq);
		logger.debug("Review save requested for rseq={}", rseq);

		if (loginUser == null) {
			return "not_logedin";
		}

		for (int value : rating) {
			reviewVO.setScore(value);
		}
		reviewVO.setEmail(loginUser.getEmail());

		if (reviewService.insertReview(reviewVO) > 0) {
			return "success";
		}
		return "fail";
	}

	@RequestMapping(value = "/delete", produces = "application/json; charset=UTF-8")
	public String reviewDelete(ReviewVO vo, MemberVO memberVO, HttpSession session) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "not_logedin";
		}

		vo.setEmail(loginUser.getEmail());
		logger.info("Deleting review reseq={} by user={}", vo.getReseq(), vo.getEmail());
		reviewService.deleteReview(vo);
		return "success";
	}
}