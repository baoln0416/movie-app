package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import project.graduation.entity.User;

@Data
@NoArgsConstructor
public class UserDTO {

	// check not null, check length, check exists, check format (regex)...
	private String userName;

	// check not null, check length, check exists on database, check format
	// (regex)...
	private String email;

	// check not null, check length, check format (regex)...
	private String password;

	// check not null, check length, check format (regex)...
	private String firstName;

	// check not null, check length, check format (regex)...
	private String lastName;
	
	private User.Role role;
	
}