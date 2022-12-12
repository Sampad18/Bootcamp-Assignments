package SpringBoot.Assignment1.repository;


import SpringBoot.Assignment1.Entity.Employee;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface EmployeeRepository extends ReactiveCassandraRepository<Employee,Integer> {
    @AllowFiltering
    Mono<Boolean> existsByEmpId(int empId);
    @AllowFiltering
    Mono<Employee> findByempId(int empId);
}
