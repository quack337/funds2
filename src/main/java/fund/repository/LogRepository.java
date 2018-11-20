package fund.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fund.domain.Log;

public interface LogRepository extends JpaRepository<Log, Integer>  {

}
