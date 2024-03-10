package project.graduation.service;

//import javax.xml.bind.helpers.ParseConversionEventImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import project.graduation.entity.User;
import project.graduation.repository.RegistrationUserTokenRepository;
import project.graduation.repository.ResetPasswordTokenRepository;

@Component
public class EmailService implements IEmailService {

	@Autowired
	private IUserService userService;

	@Autowired
	private RegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendRegistrationUserConfirm(String email) {

		User user = userService.findUserByEmail(email);
		String token = registrationUserTokenRepository.findByUserId(user.getId());

		String confirmationUrl = "http://localhost:8080/api/v1/users/activeUser?token=" + token;

		String subject = "Xác Nhận Đăng Ký Account";
		String content = "Bạn đã đăng ký thành công. Click vào link dưới đây để kích hoạt tài khoản \n"
				+ confirmationUrl;

		sendEmail(email, subject, content);
	}

	@Override
	public void sendResetPassword(String email) {

		User user = userService.findUserByEmail(email);
		String token = resetPasswordTokenRepository.findByUserId(user.getId());

		String confirmationUrl = "https://localhost:3000/auth/new-password/" + token;

		String subject = "Thiết lập lại mật khẩu";
		String content = "Click vào link dưới đây để thiết lập lại mật khẩu (nếu không phải bạn xin vui lòng bỏ qua).\n"
				+ confirmationUrl;

		sendEmail(email, subject, content);
	}

	@Override
	public void sendResetPrivateInfo(String email) {

		User user = userService.findUserByEmail(email);

		String confirmOTP = resetPasswordTokenRepository.findByUserId(user.getId());

//		user.setChangeInfoOTP(confirmOTP + "");

		String subject = "Thiết lập lại Username hoặc Email";
		String content = "mã OTP của bạn là (nếu không phải bạn xin vui lòng bỏ qua).\n" + confirmOTP;

		sendEmail(email, subject, content);
	}

	private void sendEmail(final String recipientEmail, final String subject, final String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(recipientEmail);
		message.setSubject(subject);
		message.setText(content);

		mailSender.send(message);
	}

}
