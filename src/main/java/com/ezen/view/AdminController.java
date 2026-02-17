package com.ezen.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.HostVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.QnaVO;
import com.ezen.biz.service.BookingService;
import com.ezen.biz.service.HostService;
import com.ezen.biz.service.MemberService;
import com.ezen.biz.service.QnaService;
import com.ezen.view.support.WebParamSanitizer;

import utils.Criteria;
import utils.PageMaker;

@Controller
@SessionAttributes("admin")
public class AdminController {
	@Autowired
	private QnaService qnaService;
	@Autowired
	private HostService hostService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private BookingService bookingService;

	@GetMapping("/admin_login_form")
	public String adminLoginView() {
		return "admin/main";
	}// adminLoginView

	@GetMapping("/admin_logout")
	public String adminLogout(SessionStatus status) {
		status.setComplete();
		return "index";
	}// adminLogout


	@PostMapping("/admin_qna_detail")
	public String adminQnaDetail(Model model, QnaVO vo) {
		QnaVO qna = qnaService.getQna(vo.getQseq());
		model.addAttribute("qnaVO", qna);
		return "admin/qna/qnaDetail";
	}// adminQnaDetail

	@PostMapping("/admin_qna_repsave")
	public String adminQnaRepSave(Model model, QnaVO vo) {
		qnaService.updateQna(vo);
		return "redirect:admin_qna_list";
	}// adminQnaRepSave

//	   //상품별 판매 실적 화면 출력
//	@RequestMapping("/admin_booking_record_form")
//		public String adminProductSalesForm() {
//		  return "admin/order/salesRecords";
//	    }
	@RequestMapping("/approve_host_save")
	public String approveHostSave(@RequestParam(value = "status") String[] hemail) {

		for (int i = 0; i < hemail.length; i++) {
			hostService.updateHostStatus(hemail[i]);
		}

		return "redirect:admin_hostList";
	}

	@RequestMapping("/delete_host")
	public String deleteHost(@RequestParam(value = "delete") String[] hemail) {

		for (int i = 0; i < hemail.length; i++) {
			hostService.deleteHost(hemail[i]);
		}

		return "redirect:admin_hostList";
	}

	@RequestMapping("/delete_member")
	public String deleteMember(@RequestParam(value = "delete") String[] email) {

		for (int i = 0; i < email.length; i++) {
			memberService.deleteMember(email[i]);
		}

		return "redirect:admin_memberList";
	}

	// after admin login, add the hostlist to be pased to the hostList page
	@RequestMapping("/admin_hostList")
	public String adminHostList(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,
			@RequestParam(value = "rowsPerPage", defaultValue = "10") String rowsPerPage,
			@RequestParam(value = "key", defaultValue = "") String name, Model model) {

		Criteria criteria = new Criteria();
		criteria.setPageNum(WebParamSanitizer.parseInt(pageNum, 1, 1, 10000));
		criteria.setRowsPerPage(WebParamSanitizer.parseInt(rowsPerPage, 10, 5, 20));
		String normalizedName = WebParamSanitizer.normalizeKeyword(name, 100);
		List<HostVO> hostList = hostService.getListHostWithPaging(criteria, normalizedName);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(hostService.countHostList(normalizedName));

		model.addAttribute("hostList", hostList);
		model.addAttribute("hostListSize", hostList.size());
		model.addAttribute("key", normalizedName);
		model.addAttribute("pageMaker", pageMaker);

		return "admin/host/hostList";
	}
	
	@RequestMapping("/admin_memberList")
	public String adminMemberList(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,
			@RequestParam(value = "rowsPerPage", defaultValue = "10") String rowsPerPage,
			@RequestParam(value = "key", defaultValue = "") String name, Model model) {

		Criteria criteria = new Criteria();
		criteria.setPageNum(WebParamSanitizer.parseInt(pageNum, 1, 1, 10000));
		criteria.setRowsPerPage(WebParamSanitizer.parseInt(rowsPerPage, 10, 5, 20));
		String normalizedName = WebParamSanitizer.normalizeKeyword(name, 100);
		List<MemberVO> memberList = memberService.getListMemberWithPaging(criteria, normalizedName);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(memberService.countMemberList(normalizedName));

		model.addAttribute("memberList", memberList);
		model.addAttribute("memberListSize", memberList.size());
		model.addAttribute("key", normalizedName);
		model.addAttribute("pageMaker", pageMaker);

		return "admin/member/memberList";
	}

	@RequestMapping("/admin_revenue")
	public String adminRevenueDisplay() {
		return "admin/revenue/revenueRecords";
	}

	@RequestMapping("/monthlyChart")
	@ResponseBody // 화면이 아닌 데이터를 리턴하는 메소드로 지정
	public List<BookingVO> salesRecordChart() {
		List<BookingVO> listMonthRevenue = bookingService.getMonthlyRevenue();
		return listMonthRevenue;

	}

}// class
