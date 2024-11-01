package view.commands;

public class ExitCommand extends Command {
    public ExitCommand(String key, String desc){
        super(key, desc);
    }

    public void execute() {
        System.exit(0);
    }
}
