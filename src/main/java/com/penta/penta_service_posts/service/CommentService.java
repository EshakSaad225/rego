package com.penta.penta_service_posts.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.Converter.SpringSecurityAuditorAware;
import com.penta.penta_service_posts.domain.Comment;
import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.model.CommentDTO;
import com.penta.penta_service_posts.repos.CommentRepository;
import com.penta.penta_service_posts.util.NotFoundException;

@Service
public class CommentService {
    

    private final CommentRepository commentRepository;
    private final SpringSecurityAuditorAware auditorAware;
    private final PostService postService ;

    public CommentService(final CommentRepository commentRepository ,final SpringSecurityAuditorAware auditorAware ,final PostService postService ) {
        this.commentRepository = commentRepository;
        this.auditorAware = auditorAware ;
        this.postService = postService ;
    }

    public List<CommentDTO> findAll() {
        final List<Comment> comments = commentRepository.findAll(Sort.by("id"));
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    } 

    public List<CommentDTO> getPostComments(UUID postId) {
        final List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream()
                .map(comment -> mapToDTO(comment, new CommentDTO()))
                .toList();
    } 

    // public CommentDTO get(final UUID id) {
    //     return commentRepository.findById(id)
    //             .map(comment -> mapToDTO(comment, new CommentDTO()))
    //             .orElseThrow(NotFoundException::new);
    // }

    public UUID create(final CommentDTO commentDTO) {
        final Comment comment = new Comment();
        mapToEntity(commentDTO, comment);
        return commentRepository.save(comment).getId();
    }

    public void update(final UUID id, final CommentDTO commentDTO) {
        final Comment comment = commentRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && comment.getCreatedBy().getId().equals(currentUser.get().getId())){
            mapToEntity(commentDTO, comment);
            commentRepository.save(comment);
        }
        else {
            throw new AccessDeniedException("You are not allowed to update this Comment.");
        }  
    }

    public void delete(final UUID id) {
        final Comment comment = commentRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && comment.getCreatedBy().getId().equals(currentUser.get().getId())){
            commentRepository.deleteById(id);
        }
        else {
            throw new AccessDeniedException("You are not allowed to Delete this Comment.");
        }
    }

    private CommentDTO mapToDTO(final Comment comment, final CommentDTO commentDTO) {
        commentDTO.setId(comment.getId());
        commentDTO.setPost(comment.getPost());
        commentDTO.setParent(comment.getParent());
        commentDTO.setText(comment.getText());
        commentDTO.setAttachments(comment.getAttachments());
        commentDTO.setScore(comment.getScore());
        commentDTO.setCreatedBy(comment.getCreatedBy());
        commentDTO.setCreatedAt(comment.getCreatedAt());
        commentDTO.setUpdatedAt(comment.getUpdatedAt());
        commentDTO.setMentions(comment.getMentions());
        return commentDTO;
    }

    private Comment mapToEntity(final CommentDTO commentDTO, final Comment comment) {
        comment.setPost(commentDTO.getPost());
        comment.setParent(commentDTO.getParent());
        comment.setText(commentDTO.getText());
        comment.setAttachments(commentDTO.getAttachments());
        comment.setScore(commentDTO.getScore());
        comment.setMentions(postService.getMentionsFromText(commentDTO.getText()));
        return comment;
    }

}
