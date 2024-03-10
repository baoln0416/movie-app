package project.graduation.dto;

import project.graduation.entity.User;

public class ChangePublicProfileDTO {

	// TODO validate
	private String avatarUrl;

	private String biography;
	
	private String userName;
	
	private String lastName;
	
	private String firstName;
	
	private String email;
	
	private String confirmOTP;
	
	private User.Role role;

	public ChangePublicProfileDTO() {
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmOTP() {
		return confirmOTP;
	}

	public void setConfirmOTP(String confirmOTP) {
		this.confirmOTP = confirmOTP;
	}
	
	public User.Role getRole() {
		return role;
	}

	public void setRole(User.Role role) {
		this.role = role;
	}
	

}
