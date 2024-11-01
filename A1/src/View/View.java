package View;
//8. Intr-un acvariu traiesc pesti si broaste testoase.
//Sa se afiseze toate vietuitoarele din acvariu care sunt
//mai batrine de 1 an.

import Controller.*;
import Model.*;
import Repository.*;

import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        IRepo repository = new Repository(100);
        Controller controller = new Controller(repository);

        Turtle t1 = new Turtle(20, "Gerald");
        Turtle t2 = new Turtle(0.4, "Kiko");
        Turtle t3 = new Turtle(1.2, "Berd");

        Fish f1 = new Fish(4, "Bozo");
        Fish f2 = new Fish(0.5, "Fishy");
        Fish f3 = new Fish(1.2, "Ponyo");

        Snail s1 = new Snail(0.2, "Mucus");
        Snail s2 = new Snail(1, "Gertrude");
        Snail s3 = new Snail(9, "Bertha");

        controller.writeEntity(t1);
        controller.writeEntity(t2);
        controller.writeEntity(t3);
        controller.writeEntity(f1);
        controller.writeEntity(f2);
        controller.writeEntity(f3);
        controller.writeEntity(s1);
        controller.writeEntity(s2);
        controller.writeEntity(s3);

        controller.removeEntity(t1);
        //controller.removeEntity(s3);

        IEntity[] arr = controller.olderThanOne();
        for (IEntity e : arr) {
            if (e != null) {
                System.out.println(e);
            }
        }


    }
}
