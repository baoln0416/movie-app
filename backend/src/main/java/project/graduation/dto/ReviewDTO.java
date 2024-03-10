package project.graduation.dto;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDTO {

	private int reviewId;

	private int movieID;

	private String userName;
	
	private String userRole;

	private float score;
	
	private int numberOfLike;

	private String comment;

	private int uploadDay;

	private String uploadMonth;

	private int uploadYear;

	private String userAvatar;
	
	private List<String> likeByUsers;

	@SuppressWarnings("deprecation")
	public void setUploadDay(Date uploadDate) {
		this.uploadDay = uploadDate.getDate();
	}

	@SuppressWarnings("deprecation")
	public void setUploadMonth(Date uploadDate) {
		switch (uploadDate.getMonth() + 1) {
		case 1:
			this.uploadMonth = "Jan";
			break;
		case 2:
			this.uploadMonth = "Feb";
			break;
		case 3:
			this.uploadMonth = "Mar";
			break;
		case 4:
			this.uploadMonth = "Apr";
			break;
		case 5:
			this.uploadMonth = "May";
			break;
		case 6:
			this.uploadMonth = "Jun";
			break;
		case 7:
			this.uploadMonth = "Jul";
			break;
		case 8:
			this.uploadMonth = "Aug";
			break;
		case 9:
			this.uploadMonth = "Sep";
			break;
		case 10:
			this.uploadMonth = "Oct";
			break;
		case 11:
			this.uploadMonth = "Nov";
			break;
		case 12:
			this.uploadMonth = "Dec";
			break;
		default:
			this.uploadMonth = "";
			break;
		}
	}

	@SuppressWarnings("deprecation")
	public void setUploadYear(Date uploadDate) {
		this.uploadYear = uploadDate.getYear() + 1900;
	}
}
