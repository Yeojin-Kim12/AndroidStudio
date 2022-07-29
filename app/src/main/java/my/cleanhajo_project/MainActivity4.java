package my.cleanhajo_project;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import android.widget.Toast;

public class MainActivity4 extends AppCompatActivity {
    TextView textView, tiemText;
    RecyclerView mRecyclerView;
    boolean finished;
    int wash, water, tal;

    // 선언 및 초기화
    public long START_TIME_IN_MISSIS1 = 0;
    public long START_TIME_IN_MISSIS2 = 0;
    public long START_TIME_IN_MISSIS3 = 0;

    private long mTimeLeftInMillis1, mTimeLeftInMillis2, mTimeLeftInMillis3;
    // 리스트에 넣기
    private Context context;
    private ArrayList<Timer> mArrayList;
    TimerAdapter adapter;
    int minutes1, minutes2, minutes3, second1, second2, second3;

    private boolean mTimerRunning;
    private CountDownTimer mCountDownTimer;

    public long mTimeLeftInMillis;
    static String timeLeftFormatted1, timeLeftFormatted2, timeLeftFormatted3, timeLeftFormatted4, timeLeftFormatted5, timeLeftFormatted6;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        textView = findViewById(R.id.textView2);
        button = findViewById(R.id.button);
        mRecyclerView = findViewById(R.id.recyclerView);

        mArrayList = new ArrayList<>();
        adapter = new TimerAdapter(context, mArrayList);
        mRecyclerView.setAdapter(adapter);

        // 상단바 없애기.
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 깜빡임 없애기
        RecyclerView.ItemAnimator animator = mRecyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.timer_item, null);
        tiemText = view.findViewById(R.id.Time_w);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        // 데이터
        Intent getStirng = getIntent();
        Bundle bundle = getStirng.getExtras();

        wash = bundle.getInt("wash");
        START_TIME_IN_MISSIS1 = wash;
        water = bundle.getInt("water");
        START_TIME_IN_MISSIS2 = water;
        tal = bundle.getInt("tal");
        START_TIME_IN_MISSIS3 = tal;

        START_TIME_IN_MISSIS1 = 0;
        START_TIME_IN_MISSIS2 = 0;
        START_TIME_IN_MISSIS3 = 15000;

        // 시간
        mTimeLeftInMillis = START_TIME_IN_MISSIS1 + START_TIME_IN_MISSIS2 + START_TIME_IN_MISSIS3;
        mTimeLeftInMillis1 = START_TIME_IN_MISSIS1;
        mTimeLeftInMillis2 = START_TIME_IN_MISSIS2;
        mTimeLeftInMillis3 = START_TIME_IN_MISSIS3;

        minutes1 = ((int) mTimeLeftInMillis1 / 1000) / 60;
        minutes2 = ((int) mTimeLeftInMillis2 / 1000) / 60;
        minutes3 = ((int) mTimeLeftInMillis3 / 1000) / 60;
        second1 = ((int) mTimeLeftInMillis1 / 1000) % 60;
        second2 = ((int) mTimeLeftInMillis2 / 1000) % 60;
        second3 = ((int) mTimeLeftInMillis3 / 1000) % 60;

        timeLeftFormatted1 = "" + minutes1;
        timeLeftFormatted2 = "" + minutes2;
        timeLeftFormatted3 = "" + minutes3;
        timeLeftFormatted4 = "" + second1;
        timeLeftFormatted5 = "" + second2;
        timeLeftFormatted6 = "" + second3;

        //리스트에 넣음
        mArrayList.add(new Timer(R.drawable.wash, "세탁", timeLeftFormatted1 + "분", timeLeftFormatted4 + "초"));
        mArrayList.add(new Timer(R.drawable.water, "헹굼", timeLeftFormatted2 + "분", timeLeftFormatted5 + "초"));
        mArrayList.add(new Timer(R.drawable.taltal, "탈수", timeLeftFormatted3 + "분", timeLeftFormatted6 + "초"));

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // 타이머 start
        startTimer();
        startTimer1();

        //버튼 비활성화
        button.setEnabled(false);
        finished = false;
    }

    // 세탁 진행 시간
    private void startTimer1() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis1, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis1 = millisUntilFinished;
                changed1();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                startTimer2();
            }
        }.start();
        mTimerRunning = true;
    }

    // 헹굼 진행 시간
    private void startTimer2() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis2, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis2 = millisUntilFinished;
                changed2();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                startTimer3();
            }
        }.start();
        mTimerRunning = true;
    }

    // 탈수 진행 시간
    private void startTimer3() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis3, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis3 = millisUntilFinished;
                changed3();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
            }
        }.start();
        mTimerRunning = true;
    }

    // 총 남은 시간 타이머
    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }
            @Override
            public void onFinish() {
                mTimerRunning = false;
                button.setEnabled(true);
                Toast.makeText(getApplicationContext(), "세탁이 완료되었습니다", Toast.LENGTH_LONG).show();

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }.start();
        mTimerRunning = true;
    }

    // 세탁 타이머
    public void changed1() {
        minutes1 = ((int) mTimeLeftInMillis1 / 1000) / 60;
        second1 = ((int) mTimeLeftInMillis1 / 1000) % 60;
        timeLeftFormatted1 = String.format(Locale.getDefault(), "%d분", minutes1);
        timeLeftFormatted4 = String.format(Locale.getDefault(), "%d초", second1);
        // 업데이트
        mArrayList.get(0).setTime(timeLeftFormatted1);
        mArrayList.get(0).setSecond(timeLeftFormatted4);
        adapter.notifyItemChanged(0);
    }

    // 헹굼 타이머
    public void changed2() {
        minutes2 = ((int) mTimeLeftInMillis2 / 1000) / 60;
        second2 = ((int) mTimeLeftInMillis2 / 1000) % 60;
        timeLeftFormatted2 = String.format(Locale.getDefault(), "%d분", minutes2);
        timeLeftFormatted5 = String.format(Locale.getDefault(), "%d초", second2);
        //업데이트
        mArrayList.get(1).setTime(timeLeftFormatted2);
        mArrayList.get(1).setSecond(timeLeftFormatted5);
        adapter.notifyItemChanged(1);
    }

    // 탈수 타이머
    public void changed3() {
        minutes3 = ((int) mTimeLeftInMillis3 / 1000) / 60;
        second3 = ((int) mTimeLeftInMillis3 / 1000) % 60;
        timeLeftFormatted3 = String.format(Locale.getDefault(), "%d분", minutes3);
        timeLeftFormatted6 = String.format(Locale.getDefault(), "%d초", second3);
        //업데이트
        mArrayList.get(2).setTime(timeLeftFormatted3);
        mArrayList.get(2).setSecond(timeLeftFormatted6);
        adapter.notifyItemChanged(2);
    }

    // 총 타이머
    public void updateCountDownText() {
        int hours = ((int)mTimeLeftInMillis / 1000) / 3600;
        int i = ((int)mTimeLeftInMillis / 1000) % 3600;
        int minutes = i / 60;
        int second = ((int)mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d시간 %02d분 %02d초", hours, minutes, second);
        textView.setText(timeLeftFormatted);
    }
}