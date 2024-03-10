package project.graduation.entity;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "`user_review`")
@Getter
@Setter
@NoArgsConstructor
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "`review_id`")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int reviewId;

	@Column(name = "`comment`")
	private String comment;

	@ManyToOne
	@JoinColumn(name = "`movie_id`")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Movie movie;

	@ManyToOne
	@JoinColumn(name = "`user_id`")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(name = "`score`")
	private int score;

	@Column(name = "`number_of_like`")
	private int numberOfLike;

	@Column(name = "`upload_date`")
	private Date updateDate;
	
	@OneToMany(mappedBy = "review")
	private List<LikeReview> likes;

	public Review(String comment, Movie movie, User user, int score, Date updateDate) {
		super();
		this.comment = comment;
		this.movie = movie;
		this.user = user;
		this.score = score;
		this.updateDate = updateDate;
	}

}
