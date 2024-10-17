package com.example.comments.repositories;

import com.example.comments.domains.models.Comment;
import com.example.comments.domains.models.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<List<Comment>> findByPublicationId(Long publicationId);
}

