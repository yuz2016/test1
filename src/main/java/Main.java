public class Main {

    public static void main(String[] args) {
        Command command;
        if(args.length<2){
            System.err.println("Usage : java command ... file...");
            return;
        }
        switch (args[0]){
            case "Cat":
                command = new Cat();
                break;
            case "Grep":
                command = new Grep();
                break;
            default:
                System.out.println("this command is todo");return;
        }
        command.process(args);
    }
}
