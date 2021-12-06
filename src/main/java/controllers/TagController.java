package controllers;

import entities.Tag;
import entities.response.ReviewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import services.ReviewService;
import services.TagService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @GetMapping("/all")
    public List<String> getAllTags(
            @RequestParam(value = "quantity", required = true) int quantity) {
        return tagService.getAllTags(quantity);
    }
}
