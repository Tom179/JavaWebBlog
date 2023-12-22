package Model;

import java.sql.Time;

public class Article {
    public int ID;
    public String title;
    public String description;
    public String content;
    public String created_at;
    public String img;


    public Article(int ID, String title, String description, String content, String created_at, String img) {
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
