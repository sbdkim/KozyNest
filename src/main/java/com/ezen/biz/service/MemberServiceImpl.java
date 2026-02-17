package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.MemberDAO;
import com.ezen.biz.dto.MemberVO;

import utils.Criteria;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mDao;

	@Override
	public int confirmEmail(String email) {
		return mDao.confirmEmail(email);
	}

	@Override
	public int loginMember(MemberVO vo) {
		return mDao.loginMember(vo);
	}

	@Override
	public void insertMember(MemberVO vo) {
		mDao.insertMember(vo);
	}

	@Override
	public MemberVO getMember(String email) {
		return mDao.getMember(email);
	}

	@Override
	public void updateMember(MemberVO vo) {
		mDao.updateMember(vo);
	}

	@Override
	public String selectEmailByNamePhone(MemberVO vo) {
		return mDao.selectEmailByNamePhone(vo);
	}

	@Override
	public String selectPwdByEmailNamePhone(MemberVO vo) {
		return mDao.selectPwdByEmailNamePhone(vo);
	}

	@Override
	public List<MemberVO> getListMember() {
		return mDao.listMember();
	}

	@Override
	public void changePwd(MemberVO vo) {
		mDao.changePwd(vo);
	}

	@Override
	public List<MemberVO> getMemberList(String name) {
		return mDao.getMemberList(name);
	}

	@Override
	public void deleteMember(String email) {
		mDao.deleteMember(email);

	}

	@Override
	public List<MemberVO> getListMemberWithPaging(Criteria criteria, String name) {
		return mDao.getListMemberWithPaging(criteria, name);
	}

	@Override
	public int countMemberList(String name) {
		return mDao.countMemberList(name);
	}

}

