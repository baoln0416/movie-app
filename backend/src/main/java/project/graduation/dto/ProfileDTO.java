package project.graduation.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProfileDTO {

	private String userName;

	private String email;

	private String firstName;

	private String lastName;

	private String role;

	private String status;

	private String avatarUrl;

	private String liveIn;

	private String country;

	private String phoneNumber;

	private int activeDay;

	private String activeMonth;

	private int activeYear;

	private int presentDay;

	private String presentMonth;

	private int presentYear;

	private int activeTime;
	
	private List<Integer> likes;

	@SuppressWarnings("deprecation")
	public void setActiveDay(Date activeDate) {
		this.activeDay = activeDate.getDate();
	}

	@SuppressWarnings("deprecation")
	public void setActiveMonth(Date activeDate) {
		switch (activeDate.getMonth() + 1) {
		case 1:
			this.activeMonth = "Jan";
			break;
		case 2:
			this.activeMonth = "Feb";
			break;
		case 3:
			this.activeMonth = "Mar";
			break;
		case 4:
			this.activeMonth = "Apr";
			break;
		case 5:
			this.activeMonth = "May";
			break;
		case 6:
			this.activeMonth = "Jun";
			break;
		case 7:
			this.activeMonth = "Jul";
			break;
		case 8:
			this.activeMonth = "Aug";
			break;
		case 9:
			this.activeMonth = "Sep";
			break;
		case 10:
			this.activeMonth = "Oct";
			break;
		case 11:
			this.activeMonth = "Nov";
			break;
		case 12:
			this.activeMonth = "Dec";
			break;
		default:
			this.activeMonth = "";
			break;
		}
	}

	@SuppressWarnings("deprecation")
	public void setActiveYear(Date activeDate) {
		this.activeYear = activeDate.getYear() + 1900;
	}

	@SuppressWarnings("deprecation")
	public void setPresentDay(java.util.Date presentDate) {
		this.presentDay = presentDate.getDate();
	}

	@SuppressWarnings("deprecation")
	public void setPresentMonth(java.util.Date presentDate) {
		switch (presentDate.getMonth() + 1) {
		case 1:
			this.presentMonth = "Jan";
			break;
		case 2:
			this.presentMonth = "Feb";
			break;
		case 3:
			this.presentMonth = "Mar";
			break;
		case 4:
			this.presentMonth = "Apr";
			break;
		case 5:
			this.presentMonth = "May";
			break;
		case 6:
			this.presentMonth = "Jun";
			break;
		case 7:
			this.presentMonth = "Jul";
			break;
		case 8:
			this.presentMonth = "Aug";
			break;
		case 9:
			this.presentMonth = "Sep";
			break;
		case 10:
			this.presentMonth = "Oct";
			break;
		case 11:
			this.presentMonth = "Nov";
			break;
		case 12:
			this.presentMonth = "Dec";
			break;
		default:
			this.presentMonth = "";
			break;
		}
	}

	@SuppressWarnings("deprecation")
	public void setPresentYear(java.util.Date presentDate) {
		this.presentYear = presentDate.getYear() + 1900;
	}

	public void setPhoneNumber(String phoneNumber, String country) {
		if (phoneNumber.contains("+")) {
			this.phoneNumber = phoneNumber;
		} else {
			switch (country) {
			case ("Vietnam"):
				this.phoneNumber = "+84 " + phoneNumber;
				break;
			case ("Japan"):
				this.phoneNumber = "+81 " + phoneNumber;
				break;
			case ("USA"):
				this.phoneNumber = "+1 " + phoneNumber;
				break;
			default:
				this.phoneNumber = phoneNumber;
			}
		}
		
	}

	public ProfileDTO(String userName, String email, String firstName, String lastName, String role, String status,
			String avatarUrl, String liveIn, String country) {
		this.userName = userName;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.status = status;
		this.avatarUrl = avatarUrl;
		this.liveIn = liveIn;
		this.country = country;
	}

}
