package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.HostDAO;
import com.ezen.biz.dto.HostVO;

import utils.Criteria;

@Service
public class HostServiceImpl implements HostService {

	@Autowired
	private HostDAO hDao;

	@Override
	public int confirmEmail(String hemail) {
		return hDao.confirmEmail(hemail);
	}

	@Override
	public int loginHost(HostVO vo) {
		return hDao.loginHost(vo);
	}

	@Override
	public void insertHost(HostVO vo) {
		hDao.insertHost(vo);

	}

	@Override
	public HostVO getHost(String hemail) {
		return hDao.getHost(hemail);
	}
	
	@Override
	public HostVO getStatus(String hemail) {
		
		return hDao.getStatus(hemail);
	}

	@Override
	public void updateHost(String hemail) {
		hDao.updateHost(hemail);

	}

	@Override
	public String selectEmailByNamePhone(HostVO vo) {
		return hDao.selectEmailByNamePhone(vo);
	}

	@Override
	public String selectPwdByEmailNamePhone(HostVO vo) {
		return hDao.selectPwdByEmailNamePhone(vo);
	}

	@Override
	public void changePwd(HostVO vo) {
		hDao.changePwd(vo);
	}

	@Override
	public List<HostVO> getListHost(String name) {
		return hDao.listHost(name);
	}

	@Override
	public void updateHostStatus(String email) {
		hDao.updateHostStatus(email);

	}

	@Override
	public void deleteHost(String email) {
		hDao.deleteHost(email);
	}

	@Override
	public List<HostVO> getListHostWithPaging(Criteria criteria, String name) {
		return hDao.listHostWithPaging(criteria, name);
	}

	@Override
	public int countHostList(String name) {
		return hDao.countHostList(name);
	}

	

}

