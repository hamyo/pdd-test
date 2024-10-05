package pdd.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pdd.test.domain.AvailableTest;

@Repository
public interface AvailableTestRepository extends JpaRepository<AvailableTest, Integer> {
}
