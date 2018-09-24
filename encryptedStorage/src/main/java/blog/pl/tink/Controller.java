package blog.pl.tink;

import blog.pl.tink.cache.SecretInformationRepo;
import blog.pl.tink.datamodel.UserPostsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller {

    @Autowired
    SecretInformationRepo repo;

    @PostMapping(value = "/store", consumes = "application/json")
    public void storeObject(@RequestBody UserPostsDto dto){
        repo.save(dto);
    }

    @GetMapping("/fetch/{id}")
    public UserPostsDto fetchObject(@PathVariable String id){
        return repo.fetchById(id);
    }
}
