package com.ezen.biz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.AdminDAO;
import com.ezen.biz.dto.AdminVO;
import com.ezen.biz.dto.HostVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminDAO aDao;

	@Override
	public int adminCheck(AdminVO vo) {
		String pwd_in_db = aDao.adminCheck(vo.getEmail());
		if (pwd_in_db == null) {
			return -1; // admin email does not exist
		} else if (pwd_in_db.equals(vo.getPwd())) {
			return 1; // valid admin credentials
		} else {
			return 0; // password mismatch
		}
	}

	@Override
	public AdminVO getAdmin(String email) {
		return aDao.getAdmin(email);
	}

	@Override
	public void updateAdmin(AdminVO vo) {
		aDao.updateAdmin(vo);
	}

	@Override
	public String selectEmailByNamePhone(AdminVO vo) {
		return aDao.selectEmailByNamePhone(vo);
	}

	@Override
	public String selectPwdByEmailNamePhone(AdminVO vo) {
		return aDao.selectPwdByEmailNamePhone(vo);
	}

	@Override
	public int loginAdmin(HostVO vo) {
		String email = vo.getHemail();
		String inputPwd = vo.getPwd();
		return aDao.loginAdmin(email, inputPwd);

	}

}
