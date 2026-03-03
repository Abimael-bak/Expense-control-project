package com.Gastos.controll.resource;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.Gastos.controll.entities.User;
import com.Gastos.controll.entities.DTO.UserRequest;
import com.Gastos.controll.entities.DTO.UserResponse;
import com.Gastos.controll.repository.UserRepository;
import com.Gastos.controll.service.UserService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	
	private UserService userService;
	
	@Autowired
	
	private UserRepository userRepository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable UserRequest Dto){
		User user = userService.findById(Dto.id());
		
		return ResponseEntity.ok().body(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getExpenses()));
	}
	
	@PostMapping
	public ResponseEntity<Void> insert(@RequestBody  UserRequest Dto){
		User user = new User();
		user.setName(Dto.name());
		user.setEmail(Dto.email());
		user.setPassword(Dto.Password());
		
		var obj = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}/expenses")
	public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable UserRequest Dto) {
	    Optional<User> user = userRepository.findById(Dto.id());
	    if (!user.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	     
	    UserResponse DTO = new UserResponse(
	    		user.get().getId(),
	    		user.get().getName(),
	    		user.get().getEmail(),
	    		user.get().getExpenses()
	    		);
	     
	    return ResponseEntity.ok(DTO.expenses());
	}

	@PostMapping(value = "/login")
	public ResponseEntity<UserResponse> login(@RequestBody UserRequest Dto){
		String email = Dto.email();
		String password = Dto.Password();
		
		User user = userService.login(email, password);
		
		
		return ResponseEntity.ok().body(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getExpenses())); 
	}
	
	@PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest Dto){
    	User user = new User();
    			user.setName(Dto.name());
    			user.setEmail(Dto.email());
    			user.setPassword(Dto.Password());
    			
    		var obj = userService.update(id, user );
    		
    	return ResponseEntity.ok().body(new UserResponse(obj.getId(), obj.getName(), obj.getEmail(), obj.getExpenses()));
    }

   @DeleteMapping(value = "/{id}") 
   public ResponseEntity<Void> delete(@PathVariable UserRequest Dto){
	   userService.Delete(Dto.id());
	   return ResponseEntity.noContent().build();
   }

}
