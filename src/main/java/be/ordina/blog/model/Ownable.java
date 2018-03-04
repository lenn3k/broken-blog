package be.ordina.blog.model;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public class Ownable {

    private static final String DEFAULT_USERNAME = "Anonymous";

    @NotNull
    protected String author = DEFAULT_USERNAME;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
