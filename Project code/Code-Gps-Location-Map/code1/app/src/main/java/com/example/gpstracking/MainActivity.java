
package com.example.gpstracking;


//import com.google.android.gms.location.FusedLocationProviderClient;
//import com.google.android.gms.location.LocationServices;

//import com.google.android.material.R;





import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    TextView tv_lat , tv_lon , tv_altitude , tv_accuracy , tv_speed , tv_sensor , tv_updates , tv_address;
    Switch sw_locationupdates ,sw_gps;


//    FusedLocationProviderClient    FusedLocationProviderClient;













    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);





        tv_lat = findViewById(R.id . tv_lat);
        tv_lon =  findViewById(R.id . tv_lon);
        tv_altitude =  findViewById(R.id . tv_altitude);
        tv_accuracy=  findViewById(R.id . tv_accuracy);
        tv_speed=  findViewById(R.id . tv_speed);
        tv_sensor=  findViewById(R.id . tv_sensor);
        tv_updates=  findViewById(R.id . tv_updates);
        tv_address=  findViewById(R.id . tv_address);
        sw_gps=  findViewById(R.id . sw_gps);
        sw_locationupdates= findViewById(R.id . sw_locationsupdates);







        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}




