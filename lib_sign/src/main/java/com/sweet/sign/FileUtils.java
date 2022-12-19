package com.sweet.sign;


import com.sun.jndi.toolkit.url.Uri;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    private static final int EOF = -1;
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static File from(Uri uri) throws IOException {
        File tempFile = null;
        try {
            File imgFile = new File(uri.toString());
            InputStream inputStream = new FileInputStream(imgFile);
            String fileName = getFileName(uri);
            String[] splitName = splitFileName(fileName);
            tempFile = File.createTempFile(splitName[0] + System.currentTimeMillis(), splitName[1]);
            tempFile = rename(tempFile, imgFile.getAbsolutePath(), fileName);
            tempFile.deleteOnExit();
            FileOutputStream out = null;
            out = new FileOutputStream(tempFile);
            if (inputStream != null) {
                copy(inputStream, out);
                inputStream.close();
            }

            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tempFile;
    }

    private static String[] splitFileName(String fileName) {
        String name = fileName;
        String extension = "";
        int i = fileName.lastIndexOf(".");
        if (i != -1) {
            name = fileName.substring(0, i);
            extension = fileName.substring(i);
        }

        return new String[]{name, extension};
    }

    public static String getFileName(Uri uri) {
        String result = null;
        result = uri.getPath();
        int cut = result.lastIndexOf(File.separator);
        if (cut != -1) {
            result = result.substring(cut + 1);
        }
        return result;
    }

    private static File rename(File file, String parent, String newName) {
        File newFile = new File(parent, newName);
        if (!newFile.equals(file)) {
            if (newFile.exists() && newFile.delete()) {
                System.out.println("FileUtil " + "Delete old " + newName + " file");
            }
            if (file.renameTo(newFile)) {
                System.out.println("FileUtil " + "Rename file to " + newName);
            }
        }
        return newFile;
    }

    private static long copy(InputStream input, OutputStream output) throws IOException {
        long count = 0;
        int n;
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        while (EOF != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    /**
     * 写入文件
     *
     * @param append 是否追加
     */
    public static void writeToFile(File file, String str, boolean append) {
        if (file == null || str == null) {
            return;
        }
        FileWriter writer = null;
        try {
            if (file.exists() || file.createNewFile()) {
                writer = new FileWriter(file, append);
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
