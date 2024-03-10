//
package project.graduation.dto.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.graduation.entity.User;

/**
 * This class is . 
 * 
 * @Description: .
 * @author: LNBao
 * @create_date: Jun 2, 2022
 * @version: 1.0
 * @modifier: LNBao
 * @modified_date: Jun 2, 2022
 */
@Getter
@Setter
@NoArgsConstructor
public class UserFilter {
	
	private User.Role role;
	
}
