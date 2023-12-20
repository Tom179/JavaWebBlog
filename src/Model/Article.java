package Model;

import java.sql.Time;

public class Article {
    int ID;
    String title;
    String description;
    String content;
    Time created_at;
    String img;


    public Article(int ID, String title, String description, String content, Time created_at, String img) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.content = content;
        this.created_at = created_at;
        this.img = img;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", created_at=" + created_at +
                ", img='" + img + '\'' +
                '}';
    }
}
