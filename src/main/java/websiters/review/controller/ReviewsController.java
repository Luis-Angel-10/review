package websiters.review.controller;

import websiters.review.dto.ReviewRequest;
import websiters.review.dto.ReviewResponse;
import websiters.review.service.ReviewService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Validated
@Tag(name = "Reviews", description = "Operations related to restaurant reviews.")
public class ReviewsController {

    private final ReviewService service;

    @GetMapping
    @Operation(summary = "List all reviews", description = "Paginated list (5 per page).")
    @ApiResponse(responseCode = "200", description = "Reviews retrieved",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ReviewResponse.class))))
    public Page<ReviewResponse> list(@Parameter(description = "Pagination parameters") Pageable pageable) {
        Pageable fixed = Pageable.ofSize(5).withPage(pageable.getPageNumber());
        return service.list(fixed);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get review by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReviewResponse.class))),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ReviewResponse get(@PathVariable UUID id) {
        return service.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new review")
    public ReviewResponse create(@Valid @RequestBody ReviewRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update review")
    public ReviewResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody ReviewRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete review")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
