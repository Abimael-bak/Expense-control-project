package com.Gastos.controll.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.Gastos.controll.entities.Role;
import com.Gastos.controll.entities.User;
import com.Gastos.controll.repository.RoleRepository;
import com.Gastos.controll.repository.UserRepository;
import com.Gastos.controll.service.Exception.ResourceNotFoundException;
import com.Gastos.controll.service.Exception.UserException;

@Service
public class UserService {
 
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	
	RoleRepository roleRepository;
	
	
   public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
		   RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

   public User findById(Long id) {
	   return userRepository.findById(id)
			   .orElseThrow(()-> new ResourceNotFoundException("Resource not found. id"+ id));
   }
	
   public User insert(User obj) {

	  if(userRepository.findByEmail(obj.getEmail()).isPresent()) {
		throw new UserException("Email já está em uso!");
	  }
	  
	  if (roleRepository.findByName(Role.Values.BASIC.name()) == null) {
	        var newRole = new Role(null, Role.Values.BASIC.name());
	         roleRepository.save(newRole);
	    }

       var basicRole = roleRepository.findByName(Role.Values.BASIC.name());
       
       obj.setRoles(Set.of(basicRole));
	return userRepository.save(obj);
	   
   }
	
  public User login(String email, String password) {
	  
	User user = userRepository.findByEmail(email)
			.orElseThrow(()-> new UserException("Email não cadastrado!"));
	
	if(!user.testPassword(password, passwordEncoder)) {
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