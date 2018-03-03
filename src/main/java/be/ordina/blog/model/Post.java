package be.ordina.blog.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends Ownable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    @NotNull
    private String title;
    @NotNull
    private String body;
    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Post> comments = new ArrayList<>();

    public Post() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Post> getComments() {
        return comments;
    }

    public void setComments(List<Post> comments) {
        this.comments = comments;
    }
}
