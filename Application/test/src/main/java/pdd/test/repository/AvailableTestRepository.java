package pdd.test.repository;

import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pdd.test.domain.AvailableTest;

import java.util.List;

@Repository
public interface AvailableTestRepository extends JpaRepository<AvailableTest, Integer> {

}
