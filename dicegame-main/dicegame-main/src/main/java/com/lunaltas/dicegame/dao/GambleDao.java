package com.lunaltas.dicegame.dao;

import java.util.List;

import com.lunaltas.dicegame.domain.Gamble;

public interface GambleDao {

    void save(Gamble gamble);

    void update(Gamble gamble);

    void delete(Long id);

    Gamble findById(Long id);

    List<Gamble> findAll();
}
