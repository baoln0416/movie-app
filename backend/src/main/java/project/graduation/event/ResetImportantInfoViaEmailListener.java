package project.graduation.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import project.graduation.service.IEmailService;

@Component
public class ResetImportantInfoViaEmailListener implements ApplicationListener<OnResetImportantInfoViaEmailEvent> {

	@Autowired
	private IEmailService emailService;

	@Override
	public void onApplicationEvent(final OnResetImportantInfoViaEmailEvent event) {
		sendResetImportantInfoViaEmail(event.getEmail());
	}

	private void sendResetImportantInfoViaEmail(String email) {
		emailService.sendResetPrivateInfo(email);
	}

}
