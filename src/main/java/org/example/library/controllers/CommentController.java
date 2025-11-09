package org.example.library.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @GetMapping("/book/{bookId}")
    public ResponseEntity<?> getCommentsByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(Collections.emptyList());
    }
}
