package SpringBoot.Assignment2.constants;

import org.springframework.stereotype.Component;

@Component
public  class KafkaConstants {

    public static final String TOPIC1="app_update";
    public static final String TOPIC2="employee_updates";
    public static final String TOPIC3="employee_DLQ";
    public static final String GROUP_ID="group-employees";
    public static final String HOST="localhost:9092";
}