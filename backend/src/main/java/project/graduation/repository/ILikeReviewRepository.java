package project.graduation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import project.graduation.entity.LikeReview;

public interface ILikeReviewRepository extends JpaRepository<LikeReview, Integer>, JpaSpecificationExecutor<LikeReview> {

}
