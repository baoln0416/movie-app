package project.graduation.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`user_movie`")
@Data
@NoArgsConstructor
public class UserMovie implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`id`")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "`movie_id`")
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "`user_id`")
	private User user;
	
	@Column(name = "`time`")
	private Date recordTime;
}
