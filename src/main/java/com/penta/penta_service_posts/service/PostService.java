package com.penta.penta_service_posts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public PostService(PostRepository postRepository, UsersRepository usersRepository) {
        this.postRepository = postRepository;
        this.usersRepository = usersRepository;
    }

    public List<Users> getMentionsFromText(String text){ 

        // "\\$%<111111>\\$\\$<usname>

      String regex = "\\$%<(.*?)>\\$\\$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(text);
      List<UUID> usersId =  new ArrayList <> () ;

      while (matcher.find()) {
        System.out.println("dlkwjdslpsiwjpld"+matcher.group(1));
        usersId.add(UUID.fromString(matcher.group(1))) ;
      }
      List<Users> usres = usersRepository.findByIdIn(usersId);
      System.out.println(usres);
    return usres ;
  }

    public List<PostDTO> findAll() {
        final List<Post> posts = postRepository.findAll(Sort.by("id"));
        return posts.stream()
                .map(post -> mapToDTO(post, new PostDTO()))
                .toList();
    }

    public List<PostDTO> getUserPosts(UUID userId) {
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
        mapToEntity(postDTO, post);
        postRepository.save(post);
    }

    public void delete(final UUID id) {
        postRepository.deleteById(id);
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
        post.setCreatedBy(postDTO.getCreatedBy());
        post.setMoreData(postDTO.getMoreData());
        post.setMentions(getMentionsFromText( postDTO.getText()));
        post.setHashtags(postDTO.getHashtags());
        return post;
    }

}
