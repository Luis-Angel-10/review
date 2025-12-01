package websiters.review.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review_comment")
public class ReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID reviewId;

    private UUID authorId;

    private UUID parentId;

    private String content;

    private Instant publishedAt;

    @PrePersist
    public void prePersist() {
        publishedAt = Instant.now();
    }
}
