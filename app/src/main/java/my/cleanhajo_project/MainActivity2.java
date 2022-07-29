package my.cleanhajo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    Information_Fragment fragment_1;
    QR_Fragment fragment_2;

    TextView textView_Name_Information, textView_L_Information, textView_M_Information, textView_S_Information, text_All_Information;
    String text, sell;
    int wash, water, tal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        fragment_1 =(Information_Fragment) getSupportFragmentManager().findFragmentById(R.id.frag1);
        fragment_2 = new QR_Fragment();

        textView_Name_Information = findViewById(R.id.textView_Name_Information);
        textView_L_Information = findViewById(R.id.text_L_Information);
        textView_M_Information = findViewById(R.id.text_M_Information);
        textView_S_Information = findViewById(R.id.text_S_Information);
        text_All_Information = findViewById(R.id.text_all_Information);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 전달할 데이터를 받을 Intent
        Intent getStirng = getIntent();
        Bundle bundle2 = getStirng.getExtras();

        text = bundle2.getString("text");
        textView_Name_Information.setText(text);

        String numL = bundle2.getString("num1");
        textView_L_Information.setText(numL);

        String numM = bundle2.getString("num2");
        textView_M_Information.setText(numM);

        String numS = bundle2.getString("num3");
        textView_S_Information.setText(numS);

        String numAll = bundle2.getString("total");
        text_All_Information.setText(numAll);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.tab1:
                        // 정보 프레그먼트로 화면 전환
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                        textView_Name_Information.setText(text);
                        textView_L_Information.setText(numL);
                        textView_M_Information.setText(numM);
                        textView_S_Information.setText(numS);
                        text_All_Information.setText(numAll);
                        fragment_1.setArguments(bundle2);

                        transaction.replace(R.id.frag1, fragment_1);
                        transaction.commit();

                        break;
                    case R.id.tab2:
                        // QR 스캐너 실행
                        new IntentIntegrator(MainActivity2.this).initiateScan();
                        break;
                }
                return true;
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null)
        {
            // 스캔된 것이 없을 때
            if(result.getContents() == null)
            {
                Toast.makeText(this,"스캔이 취소되었습니다.",Toast.LENGTH_LONG).show();
            }
            else
            {
                try {
                    //data를 json으로 변환
                    JSONObject obj = new JSONObject(result.getContents());
                    sell = obj.getString("sell");
                    wash = Integer.parseInt(obj.getString("wash"));
                    water = Integer.parseInt(obj.getString("water"));
                    tal = Integer.parseInt(obj.getString("tal"));

                    //다음 클래스로 이동
                    Intent intent2 = new Intent(MainActivity2.this, MainActivity3.class);
                    Bundle bundle3 = new Bundle();
                    bundle3.putString("text",text);
                    bundle3.putString("sell",sell);
                    bundle3.putInt("wash",wash);
                    bundle3.putInt("water",water);
                    bundle3.putInt("tal",tal);
                    intent2.putExtras(bundle3);

                    startActivity(intent2);
                } catch (JSONException e) {
                    Toast.makeText(this,""+e,Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        }
        else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}