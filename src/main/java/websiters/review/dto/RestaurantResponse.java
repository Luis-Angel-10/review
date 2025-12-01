package websiters.review.dto;

import lombok.Data;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private String description;
    private String phone;
    private String email;
}
