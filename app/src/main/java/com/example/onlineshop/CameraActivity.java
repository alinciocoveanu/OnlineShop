package com.example.onlineshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    Camera camera;
    SurfaceView cameraSurfaceView;
    SurfaceHolder cameraSurfaceHolder;
    boolean cameraCondition = false;
    Button takePhoto;

    Camera.PictureCallback cameraPictureCallback;

    private static final int REQUEST_CAMERA_PERMISSIONS = 100;
    private static String[] PERMISSIONS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        cameraSurfaceView = findViewById(R.id.camera_preview);
        takePhoto = findViewById(R.id.take_picture);

        cameraSurfaceHolder = cameraSurfaceView.getHolder();

        cameraSurfaceHolder.addCallback(this);
        cameraSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        cameraPictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                camera.stopPreview();

                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                Bitmap cbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), null, true);

                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

                File outputFile = new File(directory, "/" + System.currentTimeMillis() + ".jpg");

                FileOutputStream fileOutputStream = null;
                getCameraPermission();

                try {
                    fileOutputStream = new FileOutputStream(outputFile);
                    cbmp.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                camera.startPreview();
            }
        };

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(null, null, null, cameraPictureCallback);
            }
        });
    }

    private void getCameraPermission() {
        int permission = PackageManager.PERMISSION_GRANTED;
        for (String i : PERMISSIONS) {
            permission = ActivityCompat.checkSelfPermission(CameraActivity.this, i);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                break;
            }
        }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CameraActivity.this, PERMISSIONS, REQUEST_CAMERA_PERMISSIONS);;
        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        getCameraPermission();
        try {
            camera = Camera.open();
            camera.setDisplayOrientation(90);
        } catch (Exception e) {
            Toast.makeText(CameraActivity.this, "Can't open camera!", Toast.LENGTH_SHORT).show();
        }

        try {
            camera.setPreviewDisplay(cameraSurfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera = null;
        cameraCondition = false;
    }
}
