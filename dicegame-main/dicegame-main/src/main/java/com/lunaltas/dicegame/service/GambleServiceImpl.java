package com.lunaltas.dicegame.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lunaltas.dicegame.dao.GambleDao;
import com.lunaltas.dicegame.domain.Gamble;

@Service @Transactional(readOnly = false)
public class GambleServiceImpl implements GambleService {
	
	@Autowired
	private GambleDao dao;

	@Override
	public void save(Gamble gamble) {
		dao.save(gamble);		
	}

	@Override
	public void update(Gamble gamble) {
		dao.update(gamble);		
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);		
	}

	@Override @Transactional(readOnly = true)
	public Gamble findById(Long id) {
		
		return dao.findById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Gamble> findAll() {
		
		return dao.findAll();
	}

	@Override
	public boolean hasBets(Long id) {
		Gamble g = findById(id);
		if (g == null) {
			return false;
		}
		return g.getBet() != null;
	}


}
