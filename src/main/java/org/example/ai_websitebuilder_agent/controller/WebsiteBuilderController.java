package org.example.ai_websitebuilder_agent.controller;

import org.example.ai_websitebuilder_agent.service.WebsiteBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class WebsiteBuilderController {
    WebsiteBuilderService websiteBuilderService;

    @Autowired
    public WebsiteBuilderController(WebsiteBuilderService websiteBuilderService) {
        this.websiteBuilderService = websiteBuilderService;
    }

    @PostMapping("/create-site")
    public ResponseEntity<?> createWebsite(@RequestBody String promptByUser){
        String response = this.websiteBuilderService.createWebsite(promptByUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("response from agent", response));
    }
}
