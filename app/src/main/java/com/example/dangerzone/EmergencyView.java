package com.example.dangerzone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dangerzone.databinding.ActivityEmergencyViewBinding;

import java.util.HashMap;

import static com.example.dangerzone.Network.getSavedObjectFromPreference;


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

       police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send our current location with a message that we are being pulled over by the police through smsManager

                PhoneBook pb = getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey");

                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();

                //get phone numbers from fireBase and shoot off texts to them
                Address currentLocation = null;
                String uri = "You are receiving this message because you are in my safety network. I , " + "have been pulled over by the police at this location: "+ "http://maps.google.com/maps?saddr=" + latitude +","+ longitude;

                SmsManager smsManager = SmsManager.getDefault();

                assert pb != null;
                HashMap<String, String> contacts = pb.getRolodex();
                for(String name : contacts.keySet()){
                    String phonenumber = contacts.get(name);
                    smsManager.sendTextMessage(phonenumber, null, uri, null, null);
                }



                //opens video camera
                Intent openCamera = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivityForResult(openCamera, 1000);


            }
        });

         suspicious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send our current location with a message that we may be in danger through smsManager
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EmergencyView.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                PhoneBook pb = getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey");
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                //Address currentLocation = null;
                String uri = "You are receiving this message because you are in my safety network. I , " +  "may be in danger at or near this location: "+ "http://maps.google.com/maps?saddr=" + latitude +","+ longitude;

                SmsManager smsManager = SmsManager.getDefault();
                assert pb != null;
                HashMap<String, String> contacts = pb.getRolodex();
                for(String name : contacts.keySet()){
                    String phonenumber = contacts.get(name);
                    smsManager.sendTextMessage(phonenumber, null, uri, null, null);
                }

                //get phone numbers from firebase and shoot off texts to them
                // smsBody.append(Uri.parse(uri));



                // smsManager.sendTextMessage(phonenumber, null, smsBody.toString(), null, null);
                Context context = getApplicationContext();
                CharSequence text = "If you haven't used the app before or want to change your safety network, hit the button on the bottom of the page";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

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
    private void requestSmsPermission() {

        // check permission is given
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);

        }
    }
}