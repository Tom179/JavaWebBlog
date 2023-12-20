package servlet;

import java.util.Arrays;

public class article {
    int id;
    String title;
    int commentCounts;
    int ViewCounts;
    int weight;
    String createDate;
    String author;
    String body;
    tag[] tags;

    public article(int id, String title, int commentCounts, int viewCounts, int weight, String createDate, String author, String body, tag[] tags) {
        this.id = id;
        this.title = title;
        this.commentCounts = commentCounts;
        ViewCounts = viewCounts;
        this.weight = weight;
        this.createDate = createDate;
        this.author = author;
        this.body = body;
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", commentCounts=" + commentCounts +
                ", ViewCounts=" + ViewCounts +
                ", weight=" + weight +
                ", createDate='" + createDate + '\'' +
                ", author='" + author + '\'' +
                ", body='" + body + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
