package utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CriteriaTest {

	@Test
	public void defaultConstructorSetsExpectedValues() {
		Criteria criteria = new Criteria();
		assertEquals(1, criteria.getPageNum());
		assertEquals(10, criteria.getRowsPerPage());
	}

	@Test
	public void setPageNumRejectsNonPositiveValues() {
		Criteria criteria = new Criteria();
		criteria.setPageNum(0);
		assertEquals(1, criteria.getPageNum());

		criteria.setPageNum(-5);
		assertEquals(1, criteria.getPageNum());
	}

	@Test
	public void setRowsPerPageAppliesBounds() {
		Criteria criteria = new Criteria();

		criteria.setRowsPerPage(3);
		assertEquals(5, criteria.getRowsPerPage());

		criteria.setRowsPerPage(25);
		assertEquals(20, criteria.getRowsPerPage());

		criteria.setRowsPerPage(12);
		assertEquals(12, criteria.getRowsPerPage());
	}
}
