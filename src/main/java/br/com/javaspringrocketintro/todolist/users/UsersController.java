package br.com.javaspringrocketintro.todolist.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/users")
public class UsersController {

   @Autowired
   private IUsersRepository userRepository;

   @PostMapping("/")
   public ResponseEntity<Object> create(@RequestBody UsersModel userModel) {
      
      var userCheck = this.userRepository.findByUserName(userModel.getUserName());
      if (userCheck != null) {
         
         return ResponseEntity.status(400).body("Esté nome ja existe, tente outro.");
         
      } 

      var passwordHashed = BCrypt.withDefaults().hashToString(10, userModel.getPassword().toCharArray());
      userModel.setPassword(passwordHashed);
      
      var userCreated = this.userRepository.save(userModel);

      return ResponseEntity.status(201).body(userCreated);
   }
   
}
