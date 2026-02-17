package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import com.ezen.biz.dao.HostDAO;
import com.ezen.biz.dto.HostVO;

import utils.Criteria;

@Service
public class HostServiceImpl implements HostService {

	@Autowired
	private HostDAO hDao;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Override
	public int confirmEmail(String hemail) {
		return hDao.confirmEmail(hemail);
	}

	@Override
	public int loginHost(HostVO vo) {
		String key = "host:" + vo.getHemail();
		if (loginAttemptService.isLocked(key)) {
			return -2;
		}

		String storedPassword = hDao.getPasswordByEmail(vo.getHemail());
		if (storedPassword == null) {
			loginAttemptService.recordFailed(key);
			return -1;
		}

		boolean matches = passwordMatches(vo.getPwd(), storedPassword);
		if (!matches) {
			loginAttemptService.recordFailed(key);
			return 0;
		}

		// Transparent migration from legacy plaintext to BCrypt after first successful login.
		if (!isBcryptHash(storedPassword)) {
			HostVO migration = new HostVO();
			migration.setHemail(vo.getHemail());
			migration.setPwd(hashPassword(vo.getPwd()));
			hDao.changePwd(migration);
		}

		loginAttemptService.recordSuccess(key);
		return 1;
	}

	@Override
	public void insertHost(HostVO vo) {
		vo.setPwd(hashPassword(vo.getPwd()));
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
		vo.setPwd(hashPassword(vo.getPwd()));
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

	private String hashPassword(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12));
	}

	private boolean passwordMatches(String rawPassword, String storedPassword) {
		if (isBcryptHash(storedPassword)) {
			return BCrypt.checkpw(rawPassword, storedPassword);
		}
		return rawPassword.equals(storedPassword);
	}

	private boolean isBcryptHash(String candidate) {
		return candidate != null && (candidate.startsWith("$2a$") || candidate.startsWith("$2b$")
				|| candidate.startsWith("$2y$"));
	}

	

}

