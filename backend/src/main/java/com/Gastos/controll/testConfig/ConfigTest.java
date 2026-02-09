package com.Gastos.controll.testConfig;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.Gastos.controll.entities.Category;
import com.Gastos.controll.entities.Expense;
import com.Gastos.controll.repository.CategoryRepository;
import com.Gastos.controll.repository.ExpenseRepository;

@Configuration
@Profile("test")
public class ConfigTest implements CommandLineRunner  {

	
	@Autowired
	 private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
	Category ct1 = new Category(null, "Electronic");
	Category ct2 = new Category(null, "Home");
	Category ct3 = new Category(null, "Trip");
	
	categoryRepository.saveAll(Arrays.asList(ct1,ct2,ct3));
/*
	Expense ex1 = new Expense(null,"new cell phone",3500.00,Instant.parse("2024-10-17T15:53:07Z"),ct1);
	Expense ex2 = new Expense(null,"vacation in paris",12000.00,Instant.parse("2024-12-23T08:30:40Z"),ct3);
	Expense ex3 = new Expense(null,"clothes washer",2300.00,Instant.parse("2024-11-13T14:19:17Z"),ct2);
	
	expenseRepository.saveAll(Arrays.asList(ex1,ex2,ex3));
	*/
	}

}
