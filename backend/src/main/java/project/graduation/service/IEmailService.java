package project.graduation.service;

public interface IEmailService {

	void sendRegistrationUserConfirm(String email);

	void sendResetPassword(String email);
	
	void sendResetPrivateInfo(String email);

}
