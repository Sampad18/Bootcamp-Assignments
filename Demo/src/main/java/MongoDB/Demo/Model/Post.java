package MongoDB.Demo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.asm.Advice;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "JobPost")
public class Post {
    @Getter @Setter private String id;
    @Getter @Setter private String profile;
    @Getter @Setter private String desc;
    @Getter @Setter private int exp;
   @Getter @Setter
   private String techs[];


    @Override
    public String toString() {
        return "Post{" +
                "id='" + id +
                ", profile='" + profile + '\'' +
                ", desc='" + desc + '\'' +
                ", exp=" + exp +
                ", techs=" + Arrays.toString(techs) +
                '}';
    }
}
