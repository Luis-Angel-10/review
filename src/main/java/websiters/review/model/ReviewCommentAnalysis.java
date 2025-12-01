package websiters.review.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "review_comment_analysis")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ReviewCommentAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID commentId;

    private String sentiment;

    private Double positiveScore;
    private Double neutralScore;
    private Double negativeScore;

    @Column(columnDefinition = "text")
    private String keyPhrases;

    private Instant analyzedAt;
}
