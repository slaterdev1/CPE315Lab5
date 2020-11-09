import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.System.exit;

public class SimulatorInterface
{
    private String[] args;
    public boolean scriptMode = false;
    public boolean running = true;

    public SimulatorInterface(String[] args){
        this.args = args;
    }
    
    public void simulate()
    {
        FileParser fp = new FileParser(args);
        fp.run();
       
        if(args.length == 1){
            BranchPredictor.initialize(2);
        }
        else if(args.length == 2){
            if(args[1].equals("2") || args[1].equals("4") || args[1].equals("8"))   
                BranchPredictor.initialize(Integer.parseInt(args[1]));
            else
            {
                BranchPredictor.initialize(2);
            }
        }
        else if(args.length == 3){
            if(args[2].equals("2") || args[2].equals("4") || args[2].equals("8"))   
                BranchPredictor.initialize(Integer.parseInt(args[2]));
            else
            {
                BranchPredictor.initialize(2);
            }
        }
        Scanner cmd_o = getScanner(args);
        if (cmd_o == null){
            System.out.println("Usage: java lab3 filename.asm [scriptFile]");
            exit(-1);
        }

        String cmdLine;
        List<String> stepArgs;
        CommandHandler handler = new CommandHandler(this);

        while(running) //running will be set to false by handler handling "q"
        {
            System.out.print("mips> ");
            cmdLine = cmd_o.nextLine();
            if(scriptMode) System.out.println(cmdLine);
            stepArgs = Arrays.asList(cmdLine.split(" "));
            handler.setArgs(stepArgs);
            handler.handleCommand();
        }

    }

    private Scanner getScanner(String[] args){
        if(args.length == 1){
            scriptMode = false;
            return new Scanner(System.in);
        }
        else if (args.length == 2) {
            if(args[1].equals("2") || args[1].equals("4") || args[1].equals("8")){
                scriptMode = false;
                return new Scanner(System.in);
            }
            else{
                scriptMode = true;
            
                try {
                    File file = new File(args[1]);
                    return new Scanner(file);
                } catch (FileNotFoundException e){
                    System.out.println("Error creating scanner: " + e);
                    exit(-1);
                }
            }
        }
        else if (args.length == 3) {
            scriptMode = true;
            try {
                File file = new File(args[1]);
                return new Scanner(file);
            } catch (FileNotFoundException e){
                System.out.println("Error creating scanner: " + e);
                exit(-1);
            }
        }
        return null;
    }

}
