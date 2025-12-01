package websiters.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websiters.review.model.ReviewComment;

import java.util.List;
import java.util.UUID;

public interface ReviewCommentRepository extends JpaRepository<ReviewComment, UUID> {

    List<ReviewComment> findByReviewId(UUID reviewId);

    List<ReviewComment> findByAuthorId(UUID authorId);

    List<ReviewComment> findByParentId(UUID parentId);

}
