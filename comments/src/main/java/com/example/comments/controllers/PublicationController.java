package com.example.comments.controllers;

import com.example.comments.CommentsApplication;
import com.example.comments.domains.models.Publication;
import com.example.comments.domains.models.User;
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
@RequestMapping("/publications")
public class PublicationController {

    private static final Logger log = LoggerFactory.getLogger(CommentsApplication.class);

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Publication> addNewPublication (@RequestBody Publication publication) {

        Optional<User> existUser = userRepository.findById(publication.getUserId());

        if(existUser.isPresent()){
            publicationRepository.save(publication);
            return ResponseEntity.ok(publication);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> getPublication (@PathVariable Long id) {
        Optional<Publication> existingPublication = publicationRepository.findById(id);

        return existingPublication.isPresent() ? ResponseEntity.ok(existingPublication.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping("/userId/{id}")
    public ResponseEntity<List<Publication>> getPublicationsUser (@PathVariable Long id) {
        Optional<List<Publication>> existingPublications = publicationRepository.findByUserId(id);

        return existingPublications.isPresent() ? ResponseEntity.ok(existingPublications.get()) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Publication> getAllPublications(){
        return publicationRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Publication> updatePublication(@PathVariable Long id, @RequestBody Publication updatePublication){
        //return publicationRepository.save(publication);
        Optional<Publication> existingPublication = publicationRepository.findById(id);
        if(existingPublication.isPresent()){
            Publication publication = existingPublication.get();
            publication.setName(updatePublication.getName());
            publication.setUserId(updatePublication.getUserId());
            publicationRepository.save(publication);
            return ResponseEntity.ok(publication);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<List<Publication>> deletePublication(@PathVariable Long id){
        Optional<Publication> existingPublication = publicationRepository.findById(id);
        if(existingPublication.isPresent()) {
            publicationRepository.deleteById(id);
            return ResponseEntity.ok(publicationRepository.findAll());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
