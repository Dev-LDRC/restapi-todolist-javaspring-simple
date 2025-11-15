package br.com.javaspringrocketintro.todolist.tasks;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ITasksRepository extends CrudRepository<TasksModel, String> {

   List<TasksModel> findByUserId(String userId);   
}
