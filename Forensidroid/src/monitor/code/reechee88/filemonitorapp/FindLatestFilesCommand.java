package monitor.code.reechee88.filemonitorapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Reechee88 on 2016. 05. 09..
 */
public class FindLatestFilesCommand extends ExecuteAsRootBase {

    public FindLatestFilesCommand(String dataDirectory)
    {
        locationOfBusyBox = dataDirectory + File.separator + "busybox";
    }

    @Override
    protected ArrayList<String> getCommandsToExecute() {
        ArrayList<String> commnds = new ArrayList<>();

        String findCommand = String.format(".%s find /storage/emulated/0/ -type f -mmin -1", locationOfBusyBox);

        commnds.add(findCommand);

        return commnds;
    }

    public List<String> executeWithResponse() {
        List<String> response = new ArrayList<>();

        try
        {
            ArrayList<String> commands = getCommandsToExecute();

            if (null != commands && commands.size() > 0)
            {
                ProcessBuilder processBuilder = new ProcessBuilder();
                processBuilder.redirectErrorStream(true);
                processBuilder.command("su");

                Process suProcess = processBuilder.start();

                DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
                DataInputStream is = new DataInputStream(suProcess.getInputStream());

                // Execute commands that require root access
                for (String currCommand : commands)
                {
                    os.writeBytes(currCommand + "\n");
                    os.flush();

                    final BufferedReader reader = new BufferedReader(
                            new InputStreamReader(is));

                    Log.i("ROOT", "FindLatestFilesCommand - executeWithResponse() - Try to read.");
                    Thread.sleep(1000);
                    while (reader.ready()) {
                        Log.i("ROOT", "FindLatestFilesCommand - executeWithResponse() - Reading...");
                        response.add(reader.readLine());
                    }
                    Log.i("ROOT", "FindLatestFilesCommand - executeWithResponse() - Every line has been read.");

                    reader.close();
                }

                os.writeBytes("exit\n");
                os.flush();

                try
                {
                    int suProcessRetval = suProcess.waitFor();
                }
                catch (Exception ex)
                {
                    Log.e("ROOT", "Error executing root action", ex);
                }
            }
        }
        catch (IOException ex)
        {
            Log.w("ROOT", "Can't get root access", ex);
        }
        catch (SecurityException ex)
        {
            Log.w("ROOT", "Can't get root access", ex);
        }
        catch (Exception ex)
        {
            Log.w("ROOT", "Error executing internal operation", ex);
        }

        return response;
    }

    private String locationOfBusyBox;
}
