package project.graduation.service;

import java.util.List;

import org.springframework.stereotype.Component;

import project.graduation.dto.MessengerFormCreate;
import project.graduation.entity.Messenger;

@Component
public interface IMessengerService {
	public List<Integer> FindAllUserId();

	public List<Messenger> getListMessengerByUserId(int id);

	List<Messenger> getListMessengerByUserName(String userName);

	void createMessenger(MessengerFormCreate form);

}
