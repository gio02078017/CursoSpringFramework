package com.example.comments.controllers;

import com.example.comments.CommentsApplication;
import com.example.comments.domains.models.Comment;
import com.example.comments.domains.models.Publication;
import com.example.comments.domains.models.User;
import com.example.comments.repositories.CommentRepository;
import com.example.comments.repositories.PublicationRepository;
import com.example.comments.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/comments")
public class CommentController {

    private static final Logger log = LoggerFactory.getLogger(CommentsApplication.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    @PostMapping
    public ResponseEntity<Comment> addNewComment (@RequestBody Comment comment) {

        Optional<Publication> existingPublication = publicationRepository.findById(comment.getPublicationId());

        if(existingPublication.isPresent()){
            commentRepository.save(comment);
            return ResponseEntity.ok(comment);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment (@PathVariable Long id) {

        Optional<Comment> existingComment = commentRepository.findById(id);

        return existingComment.isPresent() ? ResponseEntity.ok(existingComment.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Comment> getAllComments(){
        return commentRepository.findAll();
    }

    @GetMapping("/publicationId/{id}")
    public ResponseEntity<List<Comment>> getPublicationsUser (@PathVariable Long id) {
        Optional<List<Comment>> existingComments = commentRepository.findByPublicationId(id);

        return existingComments.isPresent() ? ResponseEntity.ok(existingComments.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updateComment){
        Optional<Comment> existingComment = commentRepository.findById(id);
        if(existingComment.isPresent()){
            Comment comment = existingComment.get();
            comment.setDescription(updateComment.getDescription());
            comment.setPublicationId(updateComment.getPublicationId());
            commentRepository.save(comment);
            return ResponseEntity.ok(comment);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Comment>> deleteComment(@PathVariable Long id){
        Optional<Comment> existingComment = commentRepository.findById(id);
        if(existingComment.isPresent()) {
            commentRepository.deleteById(id);
            return ResponseEntity.ok(commentRepository.findAll());
        }else{
            return ResponseEntity.notFound().build();
        }
    }






}
