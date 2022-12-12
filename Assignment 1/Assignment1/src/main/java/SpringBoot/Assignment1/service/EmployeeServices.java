package SpringBoot.Assignment1.service;

import SpringBoot.Assignment1.constants.KafkaConstants;
import SpringBoot.Assignment1.Entity.*;
import SpringBoot.Assignment1.repository.EmpSkillRepository;
import SpringBoot.Assignment1.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class EmployeeServices {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmpSkillRepository empSkillRepository;

    @Autowired
    private KafkaTemplate kafkaTemplate;



    public Mono<EmployeeResponse> createEmployee(EmployeeRequest employeeRequest) {
        Employee employee= new Employee(employeeRequest.getEmpId(),employeeRequest.getEmpName(),
                employeeRequest.getEmpCity(),employeeRequest.getEmpPhone());

        EmpSkill empSkill= new EmpSkill(employeeRequest.getEmpId(), employeeRequest.getJavaExp(), employeeRequest.getSpringExp());
        kafkaTemplate.send(KafkaConstants.TOPIC,employeeRequest);
        log.info("Message send successfully");
        return this.employeeRepository.existsByEmpId(employeeRequest.getEmpId())
                .flatMap(aBoolean -> {
                    if(aBoolean){
                        return Mono.zip(Mono.just(employee),Mono.just(empSkill))
                                .map(t->new EmployeeResponse(t.getT1().getEmpId(),t.getT1().getEmpName(),
                                        t.getT1().getEmpCity(),t.getT1().getEmpPhone(),t.getT2().getJavaExp(),
                                        t.getT2().getSpringExp(),"Already Exists")
                                ).log("Employee already exists");
                    }
                    else{
                        return Mono.zip(employeeRepository.save(employee),empSkillRepository.save(empSkill))
                                .map(t->new EmployeeResponse(t.getT1().getEmpId(),t.getT1().getEmpName(),
                                        t.getT1().getEmpCity(),t.getT1().getEmpPhone(),t.getT2().getJavaExp(),
                                        t.getT2().getSpringExp(),"Created")
                                ).log("Employee created");

                    }
                });

    }

    public Flux<EmployeeRequest> findGreaterThanJavaExp(ExpRequest expRequest) {
        Flux<EmpSkill> empSkillFlux = null;
        if(expRequest.getSpringExp()==0.0d){
            empSkillFlux = empSkillRepository.findByjavaExpGreaterThan(expRequest.getJavaExp());
        }
        else if (expRequest.getJavaExp()==0.0d){
            empSkillFlux=empSkillRepository.findByspringExpGreaterThan(expRequest.getSpringExp());
        }
        else if(expRequest.getJavaExp()!=0.0  &&  expRequest.getSpringExp()!=0.0){
            empSkillFlux = empSkillRepository.findByjavaExpGreaterThan(expRequest.getJavaExp()).
                    filter(empSkill -> empSkill.getSpringExp() > expRequest.getSpringExp());

        }
        Flux<Employee> employeeFlux = empSkillFlux.concatMap((empSkill -> {return  this.employeeRepository.findByempId(empSkill.getEmployeeId());}));
        return Flux.zip(employeeFlux,empSkillFlux)
                .map(emp-> new EmployeeRequest(emp.getT1().getEmpId(),emp.getT1().getEmpName(),
                        emp.getT1().getEmpCity(),emp.getT1().getEmpPhone(),emp.getT2().getJavaExp(),

                        emp.getT2().getSpringExp()));
//


    }

    public Flux<EmployeeRequest> findEmpWithExp(double javaExp) {

        Flux<EmpSkill> empSkillFlux = empSkillRepository.findByjavaExpGreaterThan(javaExp);
        Flux<Employee> employeeFlux = empSkillFlux.concatMap((empSkill -> {return  employeeRepository.findByempId(empSkill.getEmployeeId());}));
        return Flux.zip(employeeFlux,empSkillFlux)
                .map(emp-> new EmployeeRequest(emp.getT1().getEmpId(),emp.getT1().getEmpName(),
                        emp.getT1().getEmpCity(),emp.getT1().getEmpPhone(),emp.getT2().getJavaExp(),

                        emp.getT2().getSpringExp()));


    }
}