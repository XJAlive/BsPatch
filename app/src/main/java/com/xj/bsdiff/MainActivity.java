package com.xj.bsdiff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {


    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    1);
        }
    }


    public void merge(View view) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String oldPath = Environment.getExternalStorageDirectory().getPath() + "/xj/package.zip";
                String patchPath = Environment.getExternalStorageDirectory().getPath() + "/xj/patch.zip";
                String mergePath = Environment.getExternalStorageDirectory().getPath() + "/xj/new.zip";


                Log.i("xj","旧文件存在：" + new File(oldPath).exists());
                Log.i("xj","patch文件存在：" + new File(patchPath).exists());

                int status = -1;
                try {
                    status = PatchUtils.getInstance().patch(oldPath, mergePath, patchPath);
                } catch (Throwable ignore) {
                    Log.e("xj ", ignore.getMessage());
                }
                if (status == 0) {
                    Log.i("xj", "文件合并成功");
                } else {
                    Log.e("xj", "文件合并失败");
                }
            }
        }).start();


    }

}
