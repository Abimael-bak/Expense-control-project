package com.Gastos.controll.resource;

import java.net.URI;
import java.util.Map;

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

import com.Gastos.controll.entities.User;
import com.Gastos.controll.service.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	
	private UserService userService;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findById(@PathVariable Long id){
		User user = userService.findById(id);
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping
	public ResponseEntity<User> insert(@RequestBody  User obj){
		User user = userService.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(user.getId()).toUri();
		
		return ResponseEntity.created(uri).body(user);
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<User> login(@RequestBody Map<String,String> body){
		String email = body.get("email");
		String password = body.get("password");
		
		User user = userService.login(email, password);
		
		
		return ResponseEntity.ok().body(user); 
	}
	
	@PutMapping(value = "/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User obj){
    	User user = userService.update(id, obj);
    	return ResponseEntity.ok().body(user);
    }

   @DeleteMapping(value = "/{id}") 
   public ResponseEntity<Void> delete(@PathVariable Long id){
	   userService.Delete(id);
	   return ResponseEntity.noContent().build();
   }

}
