package umn.ac.id.bookingin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import umn.ac.id.bookingin.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity{
    private ActivityHomeBinding binding;

    private FirebaseAuth fAuth;
    private DatabaseReference database;
    PlaceAdapter placeAdapter;
    List<UserPlaceInfo> placeList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
       /* placeAdapter = new PlaceAdapter(placeList);

        binding.recyclerView.setAdapter(placeAdapter);*/

        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("UserPlace");
        checkUser();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_history:
                        startActivity(new Intent(getApplicationContext(), HistoryActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_notif:
                        startActivity(new Intent(getApplicationContext(), NotifActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.nav_account:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));


                }
                return false;
            }
        });



        /*binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                checkUser();


            }
        });*/
    }


    private void checkUser() {
        //get current user
        FirebaseUser fUser = fAuth.getCurrentUser();
        if (fUser == null){
            //not logged in, goto main
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }else{
            String email = fUser.getEmail();
            binding.subEmailAcc.setText(email);

        }
    }


}