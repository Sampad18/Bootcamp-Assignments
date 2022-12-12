package SpringBoot.Assignment1.constants;

import org.springframework.stereotype.Component;

@Component
public class KafkaConstants {

    public static final String TOPIC="app_update";
    public static final String GROUP_ID="group-employees";
    public static final String HOST="localhost:9092";
}