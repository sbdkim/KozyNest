package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.RoomDAO;
import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.RoomVO;

import utils.Criteria;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomDAO rDao;

	@Override
	public List<RoomVO> getRoomByAcc(BookingVO vo) {
		return rDao.selectRoomByAcc(vo);
	}

	@Override
	public List<RoomVO> getRoomListWithPaging(Criteria criteria, int aseq) {

		return rDao.listRoomWithPaging(criteria, aseq);
	}

	@Override
	public int countRoomList(int aseq) {
		return rDao.countRoomList(aseq);
	}

	@Override
	public RoomVO getRoomByRseq(int rseq) {
		return rDao.selectRoomByRseq(rseq);
	}

	@Override
	public RoomVO getAccByRseq(int rseq) {
		return rDao.selectAccByRseq(rseq);
	}

	@Override
	public void insertRoom(RoomVO vo) {
		rDao.insertRoom(vo);
	}

	@Override
	public void updateRoom(RoomVO vo) {
		rDao.updateRoom(vo);
	}

	@Override
	public List<RoomVO> hostGetRoomByAcc(int aseq) {
		return rDao.hostSelectRoomByAcc(aseq);
	}

	@Override
	public void deleteRoom(int rseq) {
		rDao.deleteRoom(rseq);
	}

	@Override
	public RoomVO selectRoomByRseq(RoomVO vo) {
		return rDao.selectRoomByRseq(vo);
	}

}

