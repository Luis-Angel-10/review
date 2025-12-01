package websiters.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import websiters.review.client.GastroReviewClient;
import websiters.review.dto.ReviewRequest;
import websiters.review.dto.ReviewResponse;
import websiters.review.mapper.Mappers;
import websiters.review.model.Review;
import websiters.review.repository.ReviewRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository repo;
    private final GastroReviewClient gastroClient;

    @Transactional(readOnly = true)
    public Page<ReviewResponse> list(Pageable pageable) {
        Pageable fixed = PageRequest.of(pageable.getPageNumber(), 5);
        return repo.findAll(fixed).map(Mappers::toResponse);
    }

    @Transactional(readOnly = true)
    public ReviewResponse get(UUID id) {
        Review review = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        return Mappers.toResponse(review);
    }

    @Transactional
    public ReviewResponse create(ReviewRequest request) {

        var user = gastroClient.getUser(request.getUserId());
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");

        var restaurant = gastroClient.getRestaurant(request.getRestaurantId());
        if (restaurant == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant does not exist");

        Review review = Mappers.toEntity(request);
        review.setPublishedAt(java.time.LocalDateTime.now());

        repo.save(review);

        return Mappers.toResponse(review);
    }

    @Transactional
    public ReviewResponse update(UUID id, ReviewRequest request) {

        Review review = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found"));

        if (request.getTitle() != null) review.setTitle(request.getTitle());
        if (request.getContent() != null) review.setContent(request.getContent());
        if (request.getHasAudio() != null) review.setHasAudio(request.getHasAudio());
        if (request.getHasImage() != null) review.setHasImage(request.getHasImage());

        repo.save(review);

        return Mappers.toResponse(review);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        }

        repo.deleteById(id);
    }
}
