package br.com.javaspringrocketintro.todolist.users;

import org.springframework.data.repository.CrudRepository;

public interface IUsersRepository extends CrudRepository<UsersModel, String> {

   UsersModel findByUserName(String userName);

}
