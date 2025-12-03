package websiters.review.client;

import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import websiters.review.dto.UserResponse;
import websiters.review.dto.RestaurantResponse;

import java.util.UUID;

@FeignClient(
        name = "gastroreview",
        url = "${services.gastroreview.url}"
)
public interface GastroReviewClient {

    @GetMapping("/api/users/{id}")
    UserResponse getUser(@PathVariable @NotNull UUID id);

    @GetMapping("/api/restaurants/{id}")
    RestaurantResponse getRestaurant(@PathVariable @NotNull UUID id);
}
