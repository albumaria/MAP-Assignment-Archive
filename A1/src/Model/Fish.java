package Model;

public class Fish implements IEntity{
    private double age;
    private String name;

    public Fish(double age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setAge(int a) {
        age = a;
    }

    public double getAge() {
        return this.age;
    }

    public String getName() {
        return name;
    }

    public void setName(String s) {
        name = s;
    }

    public String toString() {
        return "Turtle: " + name + " is " + age + " years old";
    }

    public boolean equals(Object o) {
        if (!(o instanceof Fish))
            return false;

        Fish fish = (Fish) o;
        return this.age == fish.getAge() && fish.getName().equals(this.name);
    }

    public double Age() {
        return this.age;
    }

}
