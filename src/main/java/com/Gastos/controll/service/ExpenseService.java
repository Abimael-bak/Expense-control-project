package com.Gastos.controll.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.Gastos.controll.entities.Category;
import com.Gastos.controll.entities.Expense;
import com.Gastos.controll.repository.CategoryRepository;
import com.Gastos.controll.repository.ExpenseRepository;
import com.Gastos.controll.resource.Exception.DataBaseException;
import com.Gastos.controll.service.Exception.ResourceNotFoundException;

@Service
public class ExpenseService {

	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<Expense> findAll(){
		return expenseRepository.findAll();
	}
	

    public Expense insert(Expense expense) {

        Category category = expense.getCategory();

        if (category != null && category.getName() != null) {

            Category existingCategory = categoryRepository
                    .findByName(category.getName())
                    .orElse(null);

            if (existingCategory == null) {
                Category newCategory = new Category();
                newCategory.setName(category.getName());
                category = categoryRepository.save(newCategory);
            } else {
                category = existingCategory;
            }

            expense.setCategory(category);
        }

        return expenseRepository.save(expense);
    }
	
	public Double totalExpense() {
		List<Expense> expenses = findAll();
		Double total = 0.00;
		for(Expense e: expenses){
			total += e.getAmount();
		}
		
		return total;
	}
	
	public Double totalByCategory(Long id) {
		List<Expense> expenses = findAll().stream().filter(p -> p.getCategory().getId().equals(id)).toList();
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
		return expense.orElseThrow(()-> new ResourceNotFoundException("Resource not found. id"+ id));
		
	}
	
	public Expense update(Expense obj, Long id) {

	    Expense expense = findById(id); 

	    updateDate(obj, expense);

	    return expenseRepository.save(expense);
	}
	
	public void updateDate(Expense obj, Expense expense) {

	    expense.setDescription(obj.getDescription());
	    expense.setMoment(obj.getMoment());
	    expense.setAmount(obj.getAmount());

	    if (obj.getCategory() != null) {

	        Category category;

	        if (obj.getCategory().getId() != null) {
	            category = categoryRepository.findById(obj.getCategory().getId())
	                .orElseThrow(() ->
	                    new ResourceNotFoundException("Category not found with name:  " + obj.getCategory().getId())
	                );

	        } else if (obj.getCategory().getName() != null) {
	            category = categoryRepository.findByName(obj.getCategory().getName())
	                .orElseThrow(() ->
	                    new ResourceNotFoundException("Category not found with name: " + obj.getCategory().getName())
	                );
	        } else {
	            throw new RuntimeException("Category inválida");
	        }

	        expense.setCategory(category);
	    }
	}
	
	public void delete(Long id) {
		
	try {	
		Expense expense = findById(id);
		if(expense != null) {
			expenseRepository.delete(expense);
	    }else {
	    	throw new ResourceNotFoundException("Resource not found. Id"+id);
	    }
	}catch(EmptyResultDataAccessException e) {
		throw new ResourceNotFoundException("Resource not Found. Id"+ id);
	}catch(DataIntegrityViolationException r) {
		throw new DataBaseException(r.getMessage());
	   }
     }
}