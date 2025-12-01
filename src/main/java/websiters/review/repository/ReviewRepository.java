package websiters.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websiters.review.model.Review;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findByRestaurantId(UUID restaurantId);

    List<Review> findByUserId(UUID userId);

    List<Review> findByHasImageTrue();

    List<Review> findByHasAudioTrue();

    List<Review> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String t1, String t2);
}
