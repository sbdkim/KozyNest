package com.ezen.biz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.AccommodationDAO;
import com.ezen.biz.dto.AccommodationVO;

import utils.Criteria;

@Service
public class AccommodationServiceImpl implements AccommodationService {

	@Autowired
	private AccommodationDAO aDao;

	
	@Override
	public AccommodationVO getAccommodation(AccommodationVO vo) {
	
		return aDao.getAccommodation(vo);
	}
	@Override
	public List<AccommodationVO> selectAccByAdd(String address) {
		return aDao.selectAccByAdd(address);
	}

	@Override
	public List<AccommodationVO> getListAcc() {
		return aDao.listAcc();
	}

	@Override
	public List<AccommodationVO> getListAccWithPaging(Criteria criteria, String address) {
		return aDao.listAccWithPaging(criteria, address);
	}

	@Override
	public int countAccList(String address) {
		return aDao.countAccList(address);
	}

	@Override
	public String getNameByAseq(int aseq) {
		
		return aDao.getNameByAseq(aseq);
	}

	@Override
	public void insertAccommodation(AccommodationVO vo) {
		
		aDao.insertAccommodation(vo);		
	}

	@Override
	public void updateAccommodation(AccommodationVO vo) {
		
		aDao.updateAccommodation(vo);
	}

	@Override
	public List<AccommodationVO> getlistHostAccWithPaging(Criteria criteria, String hemail) {
		
		return aDao.listHostAccWithPaging(criteria, hemail);
	}

	@Override
	public List<AccommodationVO> getListHostAccommodation(AccommodationVO vo) {
		
		return aDao.listHostAccommodation(vo);
	}

	@Override
	public int countHostAccList(String hemail) {
	
		return aDao.countHostAccList(hemail);
	}
	@Override
	public void deleteAccommodation(int aseq) {
		aDao.deleteAccommodation(aseq);		
	}

	@Override
	public List<AccommodationVO> getAccByRegion(String address) {
		
		return aDao.getAccByRegion(address);
	}




}

