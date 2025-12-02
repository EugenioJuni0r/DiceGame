package com.lunaltas.dicegame.service;

import java.util.List;

import com.lunaltas.dicegame.domain.Gamble;

public interface GambleService{

	void save(Gamble gamble);
	
	void update(Gamble gamble);
	
	void delete(Long id);
	
	Gamble findById(Long id);
	
	List<Gamble> findAll();

  boolean hasBets(Long id);
	
}