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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "`User_Activities`")
@Data
@NoArgsConstructor
public class UserActivities implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`id`")
	private int id;

	@ManyToOne
	@JoinColumn(name = "`user_id`")
	@OnDelete(action = OnDeleteAction.CASCADE)
	private User user;

	@Column(name = "`movie_id`")
	private int movieId;

	@Column(name = "`time`")
	private Date recordTime;

	@Column(name = "`record`")
	private String record;
}
