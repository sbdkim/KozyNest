package com.ezen.view;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.biz.dto.AccommodationVO;
import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.HostVO;
import com.ezen.biz.dto.RoomVO;
import com.ezen.biz.dto.SalesQuantity;
import com.ezen.biz.service.AccommodationService;
import com.ezen.biz.service.BookingService;
import com.ezen.biz.service.RoomService;
import com.ezen.biz.service.UploadStorageService;

@Controller
public class HostController {
	private static final Logger logger = LoggerFactory.getLogger(HostController.class);

	@Autowired
	private BookingService bookingService;
	@Autowired
	private AccommodationService accommodationService;
	@Autowired
	private RoomService roomService;
	@Autowired
	private UploadStorageService uploadStorageService;

	@PostMapping("/host_accommodation_write_form")
	public String hostAccommodationWriteView(Model model) {
		String[] categoryList = { "호텔", "모텔", "펜션,풀빌라", "게스트 하우스" };

		model.addAttribute("categoryList", categoryList);

		return "host/accommodationWrite";
	}

	@PostMapping("/host_accommodation_write")
	public String hostAccommodationWrite(AccommodationVO vo, HttpSession session,
			@RequestParam(value = "default") MultipartFile uploadFile) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {

			vo.setHemail(loginHost.getHemail());

			if (!uploadFile.isEmpty()) {
				String fileName = uploadStorageService.storeAccommodationImage(uploadFile);
				vo.setAimage(fileName);
			} else {
				vo.setAimage("default.jpg");
			}

		}
		accommodationService.insertAccommodation(vo);

