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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dangerzone.databinding.ActivityEmergencyViewBinding;


public class EmergencyView extends AppCompatActivity {


    android.widget.Button Police, suspicious;
    Button networkEditor;

    private AppBarConfiguration appBarConfiguration;
    private ActivityEmergencyViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        networkEditor = findViewById(R.id.editNetwork);
        Police = findViewById(R.id.Police);
        suspicious = findViewById(R.id.suspicious);

        binding = ActivityEmergencyViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_emergency_view);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //send our current location with a message that we are being pulled over by the police through smsManager


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
                //Address currentLocation = null;
                //String uri = "You are receiving this message because you are in my safety network. I , " + PhoneBook.getName() + "have been pulled over by the police at this location: "+ "http://maps.google.com/maps?saddr=" + latitude +","+ longitude;

                SmsManager smsManager = SmsManager.getDefault();
                StringBuffer smsBody = new StringBuffer();
                //smsBody.append(Uri.parse(uri));

                //smsManager.sendTextMessage(phonenumber, null, smsBody.toString(), null, null);


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
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                //Address currentLocation = null;
                //  String uri = "You are receiving this message because you are in my safety network. I , " + PhoneBook.getName() + "may be in danger at or near this location: "+ "http://maps.google.com/maps?saddr=" + latitude +","+ longitude;

                SmsManager smsManager = SmsManager.getDefault();
                StringBuffer smsBody = new StringBuffer();

                //get phone numbers from firebase and shoot off texts to them
                // smsBody.append(Uri.parse(uri));

                //for(String key : )

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
}


        /**binding.fab.setOnClickLidstener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_emergency_view);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}**/