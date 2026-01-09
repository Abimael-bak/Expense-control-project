package com.Gastos.controll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gastos.controll.entities.Category;
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
	
	public Double totalByCategory(Category obj) {
		List<Expense> expenses = findAll().stream().filter(p -> p.getCategory().equals(obj)).toList();
		Double total = 0.00;
		for(Expense e: expenses){
			total += e.getAmount();
		}
		
		return total;
	}
	
	public List<Expense> findByValue(Double value){
		 List<Expense> expenses = findAll().stream().filter(p -> p.getAmount()> value).toList();
		 return expenses;
		 
	}
	
	public Expense findById(Long id) {
		Optional<Expense> expense = expenseRepository.findById(id);
		return expense.orElseThrow();
		
	}
}
