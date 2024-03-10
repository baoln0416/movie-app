package project.graduation.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.graduation.dto.MessengerFormCreate;
import project.graduation.entity.Messenger;
import project.graduation.entity.Messenger.ChatboxStatus;
import project.graduation.entity.User;
import project.graduation.repository.IMessengerRepository;

@Service
public class MessengerService implements IMessengerService {

	@Autowired(required = true)
	private IUserService userService;

	@Autowired
	private IMessengerRepository repository;

	public List<Integer> FindAllUserId() {
		return repository.FindAllUserId();
	}

	public List<Messenger> getListMessengerByUserName(String userName) {
		User user = userService.findUserByUserName(userName);
		return repository.findByUserId(user.getId());
	}

	public List<Messenger> getListMessengerByUserId(int id) {
		return repository.findByUserId(id);
	}

	public void createMessenger(MessengerFormCreate form) {
		Messenger mess = new Messenger();
		User user = userService.findUserByUserName(form.getUserName());
		if (form.getChatboxStatus().equals(ChatboxStatus.OFF.toString())) {
			mess = new Messenger(user, ChatboxStatus.OFF, form.getInboxContent());
		} else {
			mess = new Messenger(user, ChatboxStatus.ON, form.getInboxContent());
		}
		repository.save(mess);

	}
}
