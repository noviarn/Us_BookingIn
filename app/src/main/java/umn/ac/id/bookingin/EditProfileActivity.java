package umn.ac.id.bookingin;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import umn.ac.id.bookingin.databinding.ActivityEditProfileBinding;

import static android.content.ContentValues.TAG;


public class EditProfileActivity extends AppCompatActivity {
    private ActivityEditProfileBinding binding;
    private FirebaseAuth fAuth;
    private DatabaseReference ref;



    private FirebaseStorage storage;

    private StorageReference storageReference;

    /*String PROFILE_IMAGE_URL = null;*/
    /*String DISPLAY_NAME;*/
    /*String DISPLAY_PHONE;
    int TAKE_IMAGE_CODE = 10001;*/

    private static final String TAG ="EditProfileActivity";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        //init
        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("user");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Profile Pic");

        checkUser();

        FirebaseUser user = fAuth.getCurrentUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
              /*  updateProfile();*/
            }
        });


    }


    private  String changeName, changePhone;
    private void updateUser() {
        FirebaseUser fUser = fAuth.getCurrentUser();

        //get data
        changeName = binding.nameEt.getText().toString().trim();
        changePhone = binding.phoneEt.getText().toString().trim();
        if (TextUtils.isEmpty(changeName)){
            Toast.makeText(this, "Enter your name..", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(changePhone)) {
            Toast.makeText(this, "You must enter your phone number", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(EditProfileActivity.this, "Updated", Toast.LENGTH_SHORT).show();
        }


        ref = FirebaseDatabase.getInstance().getReference("user");
        String uid = fUser.getUid();

        ref.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String Name = ""+snapshot.child("name").getValue();
                String Email= ""+snapshot.child("email").getValue();
                String Phone = ""+snapshot.child("phone").getValue();
                /*String Photo = ""+snapshot.child("profileImage").getValue();*/
                long timestamp = System.currentTimeMillis();
                String uid = ""+snapshot.child("uid").getValue();
                String userType = ""+snapshot.child("userType").getValue();



                    if(!Name.equals(changeName)||!Phone.equals(changePhone)){

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("name",changeName);
                        hashMap.put("email",Email);
                        /*hashMap.put("profileImage",imageid);*/
                        hashMap.put("phone", changePhone);
                        hashMap.put("timetamp", timestamp);
                        hashMap.put("uid", uid);
                        hashMap.put("userType", userType);

                        ref.child(uid)
                                .setValue(hashMap)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(EditProfileActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                        //after user account is created
                                        startActivity(new Intent(EditProfileActivity.this, ProfileActivity.class));
                                        finish();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        //data failed updateed to db
                                        Toast.makeText(EditProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                    }



            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(EditProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void checkUser() {
        //get current user
        FirebaseUser fUser = fAuth.getCurrentUser();

        ref = FirebaseDatabase.getInstance().getReference("user");

        if (fUser == null){
            //not logged in, goto main
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            String uid = fUser.getUid();

            ref.child(uid).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {

                    String Name = ""+snapshot.child("name").getValue();
                    String Email = ""+snapshot.child("email").getValue();
                    String Phone = ""+snapshot.child("phone").getValue();

                    binding.nameEt.setHint(Name);
                    binding.phoneEt.setHint(Phone);
                    binding.emailEt.setText(Email);


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Toast.makeText(EditProfileActivity.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }


}