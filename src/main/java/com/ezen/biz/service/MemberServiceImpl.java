package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.mindrot.jbcrypt.BCrypt;

import com.ezen.biz.dao.MemberDAO;
import com.ezen.biz.dto.MemberVO;

import utils.Criteria;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberDAO mDao;

	@Autowired
	private LoginAttemptService loginAttemptService;

	@Override
	public int confirmEmail(String email) {
		return mDao.confirmEmail(email);
	}

	@Override
	public int loginMember(MemberVO vo) {
		String key = "member:" + vo.getEmail();
		if (loginAttemptService.isLocked(key)) {
			return -2;
		}

		String storedPassword = mDao.getPasswordByEmail(vo.getEmail());
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
			MemberVO migration = new MemberVO();
			migration.setEmail(vo.getEmail());
			migration.setPwd(hashPassword(vo.getPwd()));
			mDao.changePwd(migration);
		}

		loginAttemptService.recordSuccess(key);
		return 1;
	}

	@Override
	public void insertMember(MemberVO vo) {
		vo.setPwd(hashPassword(vo.getPwd()));
		mDao.insertMember(vo);
	}

	@Override
	public MemberVO getMember(String email) {
		return mDao.getMember(email);
	}

	@Override
	public void updateMember(MemberVO vo) {
		if (vo.getPwd() != null && !vo.getPwd().isEmpty() && !isBcryptHash(vo.getPwd())) {
			vo.setPwd(hashPassword(vo.getPwd()));
		}
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
		vo.setPwd(hashPassword(vo.getPwd()));
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

