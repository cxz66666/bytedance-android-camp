package com.bytedance.practice5;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bytedance.practice5.model.MessageListResponse;
import com.bytedance.practice5.model.UploadResponse;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static okhttp3.MultipartBody.*;

public class UploadActivity extends AppCompatActivity {
    private static final String TAG = "chapter5";
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
    private static final int REQUEST_CODE_COVER_IMAGE = 101;
    private static final String COVER_IMAGE_TYPE = "image/*";

    private static Retrofit retrofit;
    private IApi api;
    private Uri coverImageUri;
    private SimpleDraweeView coverSD;
    private EditText toEditText;
    private EditText contentEditText ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNetwork();
        setContentView(R.layout.activity_upload);
        coverSD = findViewById(R.id.sd_cover);
        toEditText = findViewById(R.id.et_to);
        contentEditText = findViewById(R.id.et_content);
        findViewById(R.id.btn_cover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFile(REQUEST_CODE_COVER_IMAGE, COVER_IMAGE_TYPE, "????????????");
            }
        });


        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitMessageWithURLConnection();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (REQUEST_CODE_COVER_IMAGE == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                coverImageUri = data.getData();
                coverSD.setImageURI(coverImageUri);

                if (coverImageUri != null) {
                    Log.d(TAG, "pick cover image " + coverImageUri.toString());
                } else {
                    Log.d(TAG, "uri2File fail " + data.getData());
                }

            } else {
                Log.d(TAG, "file pick fail");
            }
        }
    }

    private void initNetwork() {
        // 3
        // ??????Retrofit??????
        // ??????api??????
        retrofit=new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    private void getFile(int requestCode, String type, String title) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(type);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, requestCode);
    }

    private void submit() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(this, "???????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(this, "?????????TA?????????", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "??????????????????TA?????????", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        // 5
        // ??????api.submitMessage()??????????????????
        // ???????????????????????????activity???????????????toast
        IApi iApi=retrofit.create(IApi.class);
//        RequestBody fromBody =RequestBody.create(MediaType.parse("text/plain"), Constants.USER_NAME);
//        RequestBody toBody =RequestBody.create(MediaType.parse("text/plain"), to);
//        RequestBody contentBody =RequestBody.create(MediaType.parse("text/plain"), content);

        Log.d(TAG,Constants.USER_NAME+to+content+coverImageData.length);

        Part fromPart=Part.createFormData("from",Constants.USER_NAME);
        Part toPart=Part.createFormData("to",to);
        Part contentPart=Part.createFormData("content",content);
        Part coverPart = Part.createFormData("image",
                "test1.png",
                RequestBody.create(MediaType.parse("multipart/form-data"),
                        coverImageData));

        Call<UploadResponse>call= iApi.submitMessage(Constants.STUDENT_ID,"",fromPart,toPart,contentPart,coverPart,Constants.token);
        newThreadToSubmit(call); //????????????????????????submit
    }


    //????????????????????????Submit
    private void newThreadToSubmit(Call<UploadResponse>call){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<UploadResponse>response=call.execute();
                    Log.d(TAG,response.toString());
                    if(response.isSuccessful()&&response.body() != null){
                        finish();//??????activity
                    }
                } catch (IOException e){
                    Log.e(TAG,e.getMessage());
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UploadActivity.this,"????????????QAQ"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }



    //  7 ?????? ???URLConnection?????????????????????(??????)
    private void submitMessageWithURLConnection() {
        byte[] coverImageData = readDataFromUri(coverImageUri);
        if (coverImageData == null || coverImageData.length == 0) {
            Toast.makeText(UploadActivity.this, "???????????????", Toast.LENGTH_SHORT).show();
            return;
        }
        String to = toEditText.getText().toString();
        if (TextUtils.isEmpty(to)) {
            Toast.makeText(UploadActivity.this, "?????????TA?????????", Toast.LENGTH_SHORT).show();
            return;
        }
        String content = contentEditText.getText().toString();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(UploadActivity.this, "??????????????????TA?????????", Toast.LENGTH_SHORT).show();
            return;
        }

        if ( coverImageData.length >= MAX_FILE_SIZE) {
            Toast.makeText(UploadActivity.this, "????????????", Toast.LENGTH_SHORT).show();
            return;
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                String end = "\r\n";
                String trivalBoundary="WebKitFormBoundary7MA4YWxkTrZu0gW";
                String boundary="--WebKitFormBoundary7MA4YWxkTrZu0gW";
                DataOutputStream ds = null;
                UploadResponse result=null;


                String urlStr = String.format("https://api-android-camp.bytedance.com/zju/invoke/messages?student_id=%s&extra_value=%s",
                        Constants.STUDENT_ID, "\"\"");
                try {
                    URL url=new URL(urlStr);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                    httpURLConnection.setDoInput(true);//input from httpUrlConnect, default is true
                    httpURLConnection.setDoOutput(true);//output to httpUrlConnection
                    httpURLConnection.setUseCaches(false);//POST can't use cache
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                    httpURLConnection.setRequestProperty("Charset", "UTF-8");
                    httpURLConnection.setRequestProperty("token",Constants.token);

                    httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary="+trivalBoundary);


                    ds = new DataOutputStream(httpURLConnection.getOutputStream());
                    ds.writeBytes( boundary  + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"from\"");
                    ds.writeBytes(end+end);
                    ds.writeBytes(Constants.USER_NAME);
                    ds.writeBytes( end+boundary  + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"to\"");
                    ds.writeBytes(end+end);
                    ds.writeBytes(to);
                    ds.writeBytes( end+boundary  + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"content\"");
                    ds.writeBytes(end+end);
                    ds.writeBytes(content);
                    ds.writeBytes( end+boundary  + end);
                    ds.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=test.jpg");
                    ds.writeBytes(end);
                    ds.writeBytes("Content-Type: image/jpeg" +end+end);

                    ds.write(coverImageData);
                    ds.writeBytes(end+boundary+"--"+end);

                    ds.flush();

                    if (httpURLConnection.getResponseCode() == 200){
                        InputStream in = httpURLConnection.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                        result = new Gson().fromJson(br, new TypeToken<UploadResponse>() {
                        }.getType());
                        br.close();
                        in.close();
                    } else{
                        throw new  IOException("???????????????QAQ");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(UploadActivity.this,"????????????"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                if(result!=null&&result.success){
                   UploadActivity.this.finish();
                }
                return;
            }
        }).start();
        return;
    }


    private byte[] readDataFromUri(Uri uri) {
        byte[] data = null;
        try {
            InputStream is = getContentResolver().openInputStream(uri);
            data = Util.inputStream2bytes(is);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


}
