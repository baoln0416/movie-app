//
package project.graduation.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Entity
@Table(name = "`movie`")
@Getter
@Setter
@NoArgsConstructor
public class Movie implements Serializable {

	//
	private static final long serialVersionUID = 1L;

	@Column(name = "`id`")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "`movie_name`", length = 255, nullable = false, unique = true)
	private String name;

	@Column(name = "`average_score`")
	private float score;
	
	@Column(name = "`number_of_vote`")
	private int numberOfVote;

	@Column(name = "`publish_year`")
	private String publishYear;

	@Column(name = "`duration`")
	private int duration;

	@ManyToOne
	@JoinColumn(name = "`director`")
	private Director director;

	@Column(name = "`rating`")
	private String rating;

	@Column(name = "`country`")
	private String country;

	@Column(name = "`tag`")
	@Enumerated(EnumType.STRING)
	private Tag tag;

	@Column(name = "`poster`")
	private String poster;

	@Column(name = "`big_poster`")
	private String bigPoster;

	@Column(name = "`trailer`")
	private String trailer;
	
	@Column(name = "`ranking`")
	private int ranking; 
	
	@Column(name = "`position`")
	private int position;
	
	@Column(name = "`budget`")
	private int budget;
	
	@Column(name = "`presentation`")
	private String presentation;
	
	@Column(name = "`description`")
	private String description;

	@OneToMany(mappedBy = "movie")
	private List<MovieActor> movieActors;

	@OneToMany(mappedBy = "movie")
	private List<MovieCategory> movieCategories;

	@OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
	private List<Review> reviews;
	
	@OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE)
	private List<UserMovie> userMovies;
	
	public enum Tag {
		HOT, NEW, RECOMMEND, NORMAL, HOTWEEK
	}
}
