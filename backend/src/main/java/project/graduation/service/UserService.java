package project.graduation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import project.graduation.dto.ChangePublicProfileDTO;
import project.graduation.dto.ChangeUserPublicInfoDTO;
import project.graduation.dto.filter.UserFilter;
import project.graduation.entity.RegistrationUserToken;
import project.graduation.entity.ResetPasswordToken;
import project.graduation.entity.User;
import project.graduation.entity.User.Role;
import project.graduation.entity.UserActivities;
import project.graduation.event.OnResetImportantInfoViaEmailEvent;
import project.graduation.event.OnResetPasswordViaEmailEvent;
import project.graduation.event.OnSendRegistrationUserConfirmViaEmailEvent;
import project.graduation.repository.RegistrationUserTokenRepository;
import project.graduation.repository.ResetPasswordTokenRepository;
import project.graduation.repository.UserActivitiesRepository;
import project.graduation.repository.UserRepository;
import project.graduation.specification.UserSpecificationBuilder;

@Service
@Transactional
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RegistrationUserTokenRepository registrationUserTokenRepository;

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserActivitiesRepository activitiesRepository;

	// get all
	public Page<User> getAllUsers(Pageable pageable, UserFilter filter, String search) {

		UserSpecificationBuilder specification = new UserSpecificationBuilder(search, filter);

		return userRepository.findAll(specification.build(), pageable);
	}

	// get role
	public List<Role> getAllRoles() {
		List<Role> roles = new ArrayList<>();
		for (Role role : Role.values()) {
			roles.add(role);
		}
		return roles;
	}

	@Override
	public void createUser(User user) {
		if (user.getFacebookID() != null) {
			int i = 0;
			String userName = user.getUserName();
			boolean isExists = true;
			while (isExists == true) {
				isExists = userRepository.existsByUserName(userName);
				userName = user.getUserName() + i;
				i++;
			}
			user.setUserName(userName);
		}
		// encode password
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		// create user
		user.setRole(Role.Member);
		user.setActiveDate(new Date());
		user.setLiveIn("undefined");
		user.setCountry("undefined");
		user.setPhoneNumber("undefined");
		userRepository.save(user);

		// create new user registration token
		createNewRegistrationUserToken(user);

		// send email to confirm
		sendConfirmUserRegistrationViaEmail(user.getEmail());

		// save log activity
		UserActivities userActivity = new UserActivities();
		userActivity.setUser(user);
		userActivity.setMovieId(0);
		userActivity.setRecord("Your account has been created.");
		userActivity.setRecordTime(new Date());

		activitiesRepository.save(userActivity);
	}

	public User findByFacebookID(String facebookID) {
		return userRepository.findByFacebookID(facebookID);
	}

	private void createNewRegistrationUserToken(User user) {

		// create new token for confirm Registration
		final String newToken = UUID.randomUUID().toString();
		RegistrationUserToken token = new RegistrationUserToken(newToken, user);

		registrationUserTokenRepository.save(token);
	}

	@Override
	public void sendConfirmUserRegistrationViaEmail(String email) {
		eventPublisher.publishEvent(new OnSendRegistrationUserConfirmViaEmailEvent(email));
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findUserByUserName(String username) {
		return userRepository.findByUserName(username);
	}

	@Override
	public boolean existsUserByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsUserByUserName(String userName) {
		return userRepository.existsByUserName(userName);
	}

	@Override
	public void activeUser(String token) {

		// get token
		RegistrationUserToken registrationUserToken = registrationUserTokenRepository.findByToken(token);

		// active user
		User user = registrationUserToken.getUser();
		user.setStatus(User.UserStatus.ACTIVE);
		userRepository.save(user);

		// remove Registration User Token
		registrationUserTokenRepository.deleteById(registrationUserToken.getId());
	}

	@Override
	public void resetPasswordViaEmail(String email) {

		// find user by email
		User user = findUserByEmail(email);

		// remove token token if exists
		resetPasswordTokenRepository.deleteByUserId(user.getId());

		// create new reset password token
		createNewResetPasswordToken(user);

		// send email
		sendResetPasswordViaEmail(email);
	}

	@Override
	public void sendResetPasswordViaEmail(String email) {
		eventPublisher.publishEvent(new OnResetPasswordViaEmailEvent(email));
	}

	private void createNewResetPasswordToken(User user) {

		// create new token for Reseting password
		final String newToken = UUID.randomUUID().toString();
		ResetPasswordToken token = new ResetPasswordToken(newToken, user);

		resetPasswordTokenRepository.save(token);
	}

	@Override
	public void resetPassword(String token, String newPassword) {
		// get token
		ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

		// change password
		User user = resetPasswordToken.getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);

		// remove Reset Password
		resetPasswordTokenRepository.deleteById(resetPasswordToken.getId());
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Check user exists by username
		User user = userRepository.findByUserName(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				AuthorityUtils.createAuthorityList(user.getRole().toString()));
	}

	public void changeUserRole(String username, User.Role role) {
		User user = userRepository.findByUserName(username);
		user.setRole(role);
		userRepository.save(user);

		// TODO other field
	}

	@Override
	public void sendResetImportantInfoViaEmail(String email) {
		eventPublisher.publishEvent(new OnResetImportantInfoViaEmailEvent(email));
	}

	@Override
	public void changeUserProfile(String username, ChangePublicProfileDTO dto) throws Exception {
		User user = userRepository.findByUserName(username);

		// để tránh trường hợp ngừoi dùng k dùng website mà dùng postman chẳng hạn thì
		// lúc đó OTP ở database vẫn là null
		// nên nếu k xét trường hợp OTP nhận vào là null thì trong trường hợp đó để
		// trống phần otp vẫn có thể thay đổi đk
		// email và userName
		if (dto.getConfirmOTP() == null) {
			if (!user.getEmail().equals(dto.getEmail()) || !user.getUserName().equals(dto.getUserName())) {
				user.setChangeInfoOTP(null);
				throw new Exception(" failed confirm OTP 1 ");
			} else {
				user.setAvatarUrl(dto.getAvatarUrl());
				user.setFirstName(dto.getFirstName());
				user.setLastName(dto.getLastName());
				user.setChangeInfoOTP(null);
				userRepository.save(user);

			}
			;
		} else {
			if (user.getChangeInfoOTP().equals(dto.getConfirmOTP())) {
				user.setUserName(dto.getUserName());
				user.setChangeInfoOTP(null);
				user.setAvatarUrl(dto.getAvatarUrl());
				user.setFirstName(dto.getFirstName());
				user.setLastName(dto.getLastName());
				userRepository.save(user);

			} else {
				user.setChangeInfoOTP(null);
				throw new Exception(" failed confirm OTP 2 ");
			}

		}

	}

	public User getUserById(int id) {
		return userRepository.findById(id).get();
	}

	@Transactional
	public void deleteUsers(List<Integer> ids) {
		userRepository.deleteByIdIn(ids);
	}

	public void changeUserPublicInfo(String userName, ChangeUserPublicInfoDTO dto) {
		User user = userRepository.findByUserName(userName);

		user.setAvatarUrl(dto.getAvatarUrl());
		user.setFirstName(dto.getFirstName());
		user.setLastName(dto.getLastName());
		user.setCountry(dto.getCountry());
		user.setLiveIn(dto.getLiveIn());
		user.setPhoneNumber(dto.getPhoneNumber());

		userRepository.save(user);
	}

	// ===== Adding 03/10/22 ===== //
	public String sendOTPViaEmail(String email) {
		// find user by email
		User user = findUserByEmail(email);

		// remove token token if exists
		resetPasswordTokenRepository.deleteByUserId(user.getId());

		// create new reset password OTP
		String newOTP = createNewOTP(user);

		// send email
		sendResetPasswordOTPViaEmail(email);
		
		return newOTP;
	}
	
	public void sendResetPasswordOTPViaEmail(String email) {
		eventPublisher.publishEvent(new OnResetImportantInfoViaEmailEvent(email));
	}

	public String createNewOTP(User user) {
		// create new OTP for Reseting password
		Random generator = new Random();
		Integer randInt = generator.nextInt((999999 - 100000) + 1) + 100000;
		final String newOTP = randInt.toString();
		ResetPasswordToken token = new ResetPasswordToken(newOTP, user);

		resetPasswordTokenRepository.save(token);
		
		return newOTP;
	}
}
