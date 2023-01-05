package MongoDB.Demo.Repository;

import MongoDB.Demo.Model.Post;

import java.util.List;

public interface SearchRepository {

    List<Post> findByText(String text);

}