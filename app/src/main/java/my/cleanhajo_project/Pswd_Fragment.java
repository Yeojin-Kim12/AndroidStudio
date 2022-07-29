package my.cleanhajo_project;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Pswd_Fragment extends Fragment {

    EditText edittext[] = new EditText[4];
    Button buttonOK, buttonDel;
    Button button[] = new Button[10];
    String number;
    int j;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_pswd, container, false);

        edittext[0] = rootView.findViewById(R.id.editText1);
        edittext[1] = rootView.findViewById(R.id.editText2);
        edittext[2] = rootView.findViewById(R.id.editText3);
        edittext[3] = rootView.findViewById(R.id.editText4);
        button[0] = rootView.findViewById(R.id.button0);
        button[1] = rootView.findViewById(R.id.button1);
        button[2] = rootView.findViewById(R.id.button2);
        button[3] = rootView.findViewById(R.id.button3);
        button[4] = rootView.findViewById(R.id.button4);
        button[5] = rootView.findViewById(R.id.button5);
        button[6] = rootView.findViewById(R.id.button6);
        button[7] = rootView.findViewById(R.id.button7);
        button[8] = rootView.findViewById(R.id.button8);
        button[9] = rootView.findViewById(R.id.button9);
        buttonOK = rootView.findViewById(R.id.buttonOK);
        buttonDel = rootView.findViewById(R.id.buttonDel);

        buttonOK.setEnabled(false);
        edittext[0].requestFocus();

        // 숫자 버튼 누르면 editText에 해당 숫자 입력
        for (j = 0; j < 10; j++) {
            final int index;
            index = j;
            button[j].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (edittext[0].getText().toString().isEmpty()) {
                        number = edittext[0].getText().toString() + button[index].getText().toString();
                        edittext[0].setText(number);
                        edittext[0].setTransformationMethod(PasswordTransformationMethod.getInstance());
                        edittext[1].requestFocus();
                    }
                    else if(edittext[1].getText().toString().isEmpty()) {
                        number = edittext[1].getText().toString() + button[index].getText().toString();
                        edittext[1].setText(number);
                        edittext[1].setTransformationMethod(PasswordTransformationMethod.getInstance());
                        edittext[2].requestFocus();
                    }
                    else if (edittext[2].getText().toString().isEmpty()) {
                        number = edittext[2].getText().toString() + button[index].getText().toString();
                        edittext[2].setText(number);
                        edittext[2].setTransformationMethod(PasswordTransformationMethod.getInstance());
                        edittext[3].requestFocus();
                    }
                    else if (edittext[3].getText().toString().isEmpty()) {
                        number = edittext[3].getText().toString() + button[index].getText().toString();
                        edittext[3].setText(number);
                        edittext[3].setTransformationMethod(PasswordTransformationMethod.getInstance());
                        buttonOK.setEnabled(true);
                    }
                }
            });
        }


        buttonDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!edittext[3].getText().toString().isEmpty()) {
                    edittext[3].setText("");
                }
                else if (!edittext[2].getText().toString().isEmpty()) {
                    edittext[2].requestFocus();
                    edittext[2].setText("");
                }
                else if (!edittext[1].getText().toString().isEmpty()) {
                    edittext[1].requestFocus();
                    edittext[1].setText("");
                }
                else if (!edittext[0].getText().toString().isEmpty()) {
                    edittext[0].requestFocus();
                    edittext[0].setText("");
                }
            }
        });


        // 결제하기 버튼 누르면 Dialog 띄우기
        buttonOK.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // 비밀번호 : 5162
                if(edittext[0].getText().toString().equals("5") && edittext[1].getText().toString().equals("1") &&
                        edittext[2].getText().toString().equals("6") && edittext[3].getText().toString().equals("2")) {
                    Toast.makeText(getActivity(), "결제 성공!", Toast.LENGTH_LONG).show();
                    MainActivity3 mainActivity3 = (MainActivity3) getActivity();
                    mainActivity3.intentValue();
                }
                else {
                    Toast.makeText(getActivity(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                    edittext[0].setText("");
                    edittext[1].setText("");
                    edittext[2].setText("");
                    edittext[3].setText("");
                    edittext[0].requestFocus();
                }
            }
        });

        edittext[0].setInputType(0);
        edittext[1].setInputType(0);
        edittext[2].setInputType(0);
        edittext[3].setInputType(0);

        return rootView;
    }

}