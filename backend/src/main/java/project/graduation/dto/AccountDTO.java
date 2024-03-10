//
package project.graduation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.graduation.entity.User;

/**
 * This class is . 
 * 
 * @Description: .
 * @author: LNBao
 * @create_date: Jun 9, 2022
 * @version: 1.0
 * @modifier: LNBao
 * @modified_date: Jun 9, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
	
	int id;
	
	String userName;
	
	User.Role role;
	
	int numberOfReview;
	
	int numberOfLike;
}
