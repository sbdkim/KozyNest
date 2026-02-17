package com.ezen.view.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.ezen.biz.dto.AccommodationVO;
import com.ezen.biz.service.AccommodationService;

import utils.Criteria;

public class PublicApiControllerTest {

	private PublicApiController controller;
	private AccommodationServiceStub serviceStub;

	@Before
	public void setUp() throws Exception {
		controller = new PublicApiController();
		serviceStub = new AccommodationServiceStub();
		setField(controller, "accommodationService", serviceStub);
	}

	@Test
	public void healthReturnsOkStatus() {
		Map<String, Object> response = controller.health();
		assertEquals("ok", response.get("status"));
		assertEquals("LodgingService", response.get("service"));
	}

	@Test
	public void listAccommodationsUsesPagingAndTrimmedKey() {
		Map<String, Object> response = controller.listAccommodations(2, 30, "  seoul ");

		assertEquals(2, response.get("page"));
		assertEquals(20, response.get("size"));
		assertEquals(1, response.get("totalCount"));
		assertEquals("seoul", serviceStub.lastKey);
		assertEquals(2, serviceStub.lastCriteria.getPageNum());
		assertEquals(20, serviceStub.lastCriteria.getRowsPerPage());

		Object itemsObj = response.get("items");
		assertTrue(itemsObj instanceof List);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> items = (List<Map<String, Object>>) itemsObj;
		assertEquals(1, items.size());
		assertEquals("Sample Stay", items.get(0).get("name"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void listAccommodationsRejectsOverlongKey() {
		controller.listAccommodations(1, 10, repeat("a", 101));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAccommodationRejectsInvalidId() {
		controller.getAccommodation(0);
	}

	@Test
	public void getAccommodationReturnsDetail() {
		Map<String, Object> detail = controller.getAccommodation(10);
		assertEquals(10, detail.get("aseq"));
		assertEquals("Sample Stay", detail.get("name"));
		assertEquals("host@example.com", detail.get("hostEmail"));
		assertNotNull(detail.get("roomImage"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void getAccommodationThrowsWhenNotFound() {
		controller.getAccommodation(999);
	}

	private static void setField(Object target, String fieldName, Object value) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	private static String repeat(String ch, int count) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < count; i++) {
			builder.append(ch);
		}
		return builder.toString();
	}

	private static class AccommodationServiceStub implements AccommodationService {

		private final AccommodationVO sample = buildSample();
		private String lastKey;
		private Criteria lastCriteria;

		@Override
		public AccommodationVO getAccommodation(AccommodationVO vo) {
			if (vo.getAseq() == 10) {
				return sample;
			}
			return null;
		}

		@Override
		public List<AccommodationVO> selectAccByAdd(String address) {
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public List<AccommodationVO> getListAcc() {
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public List<AccommodationVO> getListAccWithPaging(Criteria criteria, String address) {
			this.lastCriteria = criteria;
			this.lastKey = address;
			List<AccommodationVO> list = new ArrayList<AccommodationVO>();
			list.add(sample);
			return list;
		}

		@Override
		public List<AccommodationVO> getlistHostAccWithPaging(Criteria criteria, String hemail) {
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public int countAccList(String address) {
			return 1;
		}

		@Override
		public int countHostAccList(String email) {
			return 0;
		}

		@Override
		public String getNameByAseq(int aseq) {
			return null;
		}

		@Override
		public void insertAccommodation(AccommodationVO vo) {
		}

		@Override
		public void updateAccommodation(AccommodationVO vo) {
		}

		@Override
		public List<AccommodationVO> getListHostAccommodation(AccommodationVO vo) {
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public void deleteAccommodation(int aseq) {
		}

		@Override
		public List<AccommodationVO> getAccByRegion(String address) {
			return new ArrayList<AccommodationVO>();
		}

		private static AccommodationVO buildSample() {
			AccommodationVO vo = new AccommodationVO();
			vo.setAseq(10);
			vo.setAname("Sample Stay");
			vo.setHemail("host@example.com");
			vo.setRname("Deluxe");
			vo.setCategory(2);
			vo.setAddress("Seoul");
			vo.setTel("010-0000-0000");
			vo.setBestyn("Y");
			vo.setAimage("acc.jpg");
			vo.setRimage("room.jpg");
			return vo;
		}
	}
}
