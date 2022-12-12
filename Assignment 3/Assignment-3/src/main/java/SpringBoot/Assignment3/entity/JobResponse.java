package SpringBoot.Assignment3.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobResponse {
    private int jobId;
    private String jobName;
    private double javaExp;
    private double springExp;
    private String status;
}
