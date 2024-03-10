package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeUserPublicInfoDTO {
	private String avatarUrl;
	private String firstName;
	private String lastName;
	private String country;
	private String liveIn;
	private String phoneNumber;
}
