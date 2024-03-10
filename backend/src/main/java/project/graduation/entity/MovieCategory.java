//
package project.graduation.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "`movie_category`")
@Getter
@Setter
@NoArgsConstructor
public class MovieCategory implements Serializable {
	
	//
	private static final long serialVersionUID = 1L;
	
	@Id
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "`movie_id`")
	private Movie movie;
	
	@ManyToOne
	@JoinColumn(name = "`category_id`")
	private Category category;
}
