package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserActivitiesDTO {
	private int userId;
	private String userName;
	private int movieId;
	private String movieName;
	private String recordDate;
	private String recordTime;
	private String recordDay;
	private String record;

	public void setRecordDay(int day) {
		switch (day) {
		case 0:
			this.recordDay = "Sunday";
			break;
		case 1:
			this.recordDay = "Monday";
			break;
		case 2:
			this.recordDay = "Tuesday";
			break;
		case 3:
			this.recordDay = "Wednesday";
			break;
		case 4:
			this.recordDay = "Thursday";
			break;
		case 5:
			this.recordDay = "Friday";
			break;
		case 6:
			this.recordDay = "Saturday";
			break;
		default:
			this.recordDay = "null";
			break;
		}
	}
}
