package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import services.GroupService;
import services.TagService;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/group")
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping("/all")
    public List<String> getAllTags(
            @RequestParam(value = "quantity", required = true) int quantity) {
        return groupService.getAllGroups(quantity);
    }
}