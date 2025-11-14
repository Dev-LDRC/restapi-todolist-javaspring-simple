package br.com.javaspringrocketintro.todolist.tasks;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ITasksRepository extends JpaRepository<TasksModel, UUID> {

   List<TasksModel> findByUserId(UUID userId);   
}
