package com.celulares.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.celulares.app.repository.CelularesRepository;

@Service
public class CelularesService {

	@Autowired
	private CelularesRepository celularesRepository;
	
	public List<String> getSosForCombo(){
		List<String> elements = getInitialComboOption();
		elements.addAll(celularesRepository.getSos());
		return elements;
	}
	
	public List<String> getAlmacenamientosForCombo(){
		List<String> elements = getInitialComboOption();
		elements.addAll(celularesRepository.getAlmacenamiento());
		return elements;
	}
	
	public List<String> getCamarasForCombo(){
		List<String> elements = getInitialComboOption();
		elements.addAll(celularesRepository.getCamara());
		return elements;
	}
	
	public List<String> getColoresForCombo(){
		List<String> elements = getInitialComboOption();
		elements.addAll(celularesRepository.getColor());
		return elements;
	}
	
	public List<String> getOperadoresForCombo(){
		List<String> elements = getInitialComboOption();
		elements.addAll(celularesRepository.getOperador());
		return elements;
	}
	
	public List<String> goForIt(Object... values){
		return celularesRepository.getCelulares(values);
	}
	
	private List<String> getInitialComboOption(){
		List<String> elements = new ArrayList<>();
		elements.add("-----Selecciona-----");
		return elements;
	}
}
