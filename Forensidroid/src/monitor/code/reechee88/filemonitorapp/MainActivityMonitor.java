package monitor.code.reechee88.filemonitorapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.util.Log;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AppKeyPair;
import com.example.badone.R;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainActivityMonitor extends Activity {

    public MainActivityMonitor() {
        this.filesSaved = new HashMap<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);

        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        String accesToken = sharedPref.getString(getString(R.string.preference_file_key), "");
        mDBApi = new DropboxAPI<>(session);

        if (mWifi.isConnected()) {
            if(accesToken.equals(""))
            {
                mDBApi.getSession().startOAuth2Authentication(MainActivityMonitor.this);
            }
            else
            {
                mDBApi.getSession().setOAuth2AccessToken(accesToken);
            }
        }

        dataDirectory = Environment.getDataDirectory() + "/data/" + this.getPackageName() + "/files/";
        ActivityCompat.requestPermissions(MainActivityMonitor.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

        if (ExecuteAsRootBase.canRunRootCommands()) {

            if (!isBusyBoxExists()) {
                copyBusyBoxIfNotExists();
            }

            final FindLatestFilesCommand findLatestFilesCommand = new FindLatestFilesCommand(dataDirectory);

            Log.i("ROOT", "Running as root.");

            MakeDirCommand makeDirCommand = new MakeDirCommand(copyFileLocation);
            makeDirCommand.execute();

            final GetMD5ForFileCommand getMD5ForFileCommand = new GetMD5ForFileCommand();

            ScheduledExecutorService scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    Log.i("ROOT", "Another step in.");
                    List<String> filesModified = findLatestFilesCommand.executeWithResponse();

                    List<String> filesThatMustCopy = new ArrayList<>();
                    synchronized (lockObject) {
                        for (String file : filesModified) {
                            String md5OfFile = getMD5ForFileCommand.executeForMD5(file);

                            Log.i("ROOT", "synchronized - Write Dictionary - enter");
                            if (!filesSaved.values().contains(md5OfFile)) {
                                Log.i("ROOT", "synchronized - Write Dictionary - put");
                                filesSaved.put(file, md5OfFile);
                                filesThatMustCopy.add(file);
                            }
                            Log.i("ROOT", "synchronized - Write Dictionary - leaving");
                        }
                    }
                    Log.i("ROOT", "CopyFileCommand - execute() - enter");
                    CopyFileCommand copyFileCommand = new CopyFileCommand(filesThatMustCopy, copyFileLocation);
                    copyFileCommand.execute();
                    Log.i("ROOT", "CopyFileCommand - execute() - leaving");

                    if (mWifi.isConnected() && mDBApi.getSession().isLinked()) {
                        List<File> filesInTmpDir = getListFiles(new File(copyFileLocation));

                        for(File file : filesInTmpDir)
                        {
                            FileInputStream inputStream = null;
                            try {
                                inputStream = new FileInputStream(file);
                                DropboxAPI.Entry response = mDBApi.putFile("/" + file.getName(), inputStream, file.length(), null, null);
                            } catch (FileNotFoundException e) {
                                Log.e("ROOT", "File was not found: " + e.getMessage());
                            } catch (DropboxException de)
                            {
                                Log.e("ROOT", "DropBox error: " + de.getMessage());
                            }
                        }

                        Log.i("ROOT", "CopyFileCommand - Dropbox API - siccessful Auth");
                    }
                }
            }, 0, 2, TimeUnit.SECONDS);

            scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    synchronized (lockObject) {
                        filesSaved.clear();
                    }
                }
            }, 0, 2, TimeUnit.MINUTES);
        } else {
            Log.i("ROOT", "Cannot run as root.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if(mWifi.isAvailable()) {
            AndroidAuthSession session = mDBApi.getSession();
            if (session.authenticationSuccessful()) {
                try {
                    // Required to complete auth, sets the access token on the session
                    session.finishAuthentication();

                    String authTokenDropBox = mDBApi.getSession().getOAuth2AccessToken();

                    SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preference_file_key), authTokenDropBox);
                    editor.commit();
                } catch (IllegalStateException e) {
                    Log.i("DbAuthLog", "Error authenticating", e);
                }
            }
        }
    }

    private void copyBusyBoxIfNotExists() {
        try {
            InputStream is = getAssets().open("busybox");
            File newfile = new File(getFilesDir() + File.separator + "busybox");
            newfile.createNewFile();

            BufferedOutputStream fout = new BufferedOutputStream(new FileOutputStream(newfile), 1024);

            int size = 0;
            byte[] buffer = new byte[1024];
            while ((size = is.read(buffer, 0, 1024)) >= 0) {
                fout.write(buffer, 0, size);
            }

            is.close();
            fout.close();

            ChmodCommand chmodCommand = new ChmodCommand(newfile.getPath());
            chmodCommand.execute();

            CopyFileCommand copyFileCommand = new CopyFileCommand(
                    new ArrayList<>(Arrays.asList(newfile.getPath())),
                    dataDirectory + File.separator + "busybox");
            copyFileCommand.execute();
        } catch (IOException e) {
            Log.i("ROOT", "Failed to create busybox for application.");
        }
    }

    private boolean isBusyBoxExists() {
        File busybox = new File(dataDirectory + File.separator + "busybox");

        return busybox.exists();
    }

    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    //Stores filename - md5 pair for identification
    private final Map<String, String> filesSaved;
    private String dataDirectory;
    protected final String copyFileLocation = "/storage/emulated/0/copyLocation";
    private Object lockObject = new Object();

    final static private String APP_KEY = "hdo404rwj0xs072";
    final static private String APP_SECRET = "agidpdydqcc4t6f";

    // In the class declaration section:
    private DropboxAPI<AndroidAuthSession> mDBApi;
}
