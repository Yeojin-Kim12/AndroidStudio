package my.cleanhajo_project;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Payment_Fragment extends Fragment {

    private ViewGroup rootView;

    static final int SMS_SEND_PERMISSION = 1;
    String checkNum;
    String inputText;

    TextView textView_payment, textView_YMD, textView;
    EditText editTextPhone, editTextPassword;
    Button btn_accept, btn_check, btn_payment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_payment, container, false);

        textView_payment = rootView.findViewById(R.id.textView_payment);
        textView_YMD = rootView.findViewById(R.id.textView_YMD);
        editTextPhone = rootView.findViewById(R.id.editTextPhone);
        editTextPassword = rootView.findViewById(R.id.editTextPassword);
        btn_accept = rootView.findViewById(R.id.btn_accept);
        btn_check = rootView.findViewById(R.id.btn_check);
        btn_payment = rootView.findViewById(R.id.btn_payment);
        textView = rootView.findViewById(R.id.textView5);

        // 현재 날짜 가져오기
        long now = System.currentTimeMillis();
        Date date = new Date(now);

        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");
        String getDate = simpleDate.format(date);
        textView_YMD.setText(getDate);

        // '결제하기' 버튼 비활성화
        btn_payment.setEnabled(false);

        // 인증번호 일치 여부 판단
        SharedPreferences pref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        // SMS 권한 요청하기
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.SEND_SMS);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.SEND_SMS)) {
                Toast.makeText(getActivity(), "SMS 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            }
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, SMS_SEND_PERMISSION);
        }

        // 인증번호 받기 버튼
        btn_accept.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(editTextPhone.getText().toString())) {
                    Toast.makeText(getActivity(), "전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    checkNum = numberGen(6, 1);
                    inputText = editTextPhone.getText().toString();
                    editor.putString("checkNum", checkNum);
                    editor.commit();

                    sendSMS(inputText, null, "인증번호 : "+ checkNum, null,null);
                }
            }
        });


        // 인증번호 확인 버튼
        btn_check.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty((editTextPassword.getText().toString()))) {
                    Toast.makeText(getActivity(), "인증번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
                else {
                    if(pref.getString("checkNum", "").equals(editTextPassword.getText().toString())){
                        Toast.makeText(getActivity(), "인증이 완료 되었습니다.",Toast.LENGTH_LONG).show();
                        btn_payment.setEnabled(true);
                    }
                    else {
                        Toast.makeText(getActivity(), "인증번호가 일치하지 않습니다.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


        // 결제하기 버튼 누르면 Dialog 띄우기
        btn_payment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) { showMessage(); }
        });
        return rootView;
    }


    // SMS 내용
    private void sendSMS(String phoneNumber, String scAdress, String num, PendingIntent sentIntent, PendingIntent deliveryIntent) {
        PendingIntent pi = PendingIntent.getService(getActivity(), 0,
                new Intent(getActivity(),MainActivity3.class),PendingIntent.FLAG_IMMUTABLE);
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, num, pi, null);

        Toast.makeText(getActivity(), "인증번호가 발송되었습니다.",Toast.LENGTH_LONG).show();
    }

    // 랜덤 숫자 생성
    public static String numberGen(int len, int dupCd) {
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


    // 결제하기 버튼 누를 시 띄울 Dialog 내용
    private void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("정말 결제하시겠습니까?");
        builder.setMessage("결제 후 취소가 불가능합니다.");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity3 mainActivity3 = (MainActivity3) getActivity();
                mainActivity3.onFragmentChange(0);
            }
        });

        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getActivity(), "결제 취소", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}