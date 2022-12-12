package SpringBoot.Assignment3.service;

import SpringBoot.Assignment3.entity.Employee;
import SpringBoot.Assignment3.entity.Job;
import SpringBoot.Assignment3.entity.JobResponse;
import SpringBoot.Assignment3.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@Service
public class JobServices {
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;


    public Mono<JobResponse> createJob(Job job) {
        return jobRepository.existsByjobId(job.getJobId())
                .flatMap(aBoolean -> {
                    if(aBoolean){
                        return Mono.just(new JobResponse(job.getJobId(),job.getJobName(),job.getJavaExp(),job.getSpringExp(),"Already Exists"));
                    }
                    else{
                        return jobRepository.save(job).
                                map(j-> new JobResponse(job.getJobId(),job.getJobName(),job.getJavaExp(),job.getSpringExp(),"Created"));

                    }

                        }
                );
    }


    public Flux<Employee> findEmpForJobId(int jobId){

        Flux<Employee> employeeFlux = jobRepository.findByjobId(jobId).flatMap(job -> {

            return webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/findEmpSkillSet/" + job.getJavaExp())
                    .retrieve()
                    .bodyToFlux(Employee.class);


        });
        return employeeFlux;

    }
}

