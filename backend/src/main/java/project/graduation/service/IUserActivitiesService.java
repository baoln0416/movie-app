package project.graduation.service;

import java.util.List;

import project.graduation.dto.UserActivitiesDTO;
import project.graduation.dto.UserActivityCreate;
import project.graduation.entity.User;

public interface IUserActivitiesService {
	public List<UserActivitiesDTO> getActivitiesFromUser(User user);
	
	public void createActivity(UserActivityCreate activity, User user);
}
