package pddtest.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pddtest.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<User> findBylogin(String login);
}
