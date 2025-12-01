package websiters.review.dto;

import lombok.Builder;
import lombok.Value;

import java.time.OffsetDateTime;
import java.util.UUID;

@Value
@Builder
public class ReviewResponse {

    UUID id;
    UUID userId;
    UUID restaurantId;

    String title;
    String content;

    Boolean hasAudio;
    Boolean hasImage;

    OffsetDateTime publishedAt;
}
