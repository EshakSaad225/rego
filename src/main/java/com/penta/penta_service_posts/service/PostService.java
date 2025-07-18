package com.penta.penta_service_posts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.penta.penta_service_posts.Converter.SpringSecurityAuditorAware;
import com.penta.penta_service_posts.domain.Post;
import com.penta.penta_service_posts.domain.Users;
import com.penta.penta_service_posts.model.PostDTO;
import com.penta.penta_service_posts.repos.PostRepository;
import com.penta.penta_service_posts.repos.UsersRepository;
import com.penta.penta_service_posts.util.NotFoundException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UsersRepository usersRepository;
    private final SpringSecurityAuditorAware auditorAware;

    public PostService(final PostRepository postRepository,final UsersRepository usersRepository ,final SpringSecurityAuditorAware auditorAware) {
        this.postRepository = postRepository;
        this.usersRepository = usersRepository;
        this.auditorAware = auditorAware ;
    }

    public List<Users> getMentionsFromText(String text){ 

        // %!@#<UserId>#@!%

      String regex = "%!@#<(.{36})>#@!%";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      List<String> usersId =  new ArrayList <> () ;

      while (matcher.find()) {
        usersId.add(matcher.group(1)) ;
      }
      if(!usersId.isEmpty()){
        List<Users> usres = usersRepository.findByIdIn(usersId);
        System.out.println(usres);
        return usres ;
      }
      return new ArrayList<>();
  }

    public List<String> getHashtagsFromText(String text){ 

      String regex = "(#[^\\s]+)";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      List<String> hashtags =  new ArrayList <> () ;

      while (matcher.find()) {
        hashtags.add(matcher.group(1)) ;
      }
      if(!hashtags.isEmpty()){
        return hashtags ;
      }
      return new ArrayList<>();
  }

    public List<PostDTO> findAll() {
        final List<Post> posts = postRepository.findAll(Sort.by("id"));
        return posts.stream()
                .map(post -> mapToDTO(post, new PostDTO()))
                .toList();
    }

    public List<PostDTO> getUserPosts(String userId) {
        final List<Post> posts = postRepository.findByCreatedById(userId);
        return posts.stream()
                .map(post -> mapToDTO(post, new PostDTO()))
                .toList();
    }

    public UUID create(final PostDTO postDTO) {
        final Post post = new Post();
        mapToEntity(postDTO, post);
        return postRepository.save(post).getId();
    }

    public void update(final UUID id, final PostDTO postDTO) {
        final Post post = postRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && post.getCreatedBy().getId().equals(currentUser.get().getId())){
            mapToEntity(postDTO, post);
            postRepository.save(post);
        }
        else {
            throw new AccessDeniedException("You are not allowed to update this post.");
        }
    }

    public void delete(final UUID id) {
        final Post post = postRepository.findById(id)
            .orElseThrow(NotFoundException::new);
        Optional<Users> currentUser = auditorAware.getCurrentAuditor();
        if(currentUser.isPresent() && post.getCreatedBy().getId().equals(currentUser.get().getId())){
            postRepository.deleteById(id);
        }
        else {
            throw new AccessDeniedException("You are not allowed to Delete this post.");
        }
    }

    private PostDTO mapToDTO(final Post post, final PostDTO postDTO) {
        postDTO.setId(post.getId());
        postDTO.setType(post.getType());
        postDTO.setText(post.getText());
        postDTO.setAttachments(post.getAttachments());
        postDTO.setIsSaved(post.getIsSaved());
        postDTO.setIsShared(post.getIsShared());
        postDTO.setSharedPost(post.getSharedPost());
        postDTO.setCreatedBy(post.getCreatedBy());
        postDTO.setMoreData(post.getMoreData());
        postDTO.setMentions(post.getMentions());
        postDTO.setHashtags(post.getHashtags());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
        return postDTO;
    }

    private Post mapToEntity(final PostDTO postDTO, final Post post) {

        post.setType(postDTO.getType());
        post.setText(postDTO.getText());
        post.setAttachments(postDTO.getAttachments());
        post.setIsSaved(postDTO.getIsSaved());
        post.setIsShared(postDTO.getIsShared());
        post.setSharedPost(postDTO.getSharedPost());
        post.setMoreData(postDTO.getMoreData());
        post.setMentions(getMentionsFromText(postDTO.getText()));
        post.setHashtags(getHashtagsFromText(postDTO.getText()));
        return post;
    }

}
