package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.BookingVO;
import com.ezen.biz.dto.RoomVO;

import utils.Criteria;

@Repository
public class RoomDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	public List<RoomVO> selectRoomByAcc(BookingVO vo) {
		return mybatis.selectList("RoomMapper.selectRoomByAcc", vo);
	}// selectRoomByAcc

	public List<RoomVO> listRoomWithPaging(Criteria criteria, int aseq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("aseq", aseq);
		return mybatis.selectList("RoomMapper.listRoomWithPaging", map);
	}// selectRoomByAcc

	public int countRoomList(int aseq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("aseq", aseq);
		return mybatis.selectOne("RoomMapper.countRoomList", map);
	}

	public RoomVO selectRoomByRseq(RoomVO vo) {
		return mybatis.selectOne("RoomMapper.selectRoomByRseq", vo);
	}
	
	
	public RoomVO selectRoomByRseq(int resq) {
		return mybatis.selectOne("RoomMapper.selectRoomByRseq", resq);
	}
	
	public RoomVO selectAccByRseq(int rseq) {
		return mybatis.selectOne("RoomMapper.selectAccByRseq", rseq);
	}

	public void insertRoom(RoomVO vo) {
		mybatis.insert("RoomMapper.insertRoom", vo);
	}

	public void updateRoom(RoomVO vo) {
		mybatis.update("RoomMapper.updateRoom", vo);
	}
	
	public void deleteRoom(int rseq) {
		mybatis.delete("RoomMapper.deleteRoom", rseq);
	}
	
	public List<RoomVO> hostSelectRoomByAcc(int aseq) {
		return mybatis.selectList("RoomMapper.hostSelectRoomByAcc", aseq);
	}
	
}//RoomDAO

