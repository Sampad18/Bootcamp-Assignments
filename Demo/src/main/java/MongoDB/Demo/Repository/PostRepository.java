package MongoDB.Demo.Repository;

import MongoDB.Demo.Model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post,String>
{

}