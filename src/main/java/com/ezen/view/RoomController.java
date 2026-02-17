package com.ezen.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.MemberVO;
import com.ezen.biz.dto.RoomVO;
import com.ezen.biz.service.AccommodationService;
import com.ezen.biz.service.RoomService;
import com.ezen.view.support.WebParamSanitizer;

import utils.Criteria;
import utils.PageMaker;

@Controller
public class RoomController {

	@Autowired
	private RoomService roomService;
	@Autowired
	private AccommodationService accommodationService;

	@RequestMapping("/room")
	public String roomView(BookingVO vo, Model model, int aseq, @RequestParam(value = "checkin") String checkin,
			@RequestParam(value = "checkout") String checkout) {
		String accommodationName = accommodationService.getNameByAseq(aseq);
		List<RoomVO> roomList = roomService.getRoomByAcc(vo);
		model.addAttribute("roomList", roomList);
		model.addAttribute("checkin", checkin);
		model.addAttribute("checkout", checkout);
		model.addAttribute("accommodationName", accommodationName);
		return "room/roomList";
	}

	@RequestMapping("/updateRoom")
	public String roomViewUpdate(BookingVO vo, Model model, @RequestParam(value = "aseq") int aseq,
			@RequestParam(value = "checkin") String checkin, @RequestParam(value = "checkout") String checkout) {
		String accommodationName = accommodationService.getNameByAseq(aseq);
		List<RoomVO> roomList = roomService.getRoomByAcc(vo);
		model.addAttribute("roomList", roomList);
		model.addAttribute("checkin", checkin);
		model.addAttribute("checkout", checkout);
		model.addAttribute("accommodationName", accommodationName);
		return "room/roomList";
	}

	@RequestMapping("/selectedAccommodation")
	public String accSearchList(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,
			@RequestParam(value = "rowsPerPage", defaultValue = "10") String rowsPerPage,
			@RequestParam(value = "key", defaultValue = "0") String key, Model model) {

		Criteria criteria = new Criteria();
		criteria.setPageNum(WebParamSanitizer.parseInt(pageNum, 1, 1, 10000));
		criteria.setRowsPerPage(WebParamSanitizer.parseInt(rowsPerPage, 10, 5, 20));
		int aseq = WebParamSanitizer.parseInt(key, 0, 0, Integer.MAX_VALUE);

		List<RoomVO> roomList = roomService.getRoomListWithPaging(criteria, aseq);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(roomService.countRoomList(aseq));

		model.addAttribute("roomList", roomList);
		model.addAttribute("roomListSize", roomList.size());
		model.addAttribute("pageMaker", pageMaker);

		return "room/roomList";
	}

	@RequestMapping("/room_detail")
	public String roomDetail(RoomVO vo, Model model) {
		int rseq = vo.getRseq();
		RoomVO roomDetail = roomService.getRoomByRseq(rseq);
		model.addAttribute("roomDetail", roomDetail);
		return "room/roomDetail";
	}

	@RequestMapping("/booking")
	public String booking(HttpSession session, RoomVO vo, @RequestParam(value = "checkin") String checkin,
			@RequestParam(value = "checkout") String checkout, Model model) {
		MemberVO loginUser = (MemberVO) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "member/login";
		}

		int rseq = vo.getRseq();
		RoomVO accRoom = roomService.getAccByRseq(rseq);
		model.addAttribute("checkin", checkin);
		model.addAttribute("checkout", checkout);
		model.addAttribute("accRoom", accRoom);
		return "room/booking";
	}
}
