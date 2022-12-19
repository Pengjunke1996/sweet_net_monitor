package com.sweet.sign;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UploadClient {

    public static void uploadSign(String input, String output, String url) throws Exception {
        File fileInput = new File(input);
        if (!fileInput.exists()) {
            throw new Exception("input file does not exist");
        }
        File fileOutput = new File(output);
        if (fileInput.exists()) {
            fileOutput.delete();
            fileInput.createNewFile();
        }
        uploadSign(fileInput, fileOutput, url);
    }

    public static void uploadSign(File file, File outFile, String url) throws IOException {
        HashMap<String, Object> pMap = new HashMap<String, Object>();
        pMap.put("file", file);
        RequestBody body = buildMultipartBody(pMap);
        FileRequestBody fileRequestBody = new FileRequestBody(body, new ProgressCallback() {
            @Override
            public void onLoading(long total, long progress) {
                System.out.println(total + "  " + progress);
            }
        });
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(130, TimeUnit.SECONDS)
                .writeTimeout(130, TimeUnit.SECONDS)
                .build();

        Request req = new Request.Builder().url(url).post(fileRequestBody).build();
        Response response = okHttpClient.newCall(req).execute();
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        fileOutputStream.write(Objects.requireNonNull(response.body()).bytes());
        fileOutputStream.flush();
        fileOutputStream.close();
        System.out.println("sign successful");
    }

    private static MultipartBody buildMultipartBody(Map<String, Object> pMap) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        Set<String> keySet = pMap.keySet();
        for (String key : keySet) {
            Object valueOut = pMap.get(key);
            if (valueOut instanceof File) {
                File value = (File) valueOut;
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), value);
                String encode = URLEncoder.encode(value.getName());
                builder.addFormDataPart(key, encode, requestBody);
            } else if (valueOut instanceof String) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), (String) valueOut);
                builder.addFormDataPart(key, null, requestBody);
            } else if (valueOut instanceof Integer) {
                builder.addFormDataPart(key, valueOut.toString());
            } else {
                if (valueOut == null) {
//                    builder.addFormDataPart(key, "")
                } else {
                    builder.addFormDataPart(key, valueOut.toString());
                }
            }
        }
        return builder.build();
    }

    public static void main(String[] args) throws Exception {
        //uploadSign("/Users/pjk/Downloads/1.apk","/Users/pjk/Downloads/2.apk", "http://172.31.32.249:8161/appSign/add?product=talktalk");
        String inputPath = args[0];
        String outputPath = args[1];
        String uploadUrl = args[2];
        uploadSign(inputPath, outputPath, uploadUrl);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}

