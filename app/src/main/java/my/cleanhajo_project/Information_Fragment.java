package my.cleanhajo_project;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Information_Fragment extends Fragment {

    TextView textView_Name_Information, textView_L_Information, textView_M_Information, textView_S_Information, text_All_Information;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_information, container, false);

        textView_Name_Information = rootView.findViewById(R.id.textView_Name_Information);
        textView_L_Information = rootView.findViewById(R.id.text_L_Information);
        textView_M_Information = rootView.findViewById(R.id.text_M_Information);
        textView_S_Information = rootView.findViewById(R.id.text_S_Information);
        text_All_Information = rootView.findViewById(R.id.text_all_Information);

        return rootView;
    }


}