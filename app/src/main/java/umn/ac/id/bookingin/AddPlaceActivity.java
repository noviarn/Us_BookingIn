package umn.ac.id.bookingin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import umn.ac.id.bookingin.databinding.ActivityAddPlaceBinding;
import umn.ac.id.bookingin.databinding.ActivityRegisterBinding;

public class AddPlaceActivity extends AppCompatActivity{

    private EditText placeNameEdt, placeLocEdt, placeDescEdt;
    private Button submitBtn;

    //view binding
    private ActivityAddPlaceBinding binding;

    //firebase Auth
    private FirebaseAuth fAuth;

    // creating a variable for
    // our object class
    UserPlaceInfo userPlaceInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // initializing our edittext and button
        placeNameEdt = findViewById(R.id.placeNameBox);
        placeLocEdt = findViewById(R.id.placeLocBox);
        placeDescEdt = findViewById(R.id.placeDescBox);

        // below line is used to get the
        // instance of our FIrebase database.
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();

        //init firebase auth
        fAuth = FirebaseAuth.getInstance();

        // below line is used to get reference for our database.
        DatabaseReference taskRef = rootRef.child("UserPlace").push();

        // initializing our object
        // class variable.
        userPlaceInfo = new UserPlaceInfo();

        submitBtn = findViewById(R.id.submitPlaceBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting text from our edittext fields.
                String name = placeNameEdt.getText().toString();
                String location = placeLocEdt.getText().toString();
                String desc = placeDescEdt.getText().toString();

                // below line is for checking weather the
                // edittext fields are empty or not.
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location) || TextUtils.isEmpty(desc)) {
                    // if the text fields are empty
                    // then show the below message.
                    Toast.makeText(AddPlaceActivity.this, "All fields should be filled!", Toast.LENGTH_SHORT).show();
                } else {
                    // else call the method to add
                    // data to our database.
                    addDatatoFirebase(name, location, desc);
                }
            }

            private void addDatatoFirebase(String name, String location, String desc) {
                //get current user uid, since user is registered so we can get now
                String user_id = fAuth.getCurrentUser().getUid();

                // below 3 lines of code is used to set
                // data in our object class.
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("user_id", user_id);
                hashMap.put("name", name);
                hashMap.put("location", location);
                hashMap.put("desc", desc);

                //set data to db
                taskRef
                        .setValue(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                //data added to db
                                Toast.makeText(AddPlaceActivity.this, "Place submission successful", Toast.LENGTH_SHORT).show();
                                //after user account is created
                                startActivity(new Intent(AddPlaceActivity.this, MyPlacesActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                //data failed adding to db
                                Toast.makeText(AddPlaceActivity.this, "Place submission failed", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}