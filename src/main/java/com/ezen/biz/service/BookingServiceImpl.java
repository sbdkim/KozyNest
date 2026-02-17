package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.BookingDAO;
import com.ezen.biz.dto.AccommodationVO;
import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.SalesQuantity;

@Service
public class BookingServiceImpl implements BookingService {

	@Autowired
	private BookingDAO bDao;

	@Override
	public void insertBooking(BookingVO vo) {
		bDao.insertBooking(vo);
	}

	@Override
	public List<BookingVO> getListBookByEmail(BookingVO vo) {
		return bDao.listBookByEmail(vo);
	}

	@Override
	public BookingVO selectBookByBseq(BookingVO vo) {
		return bDao.selectBookByBseq(vo);
	}

	@Override
	public void updateBookByBseq(BookingVO vo) {
		bDao.updateBookByBseq(vo);
	}

	@Override
	public void deleteBookByBseq(int bseq) {
		bDao.deleteBookByBseq(bseq);
	}

	@Override
	public List<BookingVO> getListBooking() {
		return bDao.getListBooking();
	}

	@Override
	public List<Integer> getSeqBooking(BookingVO vo) {
		return bDao.selectSeqBooking(vo);
	}

	@Override
	public List<BookingVO> getListBookingByEmail(BookingVO vo) {
		return bDao.listBookingByEmail(vo);
	}

	@Override
	public List<BookingVO> getBookingListByAseq(BookingVO vo) {
		return bDao.bookingListByAseq(vo);
	}
	
	@Override

	public List<SalesQuantity> getListBookingSales(AccommodationVO vo) {
		return bDao.getListBookingSales(vo);
	}

	@Override
	public List<BookingVO> listBestAccom() {
		return bDao.listBestAccom();
	}


	@Override
	public List<BookingVO> getListBookByAseq(BookingVO vo) {
		return bDao.listBookByAseq(vo);
	}

	@Override
	public List<BookingVO> getMonthlyRevenue() {
		return bDao.getMonthlyRevenue();
	}

	@Override
	public BookingVO getBookByBseq(int bseq) {
		return bDao.selectBookByBseq(bseq);
	}


}

