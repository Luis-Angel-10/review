package websiters.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import websiters.review.dto.ReviewAudioRequest;
import websiters.review.dto.ReviewAudioResponse;
import websiters.review.mapper.Mappers;
import websiters.review.model.Review;
import websiters.review.model.ReviewAudio;
import websiters.review.repository.ReviewAudioRepository;
import websiters.review.repository.ReviewRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewAudioService {

    private final ReviewAudioRepository repo;
    private final ReviewRepository reviewRepo;

    @Transactional(readOnly = true)
    public Page<ReviewAudioResponse> list(Pageable pageable) {
        Pageable fixed = Pageable.ofSize(5).withPage(pageable.getPageNumber());
        return repo.findAll(fixed).map(Mappers::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ReviewAudioResponse> findByReview(UUID reviewId, Pageable pageable) {
        List<ReviewAudio> all = repo.findByReviewId(reviewId);
        return paginate(all, pageable);
    }

    @Transactional(readOnly = true)
    public ReviewAudioResponse get(UUID id) {
        ReviewAudio audio = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review audio not found"));
        return Mappers.toResponse(audio);
    }

    @Transactional
    public ReviewAudioResponse create(ReviewAudioRequest in) {

        Review review = reviewRepo.findById(in.getReviewId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review not found"));

        // 👈 ESTE ES EL MÉTODO CORRECTO
        ReviewAudio audio = Mappers.toEntity(in, review);

        repo.save(audio);
        return Mappers.toResponse(audio);
    }

    @Transactional
    public ReviewAudioResponse update(UUID id, ReviewAudioRequest in) {
        ReviewAudio audio = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review audio not found"));

        if (in.getUrl() != null) audio.setUrl(in.getUrl());
        if (in.getDurationSeconds() != null) audio.setDurationSeconds(in.getDurationSeconds());
        if (in.getTranscription() != null) audio.setTranscription(in.getTranscription());

        repo.save(audio);
        return Mappers.toResponse(audio);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review audio not found");
        }
        repo.deleteById(id);
    }


    private Page<ReviewAudioResponse> paginate(List<ReviewAudio> list, Pageable pageable) {
        int size = 5;
        int page = pageable.getPageNumber();
        int start = size * page;
        int end = Math.min(start + size, list.size());

        List<ReviewAudioResponse> content = list.subList(Math.min(start, end), end)
                .stream()
                .map(Mappers::toResponse)
                .toList();

        return new PageImpl<>(content, Pageable.ofSize(size).withPage(page), list.size());
    }
}
