package websiters.review.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantResponse {
    private UUID id;
    private String name;
    private String description;
    private String phone;
    private String email;
}
