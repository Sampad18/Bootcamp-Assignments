package SpringBoot.Assignment2.service;

import SpringBoot.Assignment2.constants.KafkaConstants;
import SpringBoot.Assignment2.entity.EmployeeRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumingAndProducing {
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @KafkaListener(topics = KafkaConstants.TOPIC1,groupId = KafkaConstants.GROUP_ID)
    public void consume(EmployeeRequest employeeRequest){
        log.info("The message is consumed from the topic "+ KafkaConstants.TOPIC1);
        if (employeeRequest.getEmpName()==null || employeeRequest.getEmpCity()==null || employeeRequest.getEmpPhone()==null){
            log.info("The message is produced to the topic "+KafkaConstants.TOPIC3);
            kafkaTemplate.send(KafkaConstants.TOPIC3,employeeRequest);

        }
        else{
            log.info("The message is produced to the topic "+ KafkaConstants.TOPIC2);
            kafkaTemplate.send(KafkaConstants.TOPIC2,employeeRequest);

        }

    }




}