package monitor.code.reechee88.filemonitorapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by richard.forgacs on 5/11/2016.
 */
public class GetMD5ForFileCommand {

    public String executeForMD5(String filePath) {
        String md5OfFile = null;

        try {
            Log.i("ROOT", "GetMD5ForFileCommand - executeForMD5() - enter");
            String command = "md5sum " + filePath;

            Process suProcess = Runtime.getRuntime().exec("su");

            DataOutputStream os = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream is = new DataInputStream(suProcess.getInputStream());

            os.writeBytes(command + "\n");
            os.flush();

            final BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line = reader.readLine();

            md5OfFile = line.split(" ")[0];

            reader.close();

            os.writeBytes("exit\n");
            os.flush();

            try {
                int suProcessRetval = suProcess.waitFor();
            } catch (Exception ex) {
                Log.e("ROOT", "Error executing root action", ex);
            }

            Log.i("ROOT", "GetMD5ForFileCommand - executeForMD5() - leave");
        } catch (IOException ex) {
            Log.w("ROOT", "Can't get root access", ex);
        } catch (SecurityException ex) {
            Log.w("ROOT", "Can't get root access", ex);
        } catch (Exception ex) {
            Log.w("ROOT", "Error executing internal operation", ex);
        }

        return md5OfFile;
    }
}
