package my.cleanhajo_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity     {

    Payment_Fragment paymentFragment;
    Pswd_Fragment pswdFragment;
    TextView textViewPayment, textViewText;
    String text,sell;
    int wash, water, tal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        // 상단바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        paymentFragment = (Payment_Fragment) getSupportFragmentManager().findFragmentById(R.id.mainFragment);
        pswdFragment = new Pswd_Fragment();

        textViewPayment = findViewById(R.id.textView_payment);
        textViewText = findViewById(R.id.textView5);

        // 데이터
        Intent getStirng = getIntent();
        Bundle bundle3 = getStirng.getExtras();

        text = bundle3.getString("text");
        textViewText.setText(text);
        sell = bundle3.getString("sell");
        textViewPayment.setText(sell);
        wash = bundle3.getInt("wash");
        water = bundle3.getInt("water");
        tal = bundle3.getInt("tal");
    }

    // 프래그먼트 이동
    public void onFragmentChange(int i){
        if (i==0){
            getSupportFragmentManager().beginTransaction().replace(R.id.container, pswdFragment).commit();
        }
        else if(i==1){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            textViewText.setText(text);
            textViewPayment.setText(sell);

            transaction.replace(R.id.container, paymentFragment);
            transaction.commit();
        }

    }

    public void intentValue()
    {
        Intent intent3 = new Intent(getApplicationContext(), MainActivity4.class);

        Bundle bundle4 = new Bundle();
        bundle4.putInt("wash",wash);
        bundle4.putInt("water",water);
        bundle4.putInt("tal",tal);
        intent3.putExtras(bundle4);

        startActivity(intent3);
    }

}