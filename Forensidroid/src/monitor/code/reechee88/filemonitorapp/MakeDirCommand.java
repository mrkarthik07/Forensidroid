package monitor.code.reechee88.filemonitorapp;

import java.util.ArrayList;

/**
 * Created by Reechee88 on 2016. 05. 09..
 */
public class MakeDirCommand extends ExecuteAsRootBase {

    public MakeDirCommand(String copyLoc)
    {
        this.copyFileLocation = copyLoc;
    }

    @Override
    protected ArrayList<String> getCommandsToExecute() {
        ArrayList<String> command = new ArrayList<>();
        command.add("mkdir " + copyFileLocation);

        return command;
    }

    private String copyFileLocation;
}
