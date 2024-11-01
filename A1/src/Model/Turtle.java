package Model;

public class Turtle implements IEntity {
    private double age;
    private String name;

    public Turtle(double age, String name) {
        this.age = age;
        this.name = name;
    }

    public void setAge(int a) {
        age = a;
    }

    public double getAge() {
        return age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String n) {
        this.name = n;
    }

    public String toString() {
        return "Turtle: " + name + " is " + age + " years old";
    }

    public boolean equals(Object o) {
        if (o instanceof Turtle) {
            Turtle t = (Turtle) o;
            return this.age == t.age && this.name.equals(t.name);
        }
        return false;
    }

    public double Age() {
        return this.age;
    }
}
