package com.ezen.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.biz.dto.AccommodationVO;
import com.ezen.biz.service.AccommodationService;

import utils.Criteria;
import utils.PageMaker;

@Controller
public class AccommodationController {

	@Autowired
	private AccommodationService accommodationService;

	@RequestMapping("/search")
	public String searchView(AccommodationVO vo, Model model) {
		String address = normalizeAddress(vo.getAddress());
		List<AccommodationVO> accommodationList = accommodationService.selectAccByAdd(address);
		model.addAttribute("accommodationList", accommodationList);
		return "accommodation/accList";
	}

	@RequestMapping("/acc_search_list")
	public String accSearchList(@RequestParam(value = "pageNum", defaultValue = "1") String pageNum,
			@RequestParam(value = "rowsPerPage", defaultValue = "10") String rowsPerPage,
			@RequestParam(value = "key", defaultValue = "") String address,
			@RequestParam(value = "checkin", defaultValue = "") String checkin,
			@RequestParam(value = "checkout", defaultValue = "") String checkout,
			@RequestParam(value = "ro_count", defaultValue = "") String roCount, Model model) {

		Criteria criteria = new Criteria();
		criteria.setPageNum(parseIntOrDefault(pageNum, 1));
		criteria.setRowsPerPage(parseIntOrDefault(rowsPerPage, 10));

		String normalizedAddress = normalizeAddress(address);
		List<AccommodationVO> accommodationList = accommodationService.getListAccWithPaging(criteria, normalizedAddress);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(accommodationService.countAccList(normalizedAddress));

		model.addAttribute("accommodationList", accommodationList);
		model.addAttribute("accommodationListSize", accommodationList.size());
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("key", normalizedAddress);
		model.addAttribute("checkin", checkin);
		model.addAttribute("checkout", checkout);
		model.addAttribute("ro_count", roCount);

		return "accommodation/accList";
	}

	private int parseIntOrDefault(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException ex) {
			return defaultValue;
		}
	}

	private String normalizeAddress(String address) {
		if (address == null) {
			return "";
		}
		String normalized = address.trim();
		if (normalized.length() > 100) {
			return normalized.substring(0, 100);
		}
		return normalized;
	}
}
