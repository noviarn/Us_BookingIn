package umn.ac.id.bookingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SplashActivity extends AppCompatActivity {
    //firebase auth
    private FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //init firebase  auth
        fAuth = FirebaseAuth.getInstance();

        //start main screen after 2seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               //start main screen
               checkUser();
            }
        }, 2000);//means 2seconds
    }

    private void checkUser() {
        FirebaseUser fUser = fAuth.getCurrentUser();

        if(fUser == null){
            //user not login yet
            //start mainscreen
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
        }else{
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("user");
            ref.child(fUser.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            //get user type
                            String userType = ""+snapshot.child("userType").getValue();
                            //check usertype
                            if(userType.equals("user")){
                                //go home
                                startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {

                        }
                    });
        }
    }
}