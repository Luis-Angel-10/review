package websiters.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import websiters.review.model.ReviewCommentAnalysis;

import java.util.Optional;
import java.util.UUID;

public interface ReviewCommentAnalysisRepository extends JpaRepository<ReviewCommentAnalysis, UUID> {
    Optional<ReviewCommentAnalysis> findByComment_Id(UUID commentId);
}
