package com.Gastos.controll.securityConfig;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.Gastos.controll.entities.Role;
import com.Gastos.controll.entities.User;
import com.Gastos.controll.repository.RoleRepository;
import com.Gastos.controll.repository.UserRepository;

@Configuration
public class AdminSecurityConfig implements CommandLineRunner{

	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Value("${Email.Admin}")
	private String email;
	
	@Value("${Password.Admin}")
	private String password;
	
	public AdminSecurityConfig(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
			RoleRepository roleRepository) {
		
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}



	@Override
	public void run(String... args) throws Exception {
	
		  if (roleRepository.findByName(Role.Values.ADMIN.name()) == null) {
		        var newRole = new Role(null, Role.Values.ADMIN.name());
		         roleRepository.save(newRole);
		    }

		    var roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
		    
		    var userAdmin = userRepository.findByEmail(email);
		    
		    if(userAdmin.isEmpty()) {
		    	 User admin = new User();
		    	 admin.setName("Admin");
		    	 admin.setEmail(email);
		    	 admin.setPassword(passwordEncoder.encode(password));
		    	 admin.setRoles(Set.of(roleAdmin));
		    	 
		    	 userRepository.save(admin);
	        }
			else{
			  System.out.println("Ja existe!");
			}
		    
		    
	}

}
