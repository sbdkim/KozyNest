package com.ezen.biz.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezen.biz.dao.MapDAO;
import com.ezen.biz.dto.Location;

@Service
public class MapServiceImpl implements MapService {

	@Autowired
	private MapDAO mDao;

	@Override
	public ArrayList<Location> getAllLocations() {
		return (ArrayList<Location>) mDao.getAllLocation();
	}

}

