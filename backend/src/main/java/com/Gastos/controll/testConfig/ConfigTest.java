package com.Gastos.controll.testConfig;

import java.time.Instant;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.Gastos.controll.entities.Category;
import com.Gastos.controll.entities.Expense;
import com.Gastos.controll.entities.User;
import com.Gastos.controll.repository.CategoryRepository;
import com.Gastos.controll.repository.ExpenseRepository;
import com.Gastos.controll.repository.UserRepository;

@Configuration
@Profile("test")
public class ConfigTest implements CommandLineRunner  {

	
	@Autowired
	 private ExpenseRepository expenseRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	@Override
	public void run(String... args) throws Exception {
		
	Category ct1 = new Category(null, "Electronic");
	Category ct2 = new Category(null, "Home");
	Category ct3 = new Category(null, "Trip");
	
	categoryRepository.saveAll(Arrays.asList(ct1,ct2,ct3));
	
	User u1= new User(null,"Abimael de jesus abreu", "abimaelabreu73@","bima0000");
	User u2= new User(null,"Eduardo oliveira mota", "eduardooliveira32@","edu0000");
	User u3= new User(null,"Isaque levi", "isaaquelevi90@","levi0000");

	userRepository.saveAll(Arrays.asList(u1,u2,u3));
	
	Expense ex1 = new Expense(null,"Celular novo",3500.00,Instant.parse("2024-10-17T15:53:07Z"),ct1,u1);
	Expense ex2 = new Expense(null,"Férias em Paris",12000.00,Instant.parse("2024-12-23T08:30:40Z"),ct3,u2);
	Expense ex3 = new Expense(null,"Guarda roupa",2300.00,Instant.parse("2024-11-13T14:19:17Z"),ct2,u3);
	
	expenseRepository.saveAll(Arrays.asList(ex1,ex2,ex3));

	}

}
