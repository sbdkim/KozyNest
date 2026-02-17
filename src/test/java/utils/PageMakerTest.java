package utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PageMakerTest {

	@Test
	public void makeQueryBuildsExpectedQueryString() {
		Criteria criteria = new Criteria();
		criteria.setRowsPerPage(10);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);

		String query = pageMaker.makeQuery(3);
		assertEquals("?pageNum=3&rowsPerPage=10", query);
	}

	@Test
	public void fieldInitCalculatesPaginationRangeWithoutNext() {
		Criteria criteria = new Criteria();
		criteria.setPageNum(1);
		criteria.setRowsPerPage(10);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(95);

		assertEquals(1, pageMaker.getStartPage());
		assertEquals(10, pageMaker.getEndPage());
		assertFalse(pageMaker.isPrev());
		assertFalse(pageMaker.isNext());
	}

	@Test
	public void fieldInitCalculatesPaginationRangeWithPrevAndNext() {
		Criteria criteria = new Criteria();
		criteria.setPageNum(11);
		criteria.setRowsPerPage(10);

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCriteria(criteria);
		pageMaker.setTotalCount(300);

		assertEquals(11, pageMaker.getStartPage());
		assertEquals(20, pageMaker.getEndPage());
		assertTrue(pageMaker.isPrev());
		assertTrue(pageMaker.isNext());
	}
}
