package SpringBoot.Assignment1.controller;

import SpringBoot.Assignment1.Entity.EmpSkill;
import SpringBoot.Assignment1.Entity.EmployeeRequest;
import SpringBoot.Assignment1.Entity.EmployeeResponse;
import SpringBoot.Assignment1.Entity.ExpRequest;
import SpringBoot.Assignment1.service.EmployeeServices;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeServices employeeServices;

    @PostMapping("/createEmployee")
    public Mono<EmployeeResponse> createEmployee(@RequestBody @Valid EmployeeRequest employeeRequest){

        return this.employeeServices.createEmployee(employeeRequest);
    }

    @GetMapping("/findEmpSkillSet")
    public Flux<EmployeeRequest> findGreaterThanJavaExp(@RequestBody ExpRequest expRequest){
        return this.employeeServices.findGreaterThanJavaExp(expRequest);
    }
    @GetMapping("/findEmpSkillSet/{javaExp}")
    public Flux<EmployeeRequest> findEmpWithExp(@PathVariable("javaExp") double javaExp){
        return this.employeeServices.findEmpWithExp(javaExp);
    }
}
