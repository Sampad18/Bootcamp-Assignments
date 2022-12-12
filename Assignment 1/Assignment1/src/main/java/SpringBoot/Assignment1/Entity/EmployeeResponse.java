package SpringBoot.Assignment1.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {
    private int empId;
    private String empName;
    private String empCity;
    private String empPhone;
    private double javaExp;
    private double springExp;
    private String status;

}
