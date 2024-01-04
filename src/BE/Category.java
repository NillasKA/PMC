package BE;

public class Category {
    private String name;
    private int id;

    /**
     * Creation of a category. Simple.
     * @param id - Set this to -1 as default. DB will figure it out.
     * @param name - Name of the category. "Action", "Horror" etc.
     */
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
