package br.com.javaspringrocketintro.todolist.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import lombok.Data;

@Data
@RedisHash("users")
public class UsersModel implements Serializable {

   @Id
   private String id;

   @Indexed
   private String userName;
   private String name;
   private String password;

   private LocalDateTime updatedAt;
   private LocalDateTime createdAt;

   public UsersModel() {
      this.id = UUID.randomUUID().toString();
      this.createdAt = LocalDateTime.now();
      this.updatedAt = LocalDateTime.now();
   }
}