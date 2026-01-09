package com.Gastos.controll.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Gastos.controll.entities.Expense;
import com.Gastos.controll.service.ExpenseService;


@RestController
@RequestMapping(value = "expenses")
public class ExpenseResource {
 
	@Autowired
	private ExpenseService expenseService;
	
	@GetMapping
	public ResponseEntity<List<Expense>> findAll(){
		List<Expense> expenses = expenseService.findAll();
		return ResponseEntity.ok().body(expenses);
	}
}
