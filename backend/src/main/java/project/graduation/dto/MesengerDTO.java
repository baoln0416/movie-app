package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MesengerDTO {

	private int id;

	private String message;

	private String senderName;
	
	private String userAvatar;
}
