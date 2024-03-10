package project.graduation.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import project.graduation.dto.ChangePublicProfileDTO;
import project.graduation.dto.ChangeUserPublicInfoDTO;
import project.graduation.dto.filter.UserFilter;
import project.graduation.entity.User;
import project.graduation.entity.User.Role;

@Component
public interface IUserService extends UserDetailsService {
	Page<User> getAllUsers(Pageable pageable, UserFilter filter, String search);

	void changeUserRole(String username, User.Role role);

	List<Role> getAllRoles();

	void createUser(User user);

	User findUserByEmail(String email);

	User findUserByUserName(String username);

	public User findByFacebookID(String facebookID);

	void activeUser(String token);

	void sendConfirmUserRegistrationViaEmail(String email);

	boolean existsUserByEmail(String email);

	boolean existsUserByUserName(String userName);

	void resetPasswordViaEmail(String email);

	void resetPassword(String token, String newPassword);

	void sendResetPasswordViaEmail(String email);

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	void sendResetImportantInfoViaEmail(String email);

	void changeUserProfile(String username, ChangePublicProfileDTO dto) throws Exception;

	User getUserById(int id);

	void deleteUsers(List<Integer> ids);

	public void changeUserPublicInfo(String userName, ChangeUserPublicInfoDTO dto);
	
	public String sendOTPViaEmail(String email);
	
}
