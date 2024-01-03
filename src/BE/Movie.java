package BE;

public class Movie {
    private String name;
    private String filelink;
    private String lastview;
    private double rating;
    private int id;

    public Movie(int id, String name, double rating, String filelink, String lastview){
        this.id = id;
        setName(name);
        setRating(rating);
        setFilelink(filelink);
        setLastview(lastview);
    }

    public String getName(){
        return name;
    }

    public double getRating() {
        return rating;
    }

    public String getFilelink() {
        return filelink;
    }

    public String getLastview() {
        return lastview;
    }

    public int getId(){
        return id;
    }

    //TODO Implement safety measure
    public void setName(String name){
        this.name = name;
    }

    //TODO Implement safety measure
    private void setLastview(String lastview) {
        this.lastview = lastview;
    }

    //TODO Implement safety measure
    private void setFilelink(String filelink) {
        this.filelink = filelink;
    }

    //TODO Implement safety measure
    private void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", filelink='" + filelink + '\'' +
                ", lastview='" + lastview + '\'' +
                ", rating=" + rating +
                ", id=" + id +
                '}';
    }
}
