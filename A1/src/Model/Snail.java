package Model;

public class Snail implements IEntity {
    private double age;
    private String name;

    public Snail(double age, String name) {
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
        return "Snail: " + name + " is " + age + " years old";
    }

    public boolean equals(Object o) {
        if (!(o instanceof Snail))
            return false;

        Snail snail = (Snail) o;
        return this.age == snail.getAge() && snail.getName().equals(this.name);
    }

    public double Age() {
        return this.age;
    }

}
