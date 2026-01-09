package com.Gastos.controll.resource;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Expense> findById(@PathVariable Long id){
		Expense expense = expenseService.findById(id);
		return ResponseEntity.ok().body(expense);
	}
	
	@PostMapping
	public ResponseEntity<Expense> insert(@RequestBody Expense obj){
		 Expense expense = expenseService.insert(obj);
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		 return ResponseEntity.created(uri).body(expense);
		
	}
	
    @PutMapping(value = "/{id}")
    public ResponseEntity<Expense> update(@RequestBody Expense obj, @PathVariable Long id){
    	Expense expense = expenseService.update(obj, id);
    	return ResponseEntity.ok().body(expense);
    }
    
    @DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		expenseService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