		return "redirect:host_mypage";

	}

	@RequestMapping("/host_acc_update_form")
	public String hostAccUpdateView(AccommodationVO vo, Model model) {
		String[] categoryList = { "호텔", "모텔", "펜션,풀빌라", "게스트 하우스" };

		AccommodationVO accommodation = accommodationService.getAccommodation(vo);

		model.addAttribute("accommodationVO", accommodation);
		model.addAttribute("categoryList", categoryList);

		return "host/accommodationUpdate";
	}

	@PostMapping("/host_acc_update")
	public String hostAccUpdate(AccommodationVO vo,
			@RequestParam(value = "accommodation_images") MultipartFile uploadFile,
			@RequestParam(value = "nonmakeImg") String org_image, HttpSession session) {

		if (!uploadFile.isEmpty()) {
			String fileName = uploadStorageService.storeAccommodationImage(uploadFile);
			vo.setAimage(fileName);
		} else {
			vo.setAimage(org_image);
		}

		accommodationService.updateAccommodation(vo);

		return "redirect:host_mypage";

	}

	@RequestMapping("/host_acc_delete")
	public String hostAccDelete(@RequestParam(value = "aseq") int aseq) {

		accommodationService.deleteAccommodation(aseq);

		return "redirect:host_mypage";
	}

	@RequestMapping("/host_mypage")
	public String hostMyPageView(HttpSession session, AccommodationVO vo, Model model) {

		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {

			vo.setHemail(loginHost.getHemail());

			List<AccommodationVO> accommodationList = accommodationService.getListHostAccommodation(vo);

			model.addAttribute("accommodationList", accommodationList);

			return "host/hostmypage";

		}

	}

	@GetMapping("/host_accommodation_list")
	public String HostAccommodationListAction(HttpSession session, Model model, AccommodationVO vo) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {
			vo.setHemail(loginHost.getHemail());
			List<AccommodationVO> accommodationList = accommodationService.getListHostAccommodation(vo);

			model.addAttribute("accommodationList", accommodationList);

			return "host/accommodationList";
		}
	}

	@GetMapping("/accommodation_detail")
	public String AccommodationDetail(HttpSession session, AccommodationVO vo, Model model) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {
			vo.setHemail(loginHost.getHemail());

			logger.debug("Loading accommodation detail: {}", vo);
			AccommodationVO accommodationDetail = accommodationService.getAccommodation(vo);

			accommodationDetail.setAseq(accommodationDetail.getAseq());
			accommodationDetail.setHemail(accommodationDetail.getHemail());
			accommodationDetail.setAname(accommodationDetail.getAname());

			List<RoomVO> roomList = roomService.hostGetRoomByAcc(vo.getAseq());

			RoomVO roomDetail = new RoomVO();
			roomDetail.setAseq(vo.getAseq());


			model.addAttribute("accommodationDetail", accommodationDetail);
			model.addAttribute("roomList", roomList);

			return "host/accommodationDetail";


			
		}

	}

	@PostMapping("/host_room_write_form")
	public String hostRoomWriteView(RoomVO vo, Model model) {

		model.addAttribute("aseq", vo.getAseq());
		

		return "host/roomWrite";
	}

	@RequestMapping(value = "/host_room_write")
	public String hostRoomWrite(RoomVO vo, HttpSession session,
			@RequestParam(value = "default") MultipartFile uploadFile, RedirectAttributes rattr) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {

			vo.setHemail(loginHost.getHemail());

			if (!uploadFile.isEmpty()) {
				String fileName = uploadStorageService.storeRoomImage(uploadFile);
				vo.setRimage(fileName);
			} else {
				vo.setRimage("default.jpg");
			}

		}

		roomService.insertRoom(vo);

		rattr.addAttribute("aseq", vo.getAseq());

		return "redirect:accommodation_detail";

	}

	@RequestMapping("/host_room_update_form")
	public String hostRoomUpdateView(RoomVO vo, Model model) {
		RoomVO room = roomService.selectRoomByRseq(vo);

		model.addAttribute("aseq", vo.getAseq());
		model.addAttribute("roomVO", room);

		return "host/roomUpdate";
	}

	@PostMapping("/host_room_update")
	public String hostRoomUpdate(RoomVO vo, @RequestParam(value = "room_images") MultipartFile uploadFile,
			@RequestParam(value = "nonmakeImg") String org_image, HttpSession session, Model model,
			RedirectAttributes rattr) {

		if (!uploadFile.isEmpty()) {
			String fileName = uploadStorageService.storeRoomImage(uploadFile);
			vo.setRimage(fileName);
		} else {
			vo.setRimage(org_image);
		}

		roomService.updateRoom(vo);

		rattr.addAttribute("aseq", vo.getAseq());

		return "redirect:accommodation_detail";

	}

	@RequestMapping("/host_room_delete")
	public String hostRoomDelete(@RequestParam(value = "rseq") int rseq,@RequestParam(value = "aseq") int aseq ) {

		roomService.deleteRoom(rseq);

		return "redirect:accommodation_detail?aseq=" + aseq;
	}

	@GetMapping("/hostBookingList")
	public String HostBookingListAction(HttpSession session, AccommodationVO vo, Model model) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {
			vo.setHemail(loginHost.getHemail());
			List<AccommodationVO> accommodationList = accommodationService.getListHostAccommodation(vo);

			model.addAttribute("accommodationList", accommodationList);

			return "host/hostBookingList";
		}
	}
	
	
	
	@GetMapping("/host_booking_detail")
	public String HostBookingDetail(HttpSession session, BookingVO vo, Model model) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		if (loginHost == null) {
			return "member/login";
		} else {
			
			logger.debug("Host booking detail requested, status={}", vo.getStatus());
			
			vo.setHemail(loginHost.getHemail());
			if (vo.getStatus() < 1 || vo.getStatus() > 3) {
				vo.setStatus(1);
			}
			
			
			List<BookingVO> bookingList = bookingService.getListBookByAseq(vo);

			model.addAttribute("aseq", vo.getAseq());
			
			model.addAttribute("bookingList", bookingList);
					
		}
			
		return "host/hostBookingListDetail";
			
		
	}
	
	@RequestMapping("/host_booking_delete")
	public String hostBookingDelete(@RequestParam(value = "bseq") int bseq,
			@RequestParam(value = "status") int status,@RequestParam(value = "aseq") int aseq
			) {
		logger.info("Deleting booking bseq={} for aseq={} with status={}", bseq, aseq, status);
		
		bookingService.deleteBookByBseq(bseq);

		return "redirect:host_booking_detail?status=" + status + "&aseq=" + aseq;
	}

		
	
	// 상품별 판매 실적 화면 출력
	@RequestMapping("/host_booking_record_form")
	public String hostBookingSalesForm() {
		return "host/salesRecords";

	}
	

	@RequestMapping("/booking_record_chart")
	@ResponseBody // 화면이 아닌 데이터를 리턴하는 메소드로 지정
	public List<SalesQuantity> salesRecordChart(HttpSession session, AccommodationVO vo) {
		HostVO loginHost = (HostVO) session.getAttribute("loginHost");

		vo.setHemail(loginHost.getHemail());
		List<SalesQuantity> listSales = bookingService.getListBookingSales(vo);
		return listSales;
	}

}
