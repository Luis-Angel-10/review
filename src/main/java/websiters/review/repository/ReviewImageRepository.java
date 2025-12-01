package websiters.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import websiters.review.model.ReviewImage;

import java.util.UUID;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, UUID> {

    Page<ReviewImage> findByReview_Id(UUID reviewId, Pageable pageable);
}
