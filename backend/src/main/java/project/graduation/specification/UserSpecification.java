package project.graduation.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import project.graduation.entity.User;

public class UserSpecification implements Specification<User> {

	private static final long serialVersionUID = 1L;
	private UserSearchCriteria criteria;

	public UserSpecification(UserSearchCriteria criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		if (criteria.getOperator().equalsIgnoreCase("Like")) {
			return criteriaBuilder.like(root.get(criteria.getKey()), "%" + criteria.getValue().toString() + "%");
		}
		
		if (criteria.getOperator().equalsIgnoreCase("Equal")) {
			return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
		}
		
		if (criteria.getOperator().equalsIgnoreCase("NotEqual")) {
			return criteriaBuilder.notEqual(root.get(criteria.getKey()), criteria.getValue());
		}
		
		return null;
	}

}
