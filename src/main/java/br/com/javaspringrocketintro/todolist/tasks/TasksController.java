package br.com.javaspringrocketintro.todolist.tasks;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TasksController {

   @Autowired
   private ITasksRepository tasksRepository;

   @PostMapping("/")
   public ResponseEntity<Object> create(@RequestBody TasksModel tasksModel, HttpServletRequest request) {

      var currentDate = LocalDateTime.now();
      if (currentDate.isAfter(tasksModel.getStartAt()) || currentDate.isAfter(tasksModel.getEndAt())) {
         return ResponseEntity.status(400).body("A data e hora de inicio deve ser presente ou futura.");
      }
      if (tasksModel.getStartAt().isAfter(tasksModel.getEndAt())) {
         return ResponseEntity.status(400).body("A data e hora de inicio deve vir antes da data e hora de fim.");
      }

      var userId = request.getAttribute("userId");

      tasksModel.setUserId(userId.toString());

      var taskCreated = tasksRepository.save(tasksModel);
      return ResponseEntity.status(200).body(taskCreated);

   }

   @GetMapping("/")
   public ResponseEntity<Object> read(HttpServletRequest request) {

      var userId = request.getAttribute("userId");

      var userTasks = tasksRepository.findByUserId(userId.toString());

      return ResponseEntity.status(200).body(userTasks);

   }

   @PutMapping("/{taskId}")
   public ResponseEntity<Object> update(@RequestBody TasksModel tasksModel, HttpServletRequest request, @PathVariable String taskId) {

      var task = this.tasksRepository.findById(taskId).orElse(null);

      if (task == null) {
         return ResponseEntity.status(400).body("Esta tarefa não existe.");
      }

      var userId = request.getAttribute("userId");

      if (!task.getUserId().equals(userId.toString())) {
         return ResponseEntity.status(400).body("Não autorizado para alterar esta tarefa.");
      }

      if (tasksModel.getTitle() != null) {
         try {
            task.setTitle(tasksModel.getTitle());
         } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
         }
      }
      if (tasksModel.getDescription() != null) {
         task.setDescription(tasksModel.getDescription());
      }
      if (tasksModel.getStartAt() != null) {
         task.setStartAt(tasksModel.getStartAt());
      }
      if (tasksModel.getEndAt() != null) {
         task.setEndAt(tasksModel.getEndAt());
      }
      if (tasksModel.getPriority() != null) {
         task.setPriority(tasksModel.getPriority());
      }

      var currentDate = LocalDateTime.now();
      if (currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
         return ResponseEntity.status(400).body("A data e hora de inicio deve ser presente ou futura.");
      }
      if (task.getStartAt().isAfter(task.getEndAt())) {
         return ResponseEntity.status(400).body("A data e hora de inicio deve vir antes da data e hora de fim.");
      }
      
      task.setUpdatedAt(LocalDateTime.now());

      var taskUpdated = this.tasksRepository.save(task);

      return ResponseEntity.status(200).body(taskUpdated);
   }

}
