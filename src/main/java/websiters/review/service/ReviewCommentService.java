package websiters.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import websiters.review.client.GastroReviewClient;
import websiters.review.dto.ReviewCommentRequest;
import websiters.review.dto.ReviewCommentResponse;
import websiters.review.dto.ReviewCommentAnalysisResponse;
import websiters.review.mapper.Mappers;
import websiters.review.model.ReviewComment;
import websiters.review.repository.ReviewCommentRepository;
import websiters.review.repository.ReviewRepository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewCommentService {

    private final ReviewCommentRepository commentRepo;
    private final ReviewRepository reviewRepo;
    private final GastroReviewClient gastroClient;

    @Transactional(readOnly = true)
    public Page<ReviewCommentResponse> list(Pageable pageable) {
        Pageable fixed = PageRequest.of(pageable.getPageNumber(), 5);
        return commentRepo.findAll(fixed).map(Mappers::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ReviewCommentResponse> findByReview(UUID reviewId, Pageable pageable) {
        List<ReviewComment> list = commentRepo.findByReviewId(reviewId);
        return paginate(list, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReviewCommentResponse> findByAuthor(UUID authorId, Pageable pageable) {
        List<ReviewComment> list = commentRepo.findByAuthorId(authorId);
        return paginate(list, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ReviewCommentResponse> findReplies(UUID parentId, Pageable pageable) {
        List<ReviewComment> list = commentRepo.findByParentId(parentId);
        return paginate(list, pageable);
    }

    @Transactional(readOnly = true)
    public ReviewCommentResponse get(UUID id) {
        ReviewComment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));
        return Mappers.toResponse(comment);
    }

    @Transactional
    public ReviewCommentResponse create(ReviewCommentRequest request) {

        if (!reviewRepo.existsById(request.getReviewId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Review not found");
        }

        var user = gastroClient.getUser(request.getAuthorId());
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Author does not exist");
        }

        if (request.getParentId() != null && !commentRepo.existsById(request.getParentId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parent comment not found");
        }

        ReviewComment comment = ReviewComment.builder()
                .reviewId(request.getReviewId())
                .authorId(request.getAuthorId())
                .parentId(request.getParentId())
                .content(request.getContent())
                .publishedAt(Instant.now())
                .build();

        commentRepo.save(comment);

        return Mappers.toResponse(comment);
    }

    @Transactional
    public ReviewCommentResponse update(UUID id, ReviewCommentRequest request) {
        ReviewComment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found"));

        if (request.getContent() != null) {
            comment.setContent(request.getContent());
        }

        commentRepo.save(comment);

        return Mappers.toResponse(comment);
    }

    @Transactional
    public void delete(UUID id) {
        if (!commentRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment not found");
        }
        commentRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public ReviewCommentAnalysisResponse getAnalysis(UUID id) {
        throw new UnsupportedOperationException("Analysis service pending implementation");
    }

    private Page<ReviewCommentResponse> paginate(List<ReviewComment> list, Pageable pageable) {
        int size = 5;
        int page = pageable.getPageNumber();
        int start = page * size;
        int end = Math.min(start + size, list.size());

        List<ReviewCommentResponse> content = list.subList(Math.min(start, end), end)
                .stream()
                .map(Mappers::toResponse)
                .toList();

        return new PageImpl<>(content, PageRequest.of(page, size), list.size());
    }
}
