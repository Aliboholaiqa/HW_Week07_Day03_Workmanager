package com.twq.workmanagerhomework

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.firebase.firestore.FirebaseFirestore
import com.twq.workmanagerhomework.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = FirebaseFirestore.getInstance()
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
           check()
        }

        setContentView(binding.root)
    }

    fun check(){
        //var locationManager = getSystemService(LOCATION_SERVICE) as? LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),0
            )
        }

        else{
            val worker = PeriodicWorkRequestBuilder<LocationWorker>(10, TimeUnit.SECONDS).build()
            WorkManager.getInstance(this).enqueue(worker)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            check()
        }else{
            AlertDialog.Builder(this).apply {
                title = "Warning"
                setMessage("To access location allow the permission")
                setPositiveButton("Ok",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri : Uri = Uri.fromParts("package",packageName,null)
                        intent.data = uri
                        startActivity(intent)
                    }
                })
            }
        }
    }
}


//fun showLocation() {
//
//
//    var locationManager = ContextCompat.getSystemService(AppCompatActivity.LOCATION_SERVICE) as? LocationManager
//    if (ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//            this,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) != PackageManager.PERMISSION_GRANTED
//    ) {
//
//    }
//    locationManager?.requestLocationUpdates(
//        LocationManager.GPS_PROVIDER,0,0f,
//        object: LocationListener {
//            override fun onLocationChanged(location: Location) {
//                //location.bearing
//
//                val l = hashMapOf("latitude" to location.latitude,
//                    "longitude" to location.longitude
//                )
//                db.collection("Location")
//                    .add(l)
//                    .addOnSuccessListener { documentReference ->
//                        Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
//                    }
//                    .addOnFailureListener { e ->
//                        Log.w(ContentValues.TAG, "Error adding document", e)
//                    }
//                //textView.text = "${location.latitude}   ${location.longitude}"
//                //this@MainActivity or context
//                Thread(){
//                    var geocoder = Geocoder(this@MainActivity)
//                    var l = geocoder.getFromLocation(location.latitude,location.altitude,10)
//                    //l[0].getAddressLine()
//                    val address = l[0]
//                    println(address.countryName + " "+address.adminArea)
//                    println(address.getAddressLine(0)+" "+address.getAddressLine(1))
//
//                    runOnUiThread {
//                        textView2.text = address.getAddressLine(0)+" "+address.getAddressLine(1)
//                    }
//                }.start()
//            }
//        })
//
//}