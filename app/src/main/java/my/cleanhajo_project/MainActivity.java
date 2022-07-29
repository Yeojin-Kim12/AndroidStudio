package my.cleanhajo_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.provider.Settings;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    GoogleMap mMap;
    private Marker currentMarker = null;
    LocationManager locationManager;
    EditText editText;
    Button button;
    MarkerOptions myMarker;
    ImageView imageView;
    double lat,lng;
    String clickMarker, random1, random2, random3, random4;
    int totalRandom;

    String name1 = "희망빨래방세탁소";
    String name2 = "마마운동화이불빨래방";
    String name3 = "왔슈 셀프빨래방";
    String name4 = "우송운동화빨래방";
    String name5 = "개울가빨래방";
    String name6 = "워시팡팡 셀프빨래방";
    String name7 = "셀피아";
    String name8 = "유앤아이워시";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        imageView = findViewById(R.id.imageView);

        // 위에 바 없애기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // 위치 활성화가 안 되어 있는 경우
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            showMessage();
        }

        // 권한
        checkDangerousPermissions();

        // 랜덤 숫자 부여
        random1 = numberRandom(1,1);
        random2 = numberRandom(1,1);
        random3 = numberRandom(1,1);
        random4 = numberRandom(1,1);
        totalRandom = Integer.parseInt(random1) + Integer.parseInt(random2) + Integer.parseInt(random3) + Integer.parseInt(random4);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // 키보드 내리기
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);    //hide keyboard
                    return true;
                }


                //Enter key Action
                switch (keyCode) {
                    case KeyEvent.KEYCODE_ENTER:

                        // 지오코딩
                        String addr = editText.getText().toString();
                        // 지오 코딩 작업을 수행하는 객체 생성
                        Geocoder geocoder= new Geocoder(getApplicationContext(),Locale.KOREA);
                        List<Address> addresses = null;

                        // 지오코더에게 지오코딩작업 요청
                        try {
                            addresses = geocoder.getFromLocationName(addr,3);
                            // 최대 3개까지 받음, 0~3개 있으면 받는다.

                            // 없는 주소일 경우
                            if (addresses == null || addresses.size() == 0) {
                                Toast.makeText(getApplicationContext(), "검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            StringBuffer buffer= new StringBuffer();
                            for(Address t : addresses){
                                buffer.append(t.getLatitude()+", "+t.getLongitude()+"\n");
                            }
                            // 대표 좌표값 위도, 경도
                            lat = addresses.get(0).getLatitude();
                            lng = addresses.get(0).getLongitude();
                            // 좌표(위도, 경도) 생성
                            LatLng point = new LatLng(lat, lng);

                            if (currentMarker != null) currentMarker.remove();
                            // 마커 생성
                            MarkerOptions mOptions2 = new MarkerOptions();
                            mOptions2.title(addr);
                            mOptions2.position(point);
                            // 마커 추가
                            mOptions2.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            currentMarker = mMap.addMarker(mOptions2);
                            // 해당 좌표로 화면 줌
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));

                        } catch (IOException e) {
                            // io 오류
                            Toast.makeText(getApplicationContext(), "검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        } catch (IllegalArgumentException illegalArgumentException) {
                            // 위도 경도 없을 때
                            Toast.makeText(getApplicationContext(), "검색에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }

                        break;
                    default:
                        // 기본 엔터키 동작
                        return false;
                }
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickMarker == "")
                {
                    Toast.makeText(getApplicationContext(), "빨래방 위치를 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //다음 클래스로 이동
                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                    //빨래방 이름, 개수 전달
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("text",clickMarker);
                    bundle2.putString("num1",random1);
                    bundle2.putString("num2",random2);
                    bundle2.putString("num3",random3);
                    bundle2.putString("total",String.valueOf(totalRandom));
                    intent.putExtras(bundle2);

                    startActivity(intent);

                }
            }
        });

    }


    // 권한
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i<permissions.length; i++)
        {
            permissionCheck = ContextCompat.checkSelfPermission(this,permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED)
            {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED)
        {
            // 권한 있음
            requestMyLocation();
        }
        else
        {
            // 권한 없음
            ActivityCompat.requestPermissions(this,permissions,1);
        }

    }

    // 권한
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1)
        {
            for(int i = 0; i < permissions.length; i++)
            {
                if (grantResults[i]==PackageManager.PERMISSION_GRANTED)
                {
                    // 권한 승인됨
                    requestMyLocation();
                }
                else
                {
                    // 승인 안됨, 아무것도 하지 않음
                }
            }
        }
    }


    //현재위치
    private void requestMyLocation() {
        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            long minTime = 0; // 갱신 시간
            float minDistance = 10; // 갱신 필요 최소 거리
            // 시간 말고 거리로 업데이트
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location)
                {
                    showCurrentLocation(location);
                }
                public void onStatusChanged(String s, int i, Bundle bundle) { }
                public void onProviderEnabled(String s) { }
                public void onProviderDisabled(String s) { }
            });
        } catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint = new LatLng(location.getLatitude(),location.getLongitude());

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPoint,15));

        Location targetLocation = new Location("");
        targetLocation.setLatitude(location.getLatitude());
        targetLocation.setLongitude(location.getLongitude());
        showMyMarker(targetLocation);
    }

    // 현재 내 위치 마커
    private void showMyMarker(Location targetLocation) {
        if (myMarker == null)

        {
            myMarker = new MarkerOptions();
            myMarker.position(new LatLng(targetLocation.getLatitude(),targetLocation.getLongitude()));
            myMarker.title("내 위치");

            BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.mylocation);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap,70,70,false);
            myMarker.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

            mMap.addMarker(myMarker);
        }
    }


    // 위치활성화 메세지
    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("GPS 설정");
        builder.setMessage("GPS가 비활성화 되어있습니다. \n활성화를 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "현재 위치를 사용하기 위해선\nGPS를 활성화 해야합니다.", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // 마커
        mMap = googleMap;

        LatLng clear1 = new LatLng(36.330385299999826, 127.46278929999984);
        LatLng clear2 = new LatLng(36.32794480000017, 127.45788239999959);
        LatLng clear3 = new LatLng(36.32703320000013, 127.4629706);
        LatLng clear4 = new LatLng(36.33550669999967, 127.44699039999978);
        LatLng clear5 = new LatLng(36.335064299999765,127.44428450000012);
        LatLng clear6 = new LatLng(36.33854290000003, 127.45826419999973);
        LatLng clear7 = new LatLng(36.33732629999974, 127.44874719999956);
        LatLng clear8 = new LatLng(36.33418030000015, 127.4514293999995);

        MarkerOptions makerOptions = new MarkerOptions();
        makerOptions
                .position(clear1)
                .title(name1);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear2)
                .title(name2);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear3)
                .title(name3);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear4)
                .title(name4);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear5)
                .title(name5);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear6)
                .title(name6);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear7)
                .title(name7);
        mMap.addMarker(makerOptions);

        makerOptions
                .position(clear8)
                .title(name8);
        mMap.addMarker(makerOptions);

        // 마커 클릭
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                clickMarker = "";
            }
        });

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        // 마커 클릭
        clickMarker = marker.getTitle();
        marker.showInfoWindow();
        LatLng latLng = marker.getPosition();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        return true;
    }

    // 랜덤 숫자
    public static String numberRandom(int len, int dupCd) {
        Random random = new Random();
        String numStr = "";

        for(int i=0; i<len; i++){
            String rd = Integer.toString(random.nextInt(10));

            if(dupCd == 1){
                numStr += rd;
            }
            else if(dupCd == 2) {
                if(!numStr.contains(rd)) {
                    numStr += rd;
                }
                else {
                    i -= 1;
                }
            }
        }
        return numStr;
    }

    // 뒤로가기 키
    public void onBackPressed()
    {
        finishAffinity();
        System.runFinalization();
        System.exit(0);
    }

}