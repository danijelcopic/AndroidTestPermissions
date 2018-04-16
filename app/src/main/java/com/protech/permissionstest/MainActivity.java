package com.protech.permissionstest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static final int PERMISSION_REQUEST_SEND_SMS = 1;
    public static final int PERMISSION_REQUEST_CAMERA = 2;

    private View mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.main_layout);


        findViewById(R.id.button_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissionSendSms();

            }
        });



        findViewById(R.id.button_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkPermissionCamera();
            }
        });


    }


    //  checkPermission... SMS
    public void checkPermissionSendSms() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {

           // Snackbar.make(mLayout, R.string.send_sms_permission_available, Snackbar.LENGTH_LONG).show();

            sendSms(); // permission is already granted

        } else {

            requestPermissionSms(); //permission is missing and must be requested
        }

    }


    // checkPermission ... CAMERA
    public void checkPermissionCamera() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {

           // Snackbar.make(mLayout, R.string.camera_permission_available, Snackbar.LENGTH_LONG).show();

            startCamera(); // permission is already granted

        } else {

            requestPermissionCamera(); //permission is missing and must be requested
        }
    }


    // requestPermission ... SMS
    public void requestPermissionSms() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)) {

            Snackbar.make(mLayout, R.string.send_sms_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                    {Manifest.permission.SEND_SMS},
                            PERMISSION_REQUEST_SEND_SMS);
                }
            }).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);

        }
    }


    // requestPermission ... CAMERA
    public void requestPermissionCamera() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

            Snackbar.make(mLayout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]
                                    {Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
            }).show();

        } else {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);

        }
    }



    // onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.send_sms_access_granted,
                        Snackbar.LENGTH_SHORT).show();

                sendSms();

            } else {

                Snackbar.make(mLayout, R.string.send_sms_access_denied,
                        Snackbar.LENGTH_SHORT).show();
            }
        }


        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(mLayout, R.string.camera_access_granted,
                        Snackbar.LENGTH_SHORT).show();

                startCamera();

            } else {

                Snackbar.make(mLayout, R.string.camera_access_denied,
                        Snackbar.LENGTH_SHORT).show();
            }
        }


    }


    public void sendSms() {

        String number = "12346556";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
    }





    public void startCamera() {

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }


}
