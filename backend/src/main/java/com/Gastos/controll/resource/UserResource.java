package com.Gastos.controll.resource;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
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
import com.Gastos.controll.entities.DTO.LoginResponse;
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
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	
	private JwtEncoder jwtEncoder;
	
	
 
	public UserResource(UserService userService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder,
			JwtEncoder jwtEncoder) {
		this.userService = userService;
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtEncoder = jwtEncoder;
	}

	@PreAuthorize("hasRole ('ADMIN')")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserResponse> findById(@PathVariable Long id){
		User user = userService.findById(id);
		
		return ResponseEntity.ok().body(new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getExpenses()));
	}
	
	@PostMapping("/cadastro")
	public ResponseEntity<Void> insert(@RequestBody  UserRequest Dto){
		User user = new User();
		user.setName(Dto.name());
		user.setEmail(Dto.email());
		user.setPassword(passwordEncoder.encode(Dto.password()));
		
		var obj = userService.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping("/{id}/expenses")
	public ResponseEntity<List<Expense>> getExpensesByUser(@PathVariable Long id) {
	    Optional<User> user = userRepository.findById(id);
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
	public ResponseEntity<LoginResponse> login(@RequestBody UserRequest Dto){
		String email = Dto.email();
		String password = Dto.password();
		
		User user = userService.login(email, password);
		
		if(user == null) {
			throw new  RuntimeException("usuario não encontrado");
		}
		
		var instant = Instant.now();
		var expiresIn = 600L;
		
		var scope = user.getRoles().stream()
				.map(Role -> Role.getName()).collect(Collectors.joining(" "));
		
		var claims = JwtClaimsSet.builder()
				 .issuer("myBackend")
				  .subject(user.getId().toString())
				  .issuedAt(instant)
				  .expiresAt(instant.plusSeconds(expiresIn))
				  .claim("scope", scope)
		          .build();
		  
		  var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
		  
		return ResponseEntity.ok().body(new LoginResponse(jwtValue, expiresIn)); 
	}
	
	
	@PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserRequest Dto){
    	User user = new User();
    			user.setName(Dto.name());
    			user.setEmail(Dto.email());
    			user.setPassword(Dto.password());
    			
    		var obj = userService.update(id, user );
    		
    	return ResponseEntity.ok().body(new UserResponse(obj.getId(), obj.getName(), obj.getEmail(), obj.getExpenses()));
    }

	@PreAuthorize("hasRole ('ADMIN')")
   @DeleteMapping(value = "/{id}") 
   public ResponseEntity<Void> delete(@PathVariable Long id){
	   userService.Delete(id);
	   return ResponseEntity.noContent().build();
   }

}
