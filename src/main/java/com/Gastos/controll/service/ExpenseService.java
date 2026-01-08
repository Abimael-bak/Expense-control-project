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
	
	public Expense insert(Expense obj) {
		return expenseRepository.save(obj);
	}
	
	public Double totalExpense() {
		List<Expense> expenses = findAll();
		Double total = 0.00;
		for(Expense e: expenses){
			total += e.getAmount();
		}
		
		return total;
	}
}
