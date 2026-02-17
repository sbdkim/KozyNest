package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.MemberVO;

import utils.Criteria;

@Repository
public class MemberDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	public int confirmEmail(String email) {
		String pwd = mybatis.selectOne("MemberMapper.confirmEmail", email);
		return (pwd != null) ? 1 : 0;
	}

	public int loginMember(MemberVO vo) {
		int result = -1;
		String pwd = mybatis.selectOne("MemberMapper.confirmEmail", vo);
		if (pwd == null) {
			result = -1;
		} else if (pwd.contentEquals(vo.getPwd())) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	public String getPasswordByEmail(String email) {
		return mybatis.selectOne("MemberMapper.confirmEmail", email);
	}

	public void insertMember(MemberVO vo) {
		mybatis.insert("MemberMapper.insertMember", vo);
	}

	public MemberVO getMember(String email) {
		return mybatis.selectOne("MemberMapper.getMember", email);
	}

	public void updateMember(MemberVO vo) {
		mybatis.update("MemberMapper.updateMember", vo);
	}

	public String selectEmailByNamePhone(MemberVO vo) {
		return mybatis.selectOne("MemberMapper.selectEmailByNamePhone", vo);
	}

	public String selectPwdByEmailNamePhone(MemberVO vo) {
		return mybatis.selectOne("MemberMapper.selectPwdByEmailNamePhone", vo);
	}

	public List<MemberVO> listMember() {
		return mybatis.selectList("MemberMapper.listMember");
	}

	public void changePwd(MemberVO vo) {
		mybatis.update("MemberMapper.changePwd", vo);
	}

	public List<MemberVO> getMemberList(String name) {
		return mybatis.selectList("MemberMapper.memberList", name);
	}

	public void deleteMember(String email) {
		mybatis.delete("MemberMapper.deleteMember", email);
	}

	public List<MemberVO> getListMemberWithPaging(Criteria criteria, String name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("name", name);
		return mybatis.selectList("MemberMapper.listMemberWithPaging", map);
	}

	public int countMemberList(String name) {
		return mybatis.selectOne("MemberMapper.countMemberList", name);
	}

}