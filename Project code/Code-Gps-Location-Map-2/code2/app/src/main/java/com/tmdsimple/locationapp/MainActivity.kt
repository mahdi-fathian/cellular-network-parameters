package com.tmdsimple.locationapp

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.tmdsimple.locationapp.ui.theme.LocationAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel : LocationViewModel = viewModel()
            LocationAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   Navigation()

                }
            }
        }
    }
}

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val viewModel : LocationViewModel = viewModel()
    val context = LocalContext.current
    val locationUtils = MyLocationUtils(context)


    NavHost(navController, startDestination= "mainscreen"){
        composable("mainscreen"){
            DisplayLocation(myLocationUtils = locationUtils,
                            viewModel = viewModel,
                            navController= navController,
                            context = context,
                      addressResults= viewModel.address.value.firstOrNull()?.formatted_address ?: "No Address")

        }

        dialog("locationselectionscreen"){backstack ->
            viewModel.location.value?.let{it1 ->

                LocationSelectionScreen(location = it1, onLocationSelected = {locationdata ->
                    viewModel.fetchAddress("${locationdata.latitude},${locationdata.longitude}")
                    navController.popBackStack()
                })

            }

        }

    }
}



@Composable
fun DisplayLocation(
    myLocationUtils: MyLocationUtils,
    viewModel: LocationViewModel,
    navController: NavController,
    context: Context,
    addressResults: String
){
    val location = viewModel.location.value

    val address = location?.let {
        myLocationUtils.requestGeocodeLocation(location)
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
       contract = ActivityResultContracts.RequestMultiplePermissions() ,
       onResult = { permissions ->
           if( permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
               &&
               permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
               ) {
                // Ok can access location
               myLocationUtils.requestLocationUpdates(viewModel=viewModel)

           } else {
               // ask for permission
               val rationaleRequired = ActivityCompat.shouldShowRequestPermissionRationale(
                                       context as MainActivity,
                                       Manifest.permission.ACCESS_FINE_LOCATION ) ||
                                       ActivityCompat.shouldShowRequestPermissionRationale(
                                       context as MainActivity,
                                       Manifest.permission.ACCESS_COARSE_LOCATION )

               if(rationaleRequired) {
                   Toast.makeText(context,"This feature require location permission",Toast.LENGTH_LONG).show()
               }else{
                   // need to set the permission from setting.
                   Toast.makeText(context,"Please enable location permission from android setting",Toast.LENGTH_LONG).show()
               }
           }
       })

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(all = 18.dp),
             horizontalAlignment = Alignment.CenterHorizontally,
             verticalArrangement = Arrangement.Center
    ){

        if ( location != null) {
            Text( "Location:\nlat: ${location.latitude} & long: ${location.longitude} \n$address",
                   fontSize = 20.sp)
        } else {
            Text("location not available")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if(myLocationUtils.hasLocationPermission(context)) {
               // permission granted -> update the location
               myLocationUtils.requestLocationUpdates(viewModel)
            }else {
               // Request location permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text("Get My Location",
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            if(myLocationUtils.hasLocationPermission(context)) {
                // permission granted -> update the location
                myLocationUtils.requestLocationUpdates(viewModel)
                navController.navigate("locationselectionscreen"){
                    this.launchSingleTop
                } // if I don't have permission
            }else {
                // Request location permission
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }) {
            Text("Select Location",
                fontSize = 20.sp)
        }

        if ( addressResults != "") {
            Text( "Selected Location:\n$addressResults",
                fontSize = 20.sp)
        } else {
            Text("Location not Selected")
        }
    }

}