package com.Gastos.controll.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Gastos.controll.entities.User;
import com.Gastos.controll.repository.UserRepository;
import com.Gastos.controll.service.Exception.ResourceNotFoundException;
import com.Gastos.controll.service.Exception.UserException;

@Service
public class UserService {
 
	@Autowired
	private UserRepository userRepository;
	
   public User findById(Long id) {
	   return userRepository.findById(id)
			   .orElseThrow(()-> new ResourceNotFoundException("Resource not found. id"+ id));
   }
	
   public User insert(User obj) {

	  if(userRepository.findByEmail(obj.getEmail()).isPresent()) {
		throw new UserException("Email já está em uso!");
	  }
	return userRepository.save(obj);
	   
   }
	
  public User login(String email, String password) {
	  
	User user = userRepository.findByEmail(email)
			.orElseThrow(()-> new UserException("Email não cadastrado!"));
	
	if(!password.equals(user.getPassword())) {
		 throw new UserException("Senha incorreta!");
	}
	return user;
  }
  
  public User update(Long id, User obj) {
	  User user = findById(id);
	  
	  userRepository.findByEmail(obj.getEmail()).ifPresent(existing -> {
	        if (!existing.getId().equals(id)) {
	            throw new UserException("Email já está em uso por outro usuário!");
	        }
	    });
	 
		  user.setName(obj.getName());
		  user.setEmail(obj.getEmail());
		  
		 return  userRepository.save(user);
		 
		 
  }
  
  public void Delete(Long id) {
	  if(!userRepository.existsById(id)) {
		  throw new UserException("Usuário não encontrado!");
	  }
	  userRepository.deleteById(id);
  }
  
  
  
}