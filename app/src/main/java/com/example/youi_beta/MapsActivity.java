package com.example.youi_beta;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, LocationListener, LocationSource {


    Location myLocation;
    Float mdLat = 24.819970f, mdLon = 121.233422f;//bundle的值 目標店家經緯度
    Double dLat, dLon;       // 目前定位經緯度

    private GoogleMap mMap;

    private boolean mbIsZoomFirst = true;
    private Marker mMarker1, mMarker2;
    private Polyline mPolylineRoute;
    Button btn_go, btn_back;

    //定義授權要求之編號
    private final int REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION = 100;

    private LocationManager mLocationMgr;
    private OnLocationChangedListener mLocationChangeListener;
    Bundle bundle;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        final Bundle bData = this.getIntent().getExtras();
        final String name = bData.getString("name");
        final String address = bData.getString("address");
        final Float Lat = Float.parseFloat(bData.getString("Lat"));
        final Float Lon = Float.parseFloat(bData.getString("Lon"));
        mdLat = bData.getFloat("Lat");
        mdLon = bData.getFloat("Lon");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //set four button for OnClickListener
        Button btnAddMarker = (Button) findViewById(R.id.btnAddMarker);
        btnAddMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMarker1 = mMap.addMarker(new MarkerOptions()
                        .title(name) // 店家 名字
                        .snippet(address)//店家地址
                        .position(new LatLng(Lat, Lon))//店家經緯度
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker1))
                        .anchor(0.5f, 0.5f));
                Log.d("name=", name);
                Log.d("address=", address);
                Log.d("Lat(maker)=", String.valueOf(Lat));
                Log.d("Lon(maker)=", String.valueOf(Lon));

            }
        });


        Button btnShowRoute = (Button) findViewById(R.id.btnShowRoute);
        btnShowRoute.setOnClickListener(btnShowRouteOnClick);
        Button btnHideRoute = (Button) findViewById(R.id.btnHideRoute);
        btnHideRoute.setOnClickListener(btnHideRouteOnClick);
        btn_go = findViewById(R.id.btn_go);
        btn_back = findViewById(R.id.btn_back);


        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdLat = Float.parseFloat(bData.getString("Lat"));
                mdLon = Float.parseFloat(bData.getString("Lon"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(mdLat, mdLon)));
                Log.d("mdLat=", String.valueOf(mdLat));
                Log.d("mdLon=", String.valueOf(mdLon));
            }

        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
                Location location =
                        mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                dLat=location.getLatitude(); // 取得緯度
                dLon=location.getLongitude(); // 取得經度
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(dLat, dLon)));
                Log.d("回到目前位置:",String.valueOf(dLat)+";"+String.valueOf(dLon));
            }


        });

        Spinner spnMapType = findViewById(R.id.spnMapType);
        spnMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 2:
                        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case 3:
                        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btn3DMap = findViewById(R.id.btn3DMap);
        btn3DMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate camUpdate=CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(mMap.getCameraPosition().target)
                        .tilt(60)
                        .zoom(18)
                        .build());
                mMap.animateCamera(camUpdate);
            }
        });
        //建立 SupportMapFragment, 且設定 Map 之 Callback
        SupportMapFragment supportMapFragment = new SupportMapFragment();

        //建立 SupportMapFragment 放到介面佈局檔裡的 FrameLayout
        supportMapFragment.getMapAsync(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.frameLayMapContainer, supportMapFragment)
                .commit();
        mLocationMgr=(LocationManager)getSystemService(LOCATION_SERVICE);
    }

    //當 APP 被切換至前景執行時會呼叫 onStart(), 此時啟動定位系統
    @Override
    protected void onStart(){
        super.onStart();
        //APP 從背景切換至前景執行時, 啟動定位功能
        if (mMap!=null)
            checkLocationPermissionAndEnableIt(true);
    }
    //當APP 被切換至背景執行時會呼叫 onStop(), 此時會停止定位
    @Override
    protected void onStop() {
        super.onStop();

        //停止定位
        checkLocationPermissionAndEnableIt(false);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull
            String[] permissions,@NonNull int[] grantResults){
        // 檢查收到的權限要求編號是否與我們所送出的相同
        if (requestCode== REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //再檢查一次, 就會進入同意之狀態, 並且順利啟動
                checkLocationPermissionAndEnableIt(true);
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bData=this.getIntent().getExtras();
        final String name=bData.getString("name");
        final String address=bData.getString("address");
        final Float Lat=Float.parseFloat(bData.getString("Lat"));
        final Float Lon=Float.parseFloat(bData.getString("Lon"));


        mMap.setLocationSource(this);
        //取得上一次定位資料
        if(ContextCompat.checkSelfPermission(MapsActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            Location location=
                    mLocationMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            dLat=location.getLatitude(); // 取得緯度
            dLon=location.getLongitude(); // 取得經度

            Log.d("目前緯度 : ",String.valueOf(dLat)+" , 目前經度: "+String.valueOf(dLon));

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition
                    (new LatLng(location.getLatitude(), location.getLongitude()), 16, 0, 0)));
            if(mMarker2==null){mMarker2 =mMap.addMarker(new MarkerOptions()
                    .title("目前位置")
                    .position(new LatLng(dLat,dLon))//目前所在位置經緯度
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker2))   // Marker
                    .anchor(0.5f,0.5f));}
            else{mMarker2.setPosition(new LatLng(dLat,dLon));};



            if(location==null)
                location=
                        mLocationMgr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if(location!=null) {
                Toast.makeText(MapsActivity.this, "成功取得上一次定位", Toast.LENGTH_SHORT).show();
                onLocationChanged(location);   //更新地圖定位
                Log.d("Location=",String.valueOf(location));
            }else
                Toast.makeText(MapsActivity.this,"沒有上一次定位的資料",
                        Toast.LENGTH_LONG).show();
        }


        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View v=getLayoutInflater().inflate(R.layout.map_info_window,null);
                TextView txtTitle=v.findViewById(R.id.txtTitle);
                txtTitle.setText(marker.getTitle());
                TextView txtSnippet=v.findViewById(R.id.txtSnippet);
                txtSnippet.setText(marker.getSnippet());
                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        //set OnCliclListener for Info Window

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                marker.hideInfoWindow();
            }
        });
        //Build Polyline and  let it hide
        PolylineOptions polylineOpt=new PolylineOptions().width(15).color(Color.BLUE);
        ArrayList<LatLng> listLatLng=new ArrayList<LatLng>();
        listLatLng.add(new LatLng(dLat, dLon));
        Log.d("dLat=",String.valueOf(dLat));
        Log.d("dLon=",String.valueOf(dLon));
        mdLat=Float.parseFloat(bData.getString("Lat"));
        mdLon=Float.parseFloat(bData.getString("Lon"));
        listLatLng.add(new LatLng(mdLat, mdLon));// 建立位置的座標物件
        Log.d("mdLat(draw)=",String.valueOf(mdLat));
        Log.d("mdLon(draw)=",String.valueOf(mdLon));
        polylineOpt.addAll(listLatLng);
        mPolylineRoute=mMap.addPolyline(polylineOpt);
        mPolylineRoute.setVisible(true);
        mMarker1 =mMap.addMarker(new MarkerOptions()
                .title(name) // 店家 名字
                .snippet(address)//店家地址
                .position(new LatLng(Lat,Lon))//店家經緯度
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker1))
                .anchor(0.5f,0.5f));

    }




    private View.OnClickListener btnShowRouteOnClick= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPolylineRoute.setVisible(true);
        }
    };

    private View.OnClickListener btnHideRouteOnClick=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPolylineRoute.setVisible(false);
        }
    };

    @Override
    public void onLocationChanged(Location location) {

        //手機的位置改變時會執行此方法 , LOCATION 物件包含最新的定位資訊
        if(mLocationChangeListener!=null)
            //將新的位置傳給  Google Map 的 my-location layer
            mLocationChangeListener.onLocationChanged(location);
        //移動地圖到新位置
        mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        //手機定位功能狀態改變時會執行此方法
        String str=s;
        switch(i){
            case LocationProvider.OUT_OF_SERVICE:
                str +="定位功能無法使用";
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                str +="暫時無法定位";  //GPS 正在訂位中時會傳入此值
                break;
        }
        Toast.makeText(MapsActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String s) {
        // 手機的定位功能開啟時會執行此方法
        Toast.makeText(MapsActivity.this,s+"定位功能開啟",Toast.LENGTH_LONG).show();
        checkLocationPermissionAndEnableIt(true);
    }

    @Override
    public void onProviderDisabled(String s) {
        // 手機的定位功能關閉 時會執行此方法
        Toast.makeText(MapsActivity.this,s+"定位功能已經被關閉",Toast.LENGTH_LONG).show();
        checkLocationPermissionAndEnableIt(false);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationChangeListener=onLocationChangedListener;
        checkLocationPermissionAndEnableIt(true);
        Toast.makeText(MapsActivity.this,"地圖的 my-location layer  已經啟用",Toast.LENGTH_LONG).show();
    }

    @Override
    public void deactivate() {
        mLocationChangeListener=null;
        checkLocationPermissionAndEnableIt(false);
        Toast.makeText(MapsActivity.this," 地圖的 my-location layer  已經關閉",Toast.LENGTH_LONG).show();
    }

    private void checkLocationPermissionAndEnableIt(boolean on) {
        if(ContextCompat.checkSelfPermission(MapsActivity.this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED)
        {
            // 徵詢使用者同意取得定位並執行
            if(ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)){
                AlertDialog.Builder altDlgBuilder=new AlertDialog.Builder(MapsActivity.this);
                altDlgBuilder.setTitle("提示");
                altDlgBuilder.setMessage("APP 需要啟動定位功能");
                altDlgBuilder.setIcon(android.R.drawable.ic_dialog_info);
                altDlgBuilder.setCancelable(false);
                altDlgBuilder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //顯示詢問使用者是否同意功能權限的對話盒
                        //使用者答覆後會執行 onRequestPermissionsResult()
                        ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION);
                    }
                });
                altDlgBuilder.show();
                return;
            }else {

                ActivityCompat.requestPermissions(MapsActivity.this, new String[]{
                        android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_FOR_ACCESS_FINE_LOCATION);
                return;
            }
        }
        //此項功能先前已取得使用者之同意

        // 依據 on 參數之值, 啟動或關閉定位功能
        if(on){
            // 如果 GPS 功能有開啟, 則優先使用 GPS 定位系統, 否則使用網路定位
            if(mLocationMgr.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                mLocationMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,this);
                Toast.makeText(MapsActivity.this,"使用 GPS 定位",Toast.LENGTH_LONG).show();
            }else
            if(mLocationMgr.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                mLocationMgr.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,5000,5,this);
                Toast.makeText(MapsActivity.this,"使用網路定位",Toast.LENGTH_LONG).show();
            }
        }else{
            mLocationMgr.removeUpdates(this);
            Toast.makeText(MapsActivity.this,"定位功能已停用", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
