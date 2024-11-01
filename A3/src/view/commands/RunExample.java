package view.commands;

import controller.Controller;

public class RunExample extends Command {
    private Controller controller;

    public RunExample(String key, String description, Controller ctr) {
        super(key, description);
        this.controller = ctr;
    }

    public void execute() {
        this.controller.allStep();

    }
}
