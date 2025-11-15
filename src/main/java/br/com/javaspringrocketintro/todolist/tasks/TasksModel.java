package br.com.javaspringrocketintro.todolist.tasks;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash("tasks")
public class TasksModel implements Serializable {

   @Id
   private String id;
   
   @org.springframework.data.redis.core.index.Indexed
   private String userId;
   
   private String title;
   private String description;
   private LocalDateTime startAt;
   private LocalDateTime endAt;
   private String priority;
   
   private LocalDateTime updatedAt;
   private LocalDateTime createdAt;

   public TasksModel() {
      this.id = UUID.randomUUID().toString();
      this.createdAt = LocalDateTime.now();
      this.updatedAt = LocalDateTime.now();
   }

   public void setTitle(String title) throws Exception {
      if (title.length() > 50) {
         throw new Exception("O titulo deve ter no maximo 50 caracteres");
      }
      this.title = title;
   }
}
