package com.ezen.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.QnaVO;
import com.ezen.biz.service.QnaService;
import com.ezen.view.support.WebParamSanitizer;

import utils.Criteria;
import utils.PageMaker;

@Controller
public class QnaController {

	@Autowired
	private QnaService qnaService;

	@GetMapping(value = "/qna_list")

	public String qnaList(HttpSession session, Model model) {
		//
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {// 로그인이 안된경우

			return "member/login";

		} else {

			List<QnaVO> qnaList = qnaService.getListQna(loginUser.getEmail());

			model.addAttribute("qnaList", qnaList);

			return "mypage/qnaList";
		}
	}

	@PostMapping("/qna_write")
	public String qunWriteAction(HttpSession session, Model model, QnaVO vo) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {// 로그인이 안된경우

			return "member/login";

		} else {
			vo.setEmail(loginUser.getEmail());// 아이디 넣어주기
			qnaService.insertQna(vo);
			return "redirect:qna_list";
		}

	}

	@GetMapping("/qna_view")
	public String qnaView(HttpSession session, Model model, QnaVO vo) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {// 로그인이 안된경우

			return "member/login";

		} else {
			// 화면에서 입력받은 qseq를 입력 파라미터로 하여 qna 상세 조회
			QnaVO qna = qnaService.getQna(vo.getQseq());

			model.addAttribute("qnaVO", qna);

			return "mypage/qnaView";
		}

	}

	@GetMapping("/qna_write_form")
	public String qnaWriteView(HttpSession session, QnaVO vo) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");

		if (loginUser == null) {// 로그인이 안된경우

			return "member/login";

		} else {

			return "mypage/qnaWrite";
		}
	}

	@RequestMapping("/admin_qna_list")
	public String adminQnaList(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,
			@RequestParam(value = "rowsPerPage", defaultValue = "10") String rowsPerPage, Model model) {
		
		Criteria criteria = new Criteria();
		criteria.setPageNum(WebParamSanitizer.parseInt(pageNum, 1, 1, 10000));
		criteria.setRowsPerPage(WebParamSanitizer.parseInt(rowsPerPage, 10, 5, 20));
		
		List<QnaVO> qnaList = qnaService.getListQnaWithPaging(criteria);
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(qnaService.countQna());
		
		model.addAttribute("qnaList", qnaList);
		model.addAttribute("qnaListSize", qnaList.size());
		model.addAttribute("pageMaker", pageMaker);
		
		return "admin/qna/qnaList";
	}
}
