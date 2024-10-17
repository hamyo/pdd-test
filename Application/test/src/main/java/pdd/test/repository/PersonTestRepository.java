package pdd.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.PersonTest;

@Repository
public interface PersonTestRepository extends JpaRepository<PersonTest, Long> {
}
