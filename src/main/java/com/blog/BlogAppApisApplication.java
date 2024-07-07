package com.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstants;
import com.blog.model.Roles;
import com.blog.repository.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner{

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private RoleRepo roleRepo;
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
	//	System.out.println(this.encoder.encode("rocky@12"));
		try {
			Roles roles1 = new Roles();
			roles1.setRole_Id(AppConstants.ADMIN_USER);
			roles1.setRole_Name("ADMIN_USER");
			
			Roles roles2 = new Roles();
			roles2.setRole_Id(AppConstants.NORMAL_USER);
			roles2.setRole_Name("NORMAL_USER");
			
			List<Roles> roles = List.of(roles1,roles2);
			
			List<Roles> result = this.roleRepo.saveAll(roles);
			result.forEach(r->{
				System.out.println(r.getRole_Name());
			});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
