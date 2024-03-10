//
package project.graduation.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "`actor`")
@Getter
@Setter
@NoArgsConstructor
public class Actor implements Serializable {

	//
	private static final long serialVersionUID = 1L;
	
	@Column(name = "`id`")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "`actor_name`", length = 255, nullable = false, unique = true)
	private String name;
	
	@Column(name = "`birth_day`", length = 255)
	private String birthDay;
	
	@Column(name = "`birth_place`", length = 255)
	private String birthPlace;
	
	@OneToMany(mappedBy = "actor")
	private List<MovieActor> movieActors;
}
