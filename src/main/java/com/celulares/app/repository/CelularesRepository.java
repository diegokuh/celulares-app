package com.celulares.app.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CelularesRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String QUERY = "SELECT DISTINCT modelo from celulares where sistema = ? AND almacenamiento = ? AND camara = ? AND color = ? AND operador = ?";
	
	public List<String> getSos(){
		return jdbcTemplate.queryForList("SELECT DISTINCT sistema FROM celulares", String.class);
	}
	
	public List<String> getAlmacenamiento(){
		return jdbcTemplate.queryForList("SELECT DISTINCT almacenamiento FROM celulares", String.class);
	}
	
	public List<String> getCamara(){
		return jdbcTemplate.queryForList("SELECT DISTINCT camara FROM celulares", String.class);
	}
	
	public List<String> getColor(){
		return jdbcTemplate.queryForList("SELECT DISTINCT color FROM celulares", String.class);
	}
	
	public List<String> getOperador(){
		return jdbcTemplate.queryForList("SELECT DISTINCT operador FROM celulares", String.class);
	}
	
	public List<String> getCelulares(Object ... values){
		return jdbcTemplate.queryForList(QUERY, String.class, values);
	}
	
}
