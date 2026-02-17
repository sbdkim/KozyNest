package com.ezen.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import com.ezen.biz.dto.AccommodationVO;
import com.ezen.biz.service.AccommodationService;

import utils.Criteria;

public class AccommodationControllerTest {

	private AccommodationController controller;
	private AccommodationServiceStub serviceStub;

	@Before
	public void setUp() throws Exception {
		controller = new AccommodationController();
		serviceStub = new AccommodationServiceStub();
		setField(controller, "accommodationService", serviceStub);
	}

	@Test
	public void accSearchListFallsBackOnInvalidPaging() {
		Model model = new ExtendedModelMap();

		String view = controller.accSearchList("abc", "xyz", "  seoul  ", "", "", "", model);

		assertEquals("accommodation/accList", view);
		assertEquals("seoul", serviceStub.lastAddress);
		assertEquals(1, serviceStub.lastCriteria.getPageNum());
		assertEquals(10, serviceStub.lastCriteria.getRowsPerPage());
		assertEquals("seoul", model.asMap().get("key"));
	}

	@Test
	public void searchViewNormalizesAddress() {
		Model model = new ExtendedModelMap();
		AccommodationVO vo = new AccommodationVO();
		vo.setAddress("  busan ");

		String view = controller.searchView(vo, model);

		assertEquals("accommodation/accList", view);
		assertEquals("busan", serviceStub.lastAddressFromSearch);
		assertTrue(model.asMap().containsKey("accommodationList"));
	}

	private static void setField(Object target, String fieldName, Object value) throws Exception {
		Field field = target.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(target, value);
	}

	private static class AccommodationServiceStub implements AccommodationService {

		private String lastAddress;
		private String lastAddressFromSearch;
		private Criteria lastCriteria;

		@Override
		public AccommodationVO getAccommodation(AccommodationVO vo) {
			return null;
		}

		@Override
		public List<AccommodationVO> selectAccByAdd(String address) {
			lastAddressFromSearch = address;
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public List<AccommodationVO> getListAcc() {
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public List<AccommodationVO> getListAccWithPaging(Criteria criteria, String address) {
			lastCriteria = criteria;
			lastAddress = address;
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public List<AccommodationVO> getlistHostAccWithPaging(Criteria criteria, String hemail) {
			return new ArrayList<AccommodationVO>();
		}

		@Override
		public int countAccList(String address) {
			return 0;
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
	}
}
