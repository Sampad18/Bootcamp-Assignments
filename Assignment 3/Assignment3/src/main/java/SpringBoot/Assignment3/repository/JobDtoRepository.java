package SpringBoot.Assignment3.repository;

import SpringBoot.Assignment3.entity.Job;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import reactor.core.publisher.Mono;

public interface JobDtoRepository extends ReactiveCassandraRepository<Job,Integer> {
    @AllowFiltering
    Mono<Job> findByjobId(int jobId);
}
