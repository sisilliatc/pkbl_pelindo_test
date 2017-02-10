package com.happytumiteam.surveypkbl;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.GoogleMap;

import java.util.HashMap;
import java.util.Map;

public class AmbilLokasi extends FragmentActivity implements LocationListener  {

    private static final String TAG = AmbilLokasi.class.getSimpleName();
    private ImageView btn_cancel;
    Button btn_ambil_lokasi, btn_tampilkan,  btn_simpan_lokasi;
    String provider;
    Location l;
    LocationManager lm;
    TextView link, latitude, longitude;
    private ProgressDialog pDialog;
    private SQLiteHandler db;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ambil_lokasi);

        btn_ambil_lokasi = (Button) findViewById(R.id.btn_tag_lokasi);
        btn_tampilkan = (Button) findViewById(R.id.btn_Lihat_Map);
        btn_simpan_lokasi = (Button) findViewById(R.id.btn_simpan_lokasi);
        btn_cancel = (ImageView) findViewById(R.id.cancel_ambil_lokasi);
        link = (TextView) findViewById(R.id.Link_textView);
        latitude = (TextView) findViewById(R.id.Latitude_textView);
        longitude = (TextView) findViewById(R.id.Longitude_textView);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        provider = lm.getBestProvider(c, false);
        //now you have best provider
        //get location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        l = lm.getLastKnownLocation(provider);

        //ambil data lokasi
        btn_ambil_lokasi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(l!=null)
                    {
                        //get latitude and longitude of the location
                        double lng=l.getLongitude();
                        double lat=l.getLatitude();
                        //display on text view

                        longitude.setText("longitude :"+lng);
                        latitude.setText("latitude :"+lat);

                        link.setText("Link : http://maps.google.com/maps?saddr="+l.getLatitude()+","+l.getLongitude());

                    }
                    else
                    {
                        longitude.setText("No Provider");
                        latitude.setText("No Provider");
                    }

                }
            });

        //tampilkan dalam bentuk maps
        btn_tampilkan.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {



                //ambil data loc
                double lng=l.getLongitude();
                double lat=l.getLatitude();


                // Validating User Input
                if(latitude.getText().toString().isEmpty() && longitude.getText().toString().isEmpty()){
                    Toast.makeText(getBaseContext(), "Lakukan proses pengambilan lokasi dulu....", Toast.LENGTH_SHORT).show();

                    // Focus the EditText for Latitude
                    latitude.requestFocus();

                    return;
                }else{
                    // Getting location

                    try {
                        lng = Double.parseDouble(latitude.getText().toString());
                        lat = Double.parseDouble(longitude.getText().toString());
                    }catch (NumberFormatException e){

                    }

                }

                Intent intent = new Intent(getBaseContext(), MapDemoActivity.class);

                // Passing latitude and longitude to the MapActiv
                intent.putExtra("lat", lat);
                intent.putExtra("lng", lng);

                startActivity(intent);

                //koding sebelumnya yg salah
//                l.getLatitude();
//                l.getLongitude();
//
//                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
//                        Uri.parse("http://maps.google.com/maps?saddr="+l.getLatitude()+",&daddr=20.5666,45.345"));
//
////                String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+l.getLatitude()+","+l.getLongitude();
////                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(Intent.createChooser(intent, "Select an application"));

                 }
             });

        //simpan lokasi yang sudah ditag
        btn_simpan_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String latitude_file = latitude.getText().toString().trim();
                String longitude_file= longitude.getText().toString().trim();
                String http_file = link.getText().toString().trim();

                if(!latitude_file.isEmpty() && !longitude_file.isEmpty() && !http_file.isEmpty()){
                    Ambil_Lokasi(latitude_file,longitude_file,http_file);

                }
                else{
                   Toast.makeText(getApplicationContext(),
                           "Lakukan proses pengambilan lokasi dulu....",Toast.LENGTH_LONG)
                           .show();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent FormData = new Intent(getApplication(), FormData.class);
                    startActivity(FormData);
                }
            });

    }



    private void Ambil_Lokasi(final String latitude_file, final String longitude_file, final String link){

        String tag_string_req = "request_location";

        pDialog.setMessage("Save data...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.Tag_Lokasi, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d(TAG,"Save Response" + s.toString());
                hideDialog();
                Log.i("tagconvertstr", "["+s+"]");
                Toast.makeText(getApplicationContext(), "Lokasi berhasil tersimpan", Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("latitude", latitude_file);
                params.put("longitude", longitude_file);
                params.put("link", link);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }



    @Override
    public void onLocationChanged(Location location) {

        double lng=l.getLongitude();
        double lat=l.getLatitude();
        longitude.setText(""+lng);
        latitude.setText(""+lat);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

        Toast.makeText( getApplicationContext(),
                "GPS Enabled",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String s) {

        Toast.makeText( getApplicationContext(),
                "GPS Disabled",
                Toast.LENGTH_SHORT ).show();


    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
