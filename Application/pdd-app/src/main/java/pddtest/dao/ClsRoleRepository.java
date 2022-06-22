package pddtest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pddtest.model.AvailableTest;
import pddtest.model.ClsRole;

@Repository
public interface ClsRoleRepository extends JpaRepository<ClsRole, Integer> {
}
