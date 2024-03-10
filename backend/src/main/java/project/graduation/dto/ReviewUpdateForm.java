package project.graduation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewUpdateForm {

	private int newScore;

	private String newComment;
}
