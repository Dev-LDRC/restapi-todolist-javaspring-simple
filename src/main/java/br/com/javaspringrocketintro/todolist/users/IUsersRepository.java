package br.com.javaspringrocketintro.todolist.users;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUsersRepository extends JpaRepository<UsersModel, UUID> {

   UsersModel findByUserName(String userName);

}
