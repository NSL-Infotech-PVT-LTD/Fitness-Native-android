package com.netscape.utrain.utils;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileUtil {

    private String TAG = this.getClass().getSimpleName();
    private static final String folderName = Environment.getExternalStorageDirectory() + "/" + Constants.APP_FOLDER_NAME + "/";

    public FileUtil() {
        generateDefaultFile();
    }

    public static File folderName() {
        File file = new File(folderName);
        if (!file.exists())
            file.mkdir();
        return file;
    }

    public static String getBaseFolderName() {
        return folderName;
    }

    public static File getFile(String path) {
        return new File(path);
    }

    private static String getPng(String path) {
        return path + ".png";
    }

    public boolean deleteFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            File file = new File(path);
            if (file.exists()) {
                return file.delete();
            } else {
                file = new File(folderName(), path);
                if (file.exists()) {
                    return file.delete();
                }
            }
        }
        return false;
    }

    public void deleteFile(List<String> paths) {
        for (String path : paths) {
            if (path != null) {
                File file = new File(folderName(), path);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
    }

    public static String generateFileName(String packageName) {
        return getPng(packageName);
    }

    public File generateFile(String path, String fileExtension) {
        File file = new File(folderName, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                file.mkdir();
            }
        }
        return new File(folderName(), path + "." + fileExtension);
    }

    private void generateDefaultFile() {
        File file = new File(folderName);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public File generateZipFile(String name) {
        File file = new File(folderName, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                file.mkdir();
            }
        }
        return new File(folderName(), name + ".zip");
    }

    public File generateFolder(String name) {
        File file = new File(folderName);
        if (!file.exists()) {
            file.mkdir();
        }
        File folderName = new File(file, name);
        if (!folderName.exists()) {
            folderName.mkdirs();
        }
        return folderName;
    }

    public static String getCacheFile(Context context, String packageName) {
        return context.getCacheDir().getPath() + "/" + generateFileName(packageName);
    }

    public static boolean exists(String path) {
        return getFile(path).exists();
    }

    public static File getOutputMediaFile() {
        File mediaFile = null;
        try {
            File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), Constants.APP_FOLDER_NAME);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory");
                    return null;
                }
            }

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mediaFile;
    }

    public static File saveFileToDirectory(Context context, String dirName, String fileName, byte[] byteData, boolean preferExternal) {
        try {
            if (byteData == null)
                return null;

            if (fileName == null)
                return null;

            File dir;
            if (dirName != null)
                dir = new File(folderName, dirName);
            else
                dir = new File(folderName);
            if (!dir.exists()) {
                Log.v("Directory \"", dirName + "\" not found");
                if (!dir.mkdirs())
                    throw new FileNotFoundException("Could not able to create directory: " + dirName);
            }

            File file = new File(dir, fileName);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(byteData);
            outputStream.flush();
            outputStream.close();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static File createFile(Context context, String dirName, String fileName) {
        try {
            if (fileName == null)
                return null;

            File dir;
            if (dirName != null)
                dir = new File(folderName, dirName);
            else
                dir = new File(folderName);
            if (!dir.exists()) {
                Log.v("Directory \"", dirName + "\" not found");
                if (!dir.mkdirs())
                    throw new FileNotFoundException("Could not able to create directory: " + dirName);
            }
            File file = new File(dir, fileName);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
