package servlet;

public class tag {
    int id;
    String avatar;
    String tagName;
    public tag(int id,String avatar,String tagName){
        this.id=id;this.avatar=avatar;this.tagName=tagName;
    }

    @Override
    public String toString() {
        return "tag{" +
                "id=" + id +
                ", avatar='" + avatar + '\'' +
                ", tagName='" + tagName + '\'' +
                '}';
    }
}
