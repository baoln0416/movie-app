//
package project.graduation.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is . 
 * 
 * @Description: .
 * @author: LNBao
 * @create_date: May 27, 2022
 * @version: 1.0
 * @modifier: LNBao
 * @modified_date: May 27, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class MovieDTO {
	private int id;
	
	private String name;
	
	private int nameLength;
	
	private float score;
	
	private String publishYear;
	
	private int duration;
	
	private String rating;
	
	private String director;
	
	private List<String> actors;
	
	private String country;
	
	private String tag;
	
	private String poster;
	
	private String bigPoster;
	
	private String trailer;
	
	private int ranking;
	
	private int position;
	
	private int budget;
	
	private String presentation;
	
	private String description;
	
	private List<String> categories;
	
	private List<ReviewDTO> reviews;
	
	private int numberOfReviews;

}
