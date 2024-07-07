package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blog.model.Roles;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {

	private int uid;
	@NotEmpty
	@Size(min = 4, message = "user name must contain 4 characters")
	private String uname;
	@Email(message = "email ID not valid !")
	private String uemail;
	@NotEmpty()
	@Size(min = 8, max = 15, message = "password must contain 8 characters")
	private String upassword;
	@NotNull
	private String uabout;
	
	private Set<RoleDto> roles = new HashSet<>();
	
	
}
