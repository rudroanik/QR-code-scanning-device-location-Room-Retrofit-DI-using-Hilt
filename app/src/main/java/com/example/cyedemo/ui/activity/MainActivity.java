package com.example.cyedemo.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cyedemo.database.table.QrCodeData;
import com.example.cyedemo.databinding.ActivityMainBinding;
import com.example.cyedemo.ui.adapter.ShowQrCodeDataAdapter;
import com.example.cyedemo.viewmodel.QrCodeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private QrCodeViewModel qrCodeViewModel;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    @Inject  ShowQrCodeDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        qrCodeViewModel = new ViewModelProvider(this).get(QrCodeViewModel.class);

        qrCodeViewModel.pagedListLiveData.observe(this, this::initRecyclerView);

        getLocationPermission();
        getDeviceLocation();

        binding.btnScan.setOnClickListener(v -> {
            IntentIntegrator intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.setCaptureActivity(QRCodeScannerActivity.class);
            intentIntegrator.setOrientationLocked(false);
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            intentIntegrator.setPrompt("Scan QR Code");
            intentIntegrator.initiateScan();

        });
    }

    private void initRecyclerView(PagedList<QrCodeData> qrCodeDataList) {

        adapter.submitList(qrCodeDataList);
        binding.qrcodeScanDataRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.qrcodeScanDataRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void getLocationPermission() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {


            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {

        final FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {

            if (location != null) {

                binding.locationTv.setText("Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude() + "\n" + ConvertPointToLocation(location.getLatitude(), location.getLongitude()));
            }

        });
    }


    public String ConvertPointToLocation(double Latitude, double Longitude) {
        String address = "";
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                    Latitude, Longitude, 5);

            if (addresses.size() > 0) {
                for (int index = 0; index < addresses.size(); index++)
                    address += addresses.get(0).getAddressLine(index) + " ";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return address;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                for (int grantResult : grantResults) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                }
                getDeviceLocation();

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                QrCodeData qrCodeData = new QrCodeData(result.getContents());
                qrCodeViewModel.insertData(qrCodeData);
                /*try {

                    //converting the data to json
                    *//*JSONObject obj = new JSONObject(result.getContents());
                    QrCodeData qrCodeData = new QrCodeData(obj.toString());
                    qrCodeViewModel.insertData(qrCodeData);*//*
                } catch (JSONException e) {
                    e.printStackTrace();
                    QrCodeData qrCodeData = new QrCodeData(result.getContents());
                    qrCodeViewModel.insertData(qrCodeData);
                }*/
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}