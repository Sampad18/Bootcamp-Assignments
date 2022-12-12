package SpringBoot.Assignment1.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(value = "emp_skill")
public class EmpSkill {

    @PrimaryKeyColumn(value = "emp_id", ordinal = 0,type = PrimaryKeyType.PARTITIONED)
    private int employeeId;
    @PrimaryKeyColumn(value="java_exp",ordinal = 1, type=PrimaryKeyType.CLUSTERED,ordering = Ordering.DESCENDING)
    private double javaExp;
    @PrimaryKeyColumn(value="spring_exp",ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private double springExp;

}
