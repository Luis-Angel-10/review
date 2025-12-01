package websiters.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websiters.review.model.ReviewImage;

import java.util.List;
import java.util.UUID;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, UUID> {

    List<ReviewImage> findByReviewId(UUID reviewId);

}
