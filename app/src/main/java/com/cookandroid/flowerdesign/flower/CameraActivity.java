package com.cookandroid.flowerdesign.flower;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cookandroid.flowerdesign.R;
import com.cookandroid.flowerdesign.ml.ModelUnquant;
import com.cookandroid.flowerdesign.model.UserStore;
import com.cookandroid.flowerdesign.user.SessionManager;
import com.cookandroid.flowerdesign.utils.Apis;
import com.cookandroid.flowerdesign.utils.Service;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CameraActivity extends AppCompatActivity {
    private static final String TAG = "CameraActivity";
    LinearLayout gradient;
    TextView result, confidence;
    ImageView imageView;
    CardView cameraCardView;
    Button capture, select, addStore;
    int imageSize = 224;
    Uri imageuri;
    Bitmap bitmap;

    Service service;
    String flowername;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();

        getPermission();

        sessionManager=new SessionManager(getApplicationContext());

        LinearLayout linearLayout=findViewById(R.id.gradient);
        AnimationDrawable animationDrawable=(AnimationDrawable)linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        imageView = findViewById(R.id.imageView);
        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        capture = findViewById(R.id.capture);
        select = findViewById(R.id.select);
        gradient = findViewById(R.id.gradient);
        addStore = findViewById(R.id.addStore);
        cameraCardView = findViewById(R.id.cameraCardView);

        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });
        addStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sessionManager.getLogin() == false){
                    Toast.makeText(CameraActivity.this, "로그인 해주세요.",
                            Toast.LENGTH_SHORT).show();
                }else{
                    addStore(flowername, sessionManager.getUsername());
                }
            }
        });
    }

    public void addStore(String flowername, String userid){
        service=Apis.getPersonaService();
        Call<UserStore> call=service.storeSave(flowername, userid);
        call.enqueue(new Callback<UserStore>() {
            @Override
            public void onResponse(Call<UserStore> call, Response<UserStore> response) {
                if(response.isSuccessful()) {
                    final UserStore msg = response.body();
                    if (msg.getMsg() == 2) {
                        Toast.makeText(CameraActivity.this, "이미 보관함에 존재합니다.",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(CameraActivity.this, "보관함에 저장되었습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<UserStore> call, Throwable t) {
                Log.e("Error:",t.getMessage());
            }
        });
    }

    private void openCamera() {
        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"new image");
        values.put(MediaStore.Images.Media.DESCRIPTION,"From the Camera");
        imageuri=getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
        startActivityForResult(camIntent,12);
    }

    private void getPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CameraActivity.this, new String[]{
                        Manifest.permission.CAMERA
                }, 11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==11){
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
    }

    public void classifyImage(Bitmap image){
        try {
            ModelUnquant model = ModelUnquant.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);

            ByteBuffer byteBuffer=ByteBuffer.allocateDirect(4*imageSize*imageSize*3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0,
                    image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            ModelUnquant.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            String[] classes={"개나리","목련꽃","무궁화","민들레","장미","진달래","해바라기","튤립"};
            String re="";
            re= String.format("%s(%.1f%%)\n",classes[maxPos],confidences[maxPos] * 100);
            result.setText(re);
            flowername=classes[maxPos];

            String con="";
            for(int i=0; i<2; i++){
                con += String.format("%s(%.1f%%)\n",classes[i],confidences[i] * 100);
            }
            confidence.setText(con);

            capture.setVisibility(View.GONE);
            select.setVisibility(View.GONE);
            cameraCardView.setVisibility(View.VISIBLE);
            result.setVisibility(View.VISIBLE);
            confidence.setVisibility(View.VISIBLE);
            addStore.setVisibility(View.VISIBLE);
            gradient.setBackgroundColor(Color.parseColor("#ffffff"));

            ActionBar actionBar=getSupportActionBar();
            actionBar.show();

            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10){
            if(data !=null){
                Uri uri=data.getData();
                try {
                    bitmap= MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                    int dimension=Math.min(bitmap.getWidth(),bitmap.getHeight());
                    bitmap= ThumbnailUtils.extractThumbnail(bitmap,dimension,dimension);
                    imageView.setImageBitmap(bitmap);

                    bitmap=Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);
                    classifyImage(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if(requestCode==12){
            try {
                bitmap=MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                        imageuri);
                int dimension=Math.min(bitmap.getWidth(),bitmap.getHeight());
                bitmap= ThumbnailUtils.extractThumbnail(bitmap,dimension,dimension);
                imageView.setImageBitmap(bitmap);

                bitmap=Bitmap.createScaledBitmap(bitmap,imageSize,imageSize,false);
                classifyImage(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}