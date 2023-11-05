package com.example.Project.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.Project.entities.Post;
import com.example.Project.services.impl.PostService;
import com.example.Project.utils.ImageUtility;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")

public class PostController {
    private final PostService postServices ;


    @PostMapping("/add")
    public ResponseEntity<ImageUploadResponse> addPost(@RequestPart("post") Post post,
                                        @RequestParam(value = "image", required = false) MultipartFile file) throws IOException {

        if (file != null) {
            post.setImage(ImageUtility.compressImage(file.getBytes()));
            post.setType(file.getContentType());
            post.setName(file.getOriginalFilename());
        }

        Post savedPost = postServices.addOrUpdatePost(post);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }
    @GetMapping("/get/{id}")
    Post getPost(@PathVariable("id") Integer id_post){
        return postServices.retrievePost(id_post);
    }
    @GetMapping("/all")
    List<Post> getAllPosts(){
        return postServices.retrieveAllPosts();
    }
    @DeleteMapping("/delete/{id}")
    void deletePost(@PathVariable("id") Integer id_post){
        postServices.removePost(id_post);
    }
    @GetMapping(path = {"/get/image/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Post> dbPost = postServices.retrievePostByName(name);

        if (!dbPost.isPresent() || dbPost.get().getImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbPost.get().getType()))
                .body(ImageUtility.decompressImage(dbPost.get().getImage()));
    }


}
