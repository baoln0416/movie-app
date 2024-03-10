package project.graduation.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`User`")
@Data
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`id`", unique = true, nullable = false)
	private int id;

	@Column(name = "`username`", nullable = false, length = 50, unique = true)
	private String userName;

	@Column(name = "`email`", nullable = false, length = 50, unique = true)
	private String email;

	@Column(name = "`password`", nullable = false, length = 800)
	private String password;

	@Column(name = "`firstName`", nullable = false, length = 50)
	private String firstName;

	@Column(name = "`lastName`", nullable = false, length = 50)
	private String lastName;

	@Column(name = "`changeInfoOTP`")
	private String changeInfoOTP;

	@Column(name = "`facebookID`")
	private String facebookID;

	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Review> comments;

	public String getFacebookID() {
		return facebookID;
	}

	public void setFacebookID(String facebookID) {
		this.facebookID = facebookID;
	}

	@Formula("concat(firstName, ' ', lastName)")
	private String fullName;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "`status`", nullable = false)
	private UserStatus status = UserStatus.NOT_ACTIVE;

	@Column(name = "avatarUrl")
	private String avatarUrl;
	
	@Column(name = "`live_in`")
	private String liveIn;
	
	@Column(name = "`country`")
	private String country;
	
	@Column(name = "`phone_number`")
	private String phoneNumber;
	
	@Column(name = "`active_date`")
	private Date activeDate;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<UserActivities> userActivities;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<UserMovie> userMovies;
	
	@OneToMany(mappedBy = "user")
	private List<LikeReview> likes;

	public User(String userName, String email, String password, String firstName, String lastName, String facebookID) {
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.facebookID = facebookID;
	}

	public enum Role {
		Admin, Member, Critic
	}

	public enum UserStatus {
		NOT_ACTIVE, ACTIVE;
	}
}
