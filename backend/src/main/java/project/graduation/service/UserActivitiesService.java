package project.graduation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.graduation.dto.UserActivitiesDTO;
import project.graduation.dto.UserActivityCreate;
import project.graduation.entity.Movie;
import project.graduation.entity.User;
import project.graduation.entity.UserActivities;
import project.graduation.repository.IMovieRepository;
import project.graduation.repository.UserActivitiesRepository;

@Service
public class UserActivitiesService implements IUserActivitiesService {

	@Autowired
	private UserActivitiesRepository repository;

	@Autowired
	private IMovieRepository movieRepository;
	
	@SuppressWarnings("deprecation")
	@Override
	public List<UserActivitiesDTO> getActivitiesFromUser(User user) {
		// TODO Auto-generated method stub
		List<UserActivities> userActs = repository.getActivitiesFromUserId(user.getId());
		List<UserActivitiesDTO> dtos = new ArrayList<UserActivitiesDTO>();
		String datePattern = "dd-MM-yyyy";
		String timePattern = "HH:mm";
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat(datePattern);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(timePattern);

		for (UserActivities userAct : userActs) {
			UserActivitiesDTO dto = new UserActivitiesDTO();

			if (userAct.getMovieId() != 0) {
				Movie movie = movieRepository.findById(userAct.getMovieId()).get();
				dto.setMovieId(movie.getId());
				dto.setMovieName(movie.getName());
			}

			dto.setUserId(user.getId());
			dto.setUserName(user.getUserName());
			dto.setRecordDate(simpleDateFormat1.format(userAct.getRecordTime()));
			dto.setRecordTime(simpleDateFormat2.format(userAct.getRecordTime()));
			dto.setRecordDay(userAct.getRecordTime().getDay());
			dto.setRecord(userAct.getRecord());

			dtos.add(dto);
		}

		return dtos;
	}

	public void createActivity(UserActivityCreate activity, User user) {
		UserActivities userActivity = new UserActivities();
		userActivity.setUser(user);
		
		Date date = new Date();
		
		if (activity.getMovieId() != 0) {
			userActivity.setMovieId(activity.getMovieId());
		} else {
			userActivity.setMovieId(0);
		}
		
		userActivity.setRecord(activity.getRecord());
		userActivity.setRecordTime(date);
		
		repository.save(userActivity);
	}
}
