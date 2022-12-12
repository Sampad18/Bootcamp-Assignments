package SpringBoot.Assignment2.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class EmployeeSerDes implements Serializer<EmployeeRequest>, Deserializer<EmployeeRequest> {
    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, EmployeeRequest employeeRequest) {


        try {
            return mapper.writeValueAsBytes(employeeRequest);
        } catch (JsonProcessingException e) {
            throw new SerializationException(e);
        }

    }

    @Override
    public EmployeeRequest deserialize(String topic, byte[] data) {

        try {
            return mapper.readValue(data, EmployeeRequest.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }


    @Override
    public void close() {
        Serializer.super.close();
    }
}