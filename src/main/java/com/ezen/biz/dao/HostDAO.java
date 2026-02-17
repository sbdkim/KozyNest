package com.ezen.biz.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.biz.dto.HostVO;

import utils.Criteria;

@Repository
public class HostDAO {

	@Autowired
	private SqlSessionTemplate mybatis;

	public int confirmEmail(String hemail) {
		String pwd = mybatis.selectOne("HostMapper.confirmEmail", hemail);
		return (pwd != null) ? 1 : 0;
	}

	public int loginHost(HostVO vo) {
		int result = -1;
		String pwd = mybatis.selectOne("HostMapper.confirmEmail", vo);
		if (pwd == null) {
			result = -1;
		} else if (pwd.contentEquals(vo.getPwd())) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	public String getPasswordByEmail(String hemail) {
		return mybatis.selectOne("HostMapper.confirmEmail", hemail);
	}

	public HostVO getStatus(String hemail) {
		return mybatis.selectOne("HostMapper.getHost", hemail);
	}

	public void insertHost(HostVO vo) {
		mybatis.insert("HostMapper.insertHost", vo);
	}

	public HostVO getHost(String hemail) {
		return mybatis.selectOne("HostMapper.getHost", hemail);
	}

	public void updateHost(String hemail) {
		mybatis.update("HostMapper.updateHost", hemail);
	}

	public String selectEmailByNamePhone(HostVO vo) {
		return mybatis.selectOne("HostMapper.selectEmailByNamePhone", vo);
	}

	public String selectPwdByEmailNamePhone(HostVO vo) {
		return mybatis.selectOne("HostMapper.selectPwdByEmailNamePhone", vo);
	}

	public void changePwd(HostVO vo) {
		mybatis.update("HostMapper.changePwd", vo);
	}

	public List<HostVO> listHost(String name) {
		return mybatis.selectList("HostMapper.getHostList", name);
	}

	public void updateHostStatus(String email) {
		mybatis.update("HostMapper.approveHost", email);
	}

	public void deleteHost(String email) {
		mybatis.delete("HostMapper.deleteHost", email);
	}

	public List<HostVO> listHostWithPaging(Criteria criteria, String name) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("criteria", criteria);
		map.put("name", name);
		return mybatis.selectList("HostMapper.listHostWithPaging", map);
	}

	public int countHostList(String name) {
		return mybatis.selectOne("HostMapper.countHostList", name);
	}

}