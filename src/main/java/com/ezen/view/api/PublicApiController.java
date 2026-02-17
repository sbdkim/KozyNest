package com.ezen.view.api;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.biz.dto.AccommodationVO;
import com.ezen.biz.service.AccommodationService;

import utils.Criteria;

@RestController
@RequestMapping("/api")
public class PublicApiController {

	@Autowired
	private AccommodationService accommodationService;

	@GetMapping("/health")
	public Map<String, Object> health() {
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("status", "ok");
		response.put("service", "LodgingService");
		return response;
	}

	@GetMapping("/accommodations")
	public Map<String, Object> listAccommodations(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "10") int size,
			@RequestParam(value = "key", defaultValue = "") String key) {
		Criteria criteria = new Criteria();
		criteria.setPageNum(page);
		criteria.setRowsPerPage(size);

		String normalizedKey = normalizeSearchKey(key);
		List<AccommodationVO> accommodations = accommodationService.getListAccWithPaging(criteria, normalizedKey);
		int totalCount = accommodationService.countAccList(normalizedKey);

		Map<String, Object> response = new LinkedHashMap<String, Object>();
		response.put("page", criteria.getPageNum());
		response.put("size", criteria.getRowsPerPage());
		response.put("totalCount", totalCount);
		response.put("items", toSummaryItems(accommodations));
		return response;
	}

	@GetMapping("/accommodations/{aseq}")
	public Map<String, Object> getAccommodation(@PathVariable("aseq") int aseq) {
		if (aseq <= 0) {
			throw new IllegalArgumentException("aseq must be greater than 0");
		}

		AccommodationVO query = new AccommodationVO();
		query.setAseq(aseq);
		AccommodationVO accommodation = accommodationService.getAccommodation(query);
		if (accommodation == null) {
			throw new IllegalArgumentException("accommodation not found");
		}

		return toDetailItem(accommodation);
	}

	private String normalizeSearchKey(String key) {
		String normalized = key == null ? "" : key.trim();
		if (normalized.length() > 100) {
			throw new IllegalArgumentException("key length must be 100 characters or fewer");
		}
		return normalized;
	}

	private List<Map<String, Object>> toSummaryItems(List<AccommodationVO> accommodations) {
		List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
		for (AccommodationVO accommodation : accommodations) {
			Map<String, Object> item = new LinkedHashMap<String, Object>();
			item.put("aseq", accommodation.getAseq());
			item.put("name", accommodation.getAname());
			item.put("category", accommodation.getCategory());
			item.put("address", accommodation.getAddress());
			item.put("telephone", accommodation.getTel());
			item.put("bestYn", accommodation.getBestyn());
			item.put("image", accommodation.getAimage());
			items.add(item);
		}
		return items;
	}

	private Map<String, Object> toDetailItem(AccommodationVO accommodation) {
		Map<String, Object> item = new LinkedHashMap<String, Object>();
		item.put("aseq", accommodation.getAseq());
		item.put("name", accommodation.getAname());
		item.put("hostEmail", accommodation.getHemail());
		item.put("roomName", accommodation.getRname());
		item.put("category", accommodation.getCategory());
		item.put("address", accommodation.getAddress());
		item.put("telephone", accommodation.getTel());
		item.put("bestYn", accommodation.getBestyn());
		item.put("image", accommodation.getAimage());
		item.put("roomImage", accommodation.getRimage());
		return item;
	}
}
