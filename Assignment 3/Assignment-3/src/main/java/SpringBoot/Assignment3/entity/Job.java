package SpringBoot.Assignment3.entity;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "job")
public class Job {

    @NotNull(message = "the job id can not be null")
    @PrimaryKeyColumn(value = "job_id", ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private int jobId;
    @NotNull(message = "the job name can not be null")
    @NotBlank(message = "the job name can not be blank")
    @Column(value = "job_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String jobName;
    @NotNull(message = "the java experience can not be null")
    @PrimaryKeyColumn(value="java_exp",ordinal = 1, type= PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING)
    private double javaExp;
    @NotNull(message = "the spring experience can not be null")
    @PrimaryKeyColumn(value="spring_exp",ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private double springExp;
}
