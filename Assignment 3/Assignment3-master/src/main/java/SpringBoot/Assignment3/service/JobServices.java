package SpringBoot.Assignment3.service;

import SpringBoot.Assignment3.entity.Employee;
import SpringBoot.Assignment3.entity.Job;
import SpringBoot.Assignment3.entity.JobResponse;
import SpringBoot.Assignment3.repository.JobDtoRepository;
import SpringBoot.Assignment3.repository.JobRepository;
import com.hazelcast.map.IMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class JobServices {
    @Autowired
    private IMap<Integer, Job> userCache;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobDtoRepository jobDtoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;


    public Mono<JobResponse> createJob(Job job) {
        return jobRepository.existsByjobId(job.getJobId())
                .flatMap(aBoolean -> {
                            if (aBoolean) {
                                return Mono.just(new JobResponse(job.getJobId(), job.getJobName(), job.getJavaExp(), job.getSpringExp(), "Already Exists"));
                            } else {
                                return jobRepository.save(job).
                                        map(j -> new JobResponse(job.getJobId(), job.getJobName(), job.getJavaExp(), job.getSpringExp(), "Created"));

                            }

                        }
                );
    }


    public Flux<Employee> findEmpForJobId(int jobId) {

        Flux<Employee> employeeFlux = jobRepository.findByjobId(jobId).flatMap(job -> {

            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/findEmpSkillSet/" + job.getJavaExp())
                    .retrieve()
                    .bodyToFlux(Employee.class);


        });
        return employeeFlux;

    }
//    public Mono<Job> getJobProfileFromCache(int id) {
//
//        if (userCache.containsKey(id)) {
//            return Mono.just(userCache.get(id))
//                    .doOnNext(p-> log.info("Employee with id " + p.getJobId() + " found in cache"));
//        } else {
//            return jobDtoRepository.findByjobId(id)
//                    .doOnNext(data -> userCache.put(id, data))
//                    .doOnNext(p -> log.info("Employee with id " + p.getJobId() + " set in cache"));
//        }
//    }
//
//    public Mono<Job> addUser(Job jobDto) {
//
//        return jobDtoRepository.insert(jobDto)
//                // Store user in cache
//                .doOnSuccess(v -> userCache.put(jobDto.getJobId(), jobDto))
//                .doOnNext(p -> log.info("Employee with id " + p.getJobId() + " set in cache and database"));
//    }

    public Mono<Job> getJobProfileFromCache(int id) {

        Mono<Job> result = getUserFromCache(id);
        return   result
                .switchIfEmpty(getUserFromDB(id))
                .flatMap(user -> saveUserToCache(user));
    }

    public Mono<Job> addUser(Job jobDto) {
        return jobDtoRepository.save(jobDto)
                .flatMap(jobDto1 -> saveUserToCache(jobDto));
    }

    private Mono<Job> getUserFromCache(int id) {

        return Mono.fromCompletionStage(userCache.getAsync(id));
        //return jobDto;
    }


    private Mono<? extends Job> saveUserToCache(Job user) {
        userCache.setAsync(user.getJobId(), user);
        return Mono.just(user);
    }

    private Mono<Job> getUserFromDB(int id) {
        return jobDtoRepository.findByjobId(id);
    }

}


//}
