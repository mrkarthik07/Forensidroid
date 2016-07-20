package monitor.code.reechee88.filemonitorapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reechee88 on 2016. 05. 09..
 */
public class CopyFileCommand extends ExecuteAsRootBase {

    public CopyFileCommand(List<String> filesToCopy, String copyLoc)
    {
        myFilesForCopy = filesToCopy;
        this.copyFileLocation = copyLoc;
    }

    @Override
    protected ArrayList<String> getCommandsToExecute() {
        ArrayList<String> commands = new ArrayList<>();

        for (String file : myFilesForCopy)
        {
            if(file.contains(copyFileLocation))
            {
                continue;
            }

            String[] splitted_path = file.split("/");
            String copyPath = copyFileLocation + "/" + splitted_path[splitted_path.length - 1];
            String copyCommand = String.format("cp %1$s %2$s", file, copyPath);

            commands.add(copyCommand);
        }

        return commands;
    }

    private List<String> myFilesForCopy;
    private String copyFileLocation;
}
