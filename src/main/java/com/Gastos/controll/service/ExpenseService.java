package com.Gastos.controll.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gastos.controll.entities.Expense;
import com.Gastos.controll.repository.ExpenseRepository;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	
	public List<Expense> findAll(){
		return expenseRepository.findAll();
	}
}
