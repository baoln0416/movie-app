package project.graduation.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import project.graduation.dto.filter.UserFilter;
import project.graduation.entity.User;

public class UserSpecificationBuilder {

	private String search;
	
	private UserFilter filter;

	public UserSpecificationBuilder(String search, UserFilter filter) {
		this.search = search;
		this.filter = filter;
	}

	@SuppressWarnings("deprecation")
	public Specification<User> build() {
		
		UserSearchCriteria defaultCriteria = new UserSearchCriteria("role", "NotEqual", User.Role.Admin);
		
		UserSearchCriteria defaultUserNameCriteria = new UserSearchCriteria("userName", "NotEqual", "Unregister");

		UserSearchCriteria seachCriteria = new UserSearchCriteria("userName", "Like", search);
		
		UserSearchCriteria roleSeachCriteria = new UserSearchCriteria("role", "Equal", filter.getRole());

		Specification<User> where = new UserSpecification(defaultCriteria);
		
		where = where.and(new UserSpecification(defaultUserNameCriteria));

		// search
		if (!StringUtils.isEmpty(search)) {
			where = where.and(new UserSpecification(seachCriteria));
		}
		
		// filter
		if (filter.getRole() != null) {
			where = where.and(new UserSpecification(roleSeachCriteria));
		}

		return where;
	}
}
