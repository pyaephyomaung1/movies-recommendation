package com.store.backend.image;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    private static final String img_dir = "upload/";

    @GetMapping("/images/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName){
      try {  Path imagePath = Paths.get(img_dir, imageName);
        Resource resource = new UrlResource(imagePath.toUri());

        if(resource.exists() && resource.isReadable()){
            return ResponseEntity.ok().body(resource);
        } else {
            return ResponseEntity.notFound().build();
        } 
    } catch (Exception e) {
        return ResponseEntity.status(500).build();
    }
}
}
