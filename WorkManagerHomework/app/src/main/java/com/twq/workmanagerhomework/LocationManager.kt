package com.twq.workmanagerhomework

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class LocationWorker (context: Context,workerParameters: WorkerParameters): Worker(context,workerParameters) {

    override fun doWork(): Result {
            showLocation()
        return Result.success()
    }


    @SuppressLint("MissingPermission")
    fun showLocation(){

        val lm = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager


//            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f) {
                val location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                val latitude = location?.latitude
                val longitude = location?.longitude
                val time = Date()
                println(location)
                println(time)

                FireBase(time, latitude, longitude)
            }
       // }

    fun FireBase(time: Date, latitude: Double?, longitude: Double?){
        val geocoder = Geocoder(applicationContext)
        val address = geocoder.getFromLocation(latitude!!,longitude!!,2)
        val db = FirebaseFirestore.getInstance()
        val location = hashMapOf(
            "date" to time,
            "latitude" to latitude,
            "longitude" to longitude,
            "address" to address[0].subAdminArea
        )

        db.collection("Location1").add(location)
            .addOnSuccessListener{
                println("Add location")
            }
            .addOnFailureListener{
                println("Failed to Add location")

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





