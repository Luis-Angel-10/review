package websiters.review.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;
import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "review_audio")
public class ReviewAudio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID reviewId;

    private String url;

    private Integer durationSeconds;

    private String transcription;

    private Instant createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }
}
