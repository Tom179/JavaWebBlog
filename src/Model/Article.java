package Model;

import java.sql.Time;

public class Article {
    public int ID;
    public String title;
    public String description;
    public String content;
    public String created_at;
    public int created_by;
    public String img;


    public Article(int ID, String title, String description, String content, String created_at,int created_by, String img) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.content = content;
        this.created_at = created_at;
        this.created_by=created_by;
        this.img = img;
    }
    
    @Override
    public String toString() {
        return "Article{" +
                "ID=" + ID +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", content='" + content + '\'' +
                ", created_at='" + created_at + '\'' +
                ", created_by=" + created_by +
                ", img='" + img + '\'' +
                '}';
    }
}
