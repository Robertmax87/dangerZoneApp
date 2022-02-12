package com.superBoss.dangerzone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.superBoss.dangerzone.databinding.ActivityEmergencyViewBinding;

import java.io.File;
import java.util.HashMap;

import static com.superBoss.dangerzone.Network.getSavedObjectFromPreference;


class TextSender extends Thread {

    private String msg;
    private String phoneNum;

    public TextSender(String phoneNum, String msg)
    {
        this.msg = msg;
        this.phoneNum = phoneNum;
    }

    public void run()
    {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNum, null, msg, null, null);
    }

}


public class EmergencyView extends AppCompatActivity {

    private static final int PERMISSION_SEND_SMS = 123;

    android.widget.Button police, suspicious, networkEditor;

    private AppBarConfiguration appBarConfiguration;
    private ActivityEmergencyViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_view);

        networkEditor = findViewById(R.id.editNetwork);
        police = findViewById(R.id.Police);
        suspicious = findViewById(R.id.suspicious);

        // binding = ActivityEmergencyViewBinding.inflate(getLayoutInflater());
        //  setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);


        requestSmsPermission();
        requestLocationPermission();
        requestVideoPermission();

        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send our current location with a message that we may be in danger through smsManager

                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                System.out.println("Starting retrieval");
                PhoneBook pb = getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey");
                System.out.println("Retrieval complete");

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestLocationPermission();

                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                if(true){//(location != null) {
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
//                    Address currentLocation = null;

                    String uri = "I have been stopped by the Police. You are receiving this message because you are in my safety network http://maps.google.com/maps?q=loc:" + String.format("%f,%f",latitude , longitude);


                    System.out.println(uri);
                    System.out.println(latitude+","+longitude);
                    assert pb != null;
                    //System.out.println("size: " + pb.rolodex.size());
                    HashMap<String, String> contacts = pb.getRolodex();

                    if (contacts.isEmpty()) {
                        Context context = getApplicationContext();
                        CharSequence text = "Before using make DangerZone has the location, If you haven't used the app before or want to change your safety network, hit the button on the bottom of the page";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }

                    for (String name : contacts.keySet()) {
                        sendSMS(contacts.get(name), uri);
                        System.out.println(contacts.get(name));
                    }
                }
                else
                {
                    System.out.println("Unable to get location");
                }

                //get phone numbers from firebase and shoot off texts to them
                // smsBody.append(Uri.parse(uri));

                Intent openCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                openCamera.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                startActivityForResult(openCamera, 2);




            }
        });


        suspicious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send our current location with a message that we may be in danger through smsManager

                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                System.out.println("Starting retrieval");
                PhoneBook pb = getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey");
                System.out.println("Retrieval complete");

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestLocationPermission();

                }
               Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
               double latitude = location.getLatitude();


                String uri = "This is a distress message. I may be in danger at this location: http://maps.google.com/maps?q=loc:" + String.format("%f,%f", latitude ,longitude);



                assert pb != null;
                //System.out.println("size: " + pb.rolodex.size());
                HashMap<String, String> contacts = pb.getRolodex();

                if(contacts.isEmpty()){
                    Context context = getApplicationContext();
                    CharSequence text = "If you haven't used the app before or want to change your safety network, hit the button on the bottom of the page";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }

                for(String name : contacts.keySet()){
//
                    sendSMS(contacts.get(name), uri);
                }

                //get phone numbers from firebase and shoot off texts to them
                // smsBody.append(Uri.parse(uri));






            }
        });

        //update or add our network

        networkEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmergencyView.this, Network.class);
                startActivity(intent);
               finish();
            }
        });
}

    private void sendSMS(String phoneNum, String msg)
    {
        TextSender ts = new TextSender(phoneNum, msg);
        ts.start();
    }

    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);

        }
    }

    private void requestLocationPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);

        }

    }


    private void requestVideoPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);

        }

    }
    private File getTempFile(Context context) {
        final File path = new File(Environment.getExternalStorageDirectory(),
                context.getPackageName());
        if (!path.exists()) {
            path.mkdir();
        }
        return new File(path, "myImage.png");
    }
}