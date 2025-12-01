package websiters.review.mapper;

import websiters.review.dto.*;
import websiters.review.model.*;

import java.time.OffsetDateTime;
import java.util.Optional;

public final class Mappers {

    private Mappers() {}

    // ---------- REVIEW ----------
    public static Review toEntity(ReviewRequest dto) {
        return Review.builder()
                .userId(dto.getUserId())
                .restaurantId(dto.getRestaurantId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .hasAudio(Boolean.TRUE.equals(dto.getHasAudio()))
                .hasImage(Boolean.TRUE.equals(dto.getHasImage()))
                .build();
    }

    public static ReviewResponse toResponse(Review entity) {
        return ReviewResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .restaurantId(entity.getRestaurantId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .hasAudio(entity.isHasAudio())
                .hasImage(entity.isHasImage())
                .publishedAt(entity.getPublishedAt() != null
                        ? OffsetDateTime.from(entity.getPublishedAt())
                        : null)
                .build();
    }

    // ---------- REVIEW IMAGE ----------
    public static ReviewImage toEntity(ReviewImageRequest dto, Review review) {
        return ReviewImage.builder()
                .review(review)         // <--- CORRECTO: objeto Review, no UUID
                .url(dto.getUrl())
                .altText(dto.getAltText())
                .build();
    }

    public static ReviewImageResponse toResponse(ReviewImage entity) {
        return ReviewImageResponse.builder()
                .id(entity.getId())
                .reviewId(entity.getReview().getId())    // <--- obtener el ID del objeto
                .url(Optional.ofNullable(entity.getUrl()).orElse(""))
                .altText(Optional.ofNullable(entity.getAltText()).orElse(""))
                .build();
    }

    // ---------- REVIEW AUDIO ----------
    public static ReviewAudio toEntity(ReviewAudioRequest dto, Review review) {
        return ReviewAudio.builder()
                .reviewId(review.getId())
                .url(dto.getUrl())
                .durationSeconds(dto.getDurationSeconds())
                .transcription(dto.getTranscription())
                .build();
    }

    public static ReviewAudioResponse toResponse(ReviewAudio entity) {
        return ReviewAudioResponse.builder()
                .id(entity.getId())
                .reviewId(entity.getReviewId())
                .url(entity.getUrl())
                .durationSeconds(entity.getDurationSeconds())
                .transcription(entity.getTranscription())
                .build();
    }

    // ---------- REVIEW COMMENT ----------
    public static ReviewCommentResponse toResponse(ReviewComment entity) {
        return ReviewCommentResponse.builder()
                .id(entity.getId())
                .reviewId(entity.getReviewId())
                .authorId(entity.getAuthorId())
                .parentId(entity.getParentId())
                .content(entity.getContent())
                .publishedAt(entity.getPublishedAt() != null
                        ? OffsetDateTime.ofInstant(entity.getPublishedAt(), java.time.ZoneOffset.UTC)
                        : null)
                .build();
    }

    public static ReviewCommentAnalysisResponse toResponse(ReviewCommentAnalysis entity) {
        return ReviewCommentAnalysisResponse.builder()
                .id(entity.getId())
                .commentId(entity.getCommentId())
                .sentiment(entity.getSentiment())
                .positiveScore(entity.getPositiveScore())
                .neutralScore(entity.getNeutralScore())
                .negativeScore(entity.getNegativeScore())
                .keyPhrases(entity.getKeyPhrases())
                .analyzedAt(
                        entity.getAnalyzedAt() != null
                                ? OffsetDateTime.ofInstant(entity.getAnalyzedAt(), java.time.ZoneOffset.UTC)
                                : null
                )
                .build();
    }

}
