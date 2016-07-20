package monitor.code.reechee88.filemonitorapp;

import java.util.ArrayList;

/**
 * Created by richard.forgacs on 5/11/2016.
 */
public class ChmodCommand extends ExecuteAsRootBase {

    public ChmodCommand(String filePath)
    {
        this.filePathForChangePermission = filePath;
    }

    @Override
    protected ArrayList<String> getCommandsToExecute() {
        ArrayList<String> commands = new ArrayList<>();
        commands.add("chmod 755 " + filePathForChangePermission);

        return commands;
    }

    private String filePathForChangePermission;
}
