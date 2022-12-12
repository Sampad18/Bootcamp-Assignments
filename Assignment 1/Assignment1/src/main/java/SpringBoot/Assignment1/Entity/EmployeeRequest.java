package SpringBoot.Assignment1.Entity;

//import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private int empId;
    private String empName;
    private String empCity;
    private String empPhone;
    private double javaExp;
    private double springExp;

}
