package SpringBoot.Assignment1.Entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;

public class EmployeeRequestDeserializer implements Deserializer<EmployeeRequest> {
    public static final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();


    @Override
    public EmployeeRequest deserialize(InputStream inputStream) throws IOException {
        return null;
    }

    @Override
    public EmployeeRequest deserializeFromByteArray(byte[] data) throws IOException {
        try {
            return mapper.readValue(data, EmployeeRequest.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}