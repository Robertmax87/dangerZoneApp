package com.superBoss.dangerzone;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;


import com.superBoss.dangerzone.databinding.ActivitySignupBinding;
import com.google.android.material.textfield.TextInputEditText;




public class Signup extends AppCompatActivity {
    /**Here I am instantiating my variables. I could easily instantiate them as something else.
     * however I have decided to instantiate them as longer more complex variables that are very
     * obvious.
     */
    TextInputEditText Fullname, Email, Username, Password, contact1, contact2, contact3, number1, number2, number3;
    TextView textView4;
    Button Register;
    //FirebaseAuth fAuth;
    ProgressBar Loading;
    private AppBarConfiguration appBarConfiguration;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        /** Here we know what our variables are. Using "findViewByID" we can locate what our
         * variables are supposed to represent.
         */
        Loading = findViewById(R.id.loadingBar);
        Fullname = findViewById(R.id.Fullname);
        Email = findViewById(R.id.Email);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Password);
        // textView4 = findViewById(R.id.textView4);
        Register = findViewById(R.id.fileNetwork);
        //fAuth = FirebaseAuth.getInstance();



        Register.setOnClickListener(new View.OnClickListener() {
            /**
             * This is where the button's functionality goes. Here we are able to make sure that
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                //String fullname, username, password;
                //fullname = String.valueOf(Fullname.getText());
                String email = Email.getText().toString().trim();
                //username = String.valueOf(Username.getText());
                String password = Password.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Password.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    Password.setError("The password is too short");
                    return;
                }
                if (email.length() < 6) {
                    Email.setError("The email length is not long enough");
                }
                Loading.setVisibility(View.VISIBLE);

            }
        });
    }
}