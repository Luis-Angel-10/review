package websiters.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import websiters.review.dto.ReviewImageRequest;
import websiters.review.dto.ReviewImageResponse;
import websiters.review.mapper.Mappers;
import websiters.review.model.Review;
import websiters.review.model.ReviewImage;
import websiters.review.repository.ReviewImageRepository;
import websiters.review.repository.ReviewRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewImageService {

    private final ReviewImageRepository repo;
    private final ReviewRepository reviewRepo;

    @Transactional(readOnly = true)
    public Page<ReviewImageResponse> list(Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                5,
                pageable.getSort()
        );

        return repo.findAll(pageable).map(Mappers::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ReviewImageResponse> findByReview(UUID reviewId, Pageable pageable) {

        pageable = PageRequest.of(
                pageable.getPageNumber(),
                5,
                pageable.getSort()
        );

        return repo.findByReview_Id(reviewId, pageable).map(Mappers::toResponse);
    }


    @Transactional(readOnly = true)
    public ReviewImageResponse get(UUID id) {
        ReviewImage img = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review image not found"));
        return Mappers.toResponse(img);
    }

    @Transactional
    public ReviewImageResponse create(ReviewImageRequest in) {

        Review review = reviewRepo.findById(in.getReviewId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review does not exist"));

        ReviewImage img = Mappers.toEntity(in, review);
        repo.save(img);

        return Mappers.toResponse(img);
    }

    @Transactional
    public ReviewImageResponse update(UUID id, ReviewImageRequest in) {

        ReviewImage img = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review image not found"));

        if (in.getUrl() != null) img.setUrl(in.getUrl());
        if (in.getAltText() != null) img.setAltText(in.getAltText());

        repo.save(img);
        return Mappers.toResponse(img);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review image not found");
        }
        repo.deleteById(id);
    }
}
