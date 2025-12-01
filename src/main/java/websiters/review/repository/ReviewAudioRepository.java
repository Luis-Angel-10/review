package websiters.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websiters.review.model.ReviewAudio;

import java.util.List;
import java.util.UUID;

public interface ReviewAudioRepository extends JpaRepository<ReviewAudio, UUID> {

    List<ReviewAudio> findByReviewId(UUID reviewId);
}
