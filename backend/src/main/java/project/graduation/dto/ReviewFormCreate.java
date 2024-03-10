package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewFormCreate {

	private int movieId;

	private String userName;

	private int score;

	private String comment;

}
