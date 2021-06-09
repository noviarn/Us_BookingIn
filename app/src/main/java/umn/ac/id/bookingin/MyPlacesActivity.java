package umn.ac.id.bookingin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import umn.ac.id.bookingin.databinding.ActivityMyPlacesBinding;
import umn.ac.id.bookingin.databinding.ActivityProfileBinding;

public class MyPlacesActivity extends AppCompatActivity {
    private ActivityMyPlacesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);
        binding = ActivityMyPlacesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyPlacesActivity.this, AddPlaceActivity.class));

            }
        });
    }
}