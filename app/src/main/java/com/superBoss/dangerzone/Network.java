package com.superBoss.dangerzone;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.superBoss.dangerzone.databinding.ActivityNetworkBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Network extends AppCompatActivity {

    //private static Object PhoneBook;

    TextInputEditText name1, name2, name3, number1, number2, number3;
    android.widget.Button networkFile;
    DatabaseReference myData;
    Context context;

    private AppBarConfiguration appBarConfiguration;
    private ActivityNetworkBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //myData = FirebaseDatabase.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        name1 = findViewById(R.id.name1);
        name2 = findViewById(R.id.name2);
        name3 = findViewById(R.id.name3);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        networkFile = findViewById(R.id.fileNetwork);




       // myData.child("PhoneBooks").child("userContacts").setValue(universal);




       // binding = ActivityNetworkBinding.inflate(getLayoutInflater());
       // setContentView(binding.getRoot());

        //setSupportActionBar(binding.toolbar);

      /**  NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_network);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
       **/
        //send information to database and then send to the home page with the two buttons
        networkFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name_1 = name1.getText().toString().trim();
                String name_2 = name2.getText().toString().trim();
                String name_3 = name3.getText().toString().trim();
                String number_1 = number1.getText().toString().trim();
                String number_2 = number2.getText().toString().trim();
                String number_3 = number3.getText().toString().trim();

                List<String> names = new ArrayList<>();
                names.add(name_1);
                names.add(name_2);
                names.add(name_3);

                List<String> numbers = new ArrayList<>();
                numbers.add(number_1);
                numbers.add(number_2);
                numbers.add(number_3);

                PhoneBook universal = new PhoneBook(names, numbers);

                Toast.makeText(Network.this, "Network added", Toast.LENGTH_SHORT).show();
                saveObjectToSharedPreference(getApplicationContext(), "mPreference", "mObjectKey", universal);
                finish();

//                startActivity(new Intent(Network.this, EmergencyView.class));


            }
        });
    }

    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, PhoneBook object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }

    public static PhoneBook getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), PhoneBook.class);
        }
        return null;
    }
}