package br.com.javaspringrocketintro.todolist.users;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "table_users")
public class UsersModel {

   @Id
   @GeneratedValue(generator = "UUID")
   private UUID id;
   @Column(unique = true)
   private String userName;
   private String name;
   private String password;

   @UpdateTimestamp
   private LocalDateTime updatedAt;

   @CreationTimestamp
   private LocalDateTime createdAt;

}