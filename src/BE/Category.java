package BE;

public class Category {
    private String name;
    private int id;

    public Category(int id, String name){
        this.id = id;
        setName(name);
    }

    public String getName(){
        return name;
    }

    public int getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
