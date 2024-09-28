package pl.com.britenet.lambda.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import pl.com.britenet.lambda.entity.User;
import pl.com.britenet.lambda.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@EnableWebMvc
@RestController
@RequestMapping("/users")
@CrossOrigin(origins="*")
public class UserController {
//안녕하세요?
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public @ResponseBody
    Iterable<User> findAll() {
        return userRepository.findAll();
    }
    //장충동 왕족발 보쌈~

    @PostMapping
    public @ResponseBody
    ResponseEntity<String> addUser(@RequestParam String name, @RequestParam String email) {
        userRepository.save(new User(name, email));
        return new ResponseEntity<String>("saved", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Optional<User> findById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    @DeleteMapping("/{id}")
    public @ResponseBody ResponseEntity<String> delete(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public @ResponseBody
    ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody Map<String, String> map) {
    	System.out.println(map);
    	String email = map.get("email");
    	String name = map.get("name");
    	Optional<User> user = userRepository.findById(id);
        System.out.println(user);
        if (user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setEmail(email);
            userToUpdate.setName(name);
            userRepository.save(userToUpdate);
            return new ResponseEntity<String>("updated", HttpStatus.OK);
        }
        return new ResponseEntity<String>("bad request", HttpStatus.BAD_REQUEST);
    }
}
