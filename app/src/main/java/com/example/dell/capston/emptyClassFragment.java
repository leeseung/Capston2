package com.example.dell.capston;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2016-05-15.
 */

public class emptyClassFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {


        public static final String ARG_PAGE = "ARG_PAGE";
         RadioGroup radioGroup;
        RadioButton radioButton_Mon, radioButton_Tue, radioButton_Web, radioButton_Tur, radioButton_Fri, radioButton_Sat;
    //LinearLayout linearLayout_Mon, linearLayout_Tue, linearLayout_Web, linearLayout_Tur, linearLayout_Fri, linearLayout_Sat;
         TextView timetable;
        // Button button;

        String Mon,Tuse,Wends,Thur,Fri,Satur;



        private int mPage;

        public static emptyClassFragment newInstance(int page) {
            Bundle args = new Bundle();
            args.putInt(ARG_PAGE, page);
            emptyClassFragment fragment = new emptyClassFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mPage = getArguments().getInt(ARG_PAGE);
        }

        // Inflate the fragment layout we defined above for this fragment
        // Set the associated text for the title
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.empty_room_main, container, false);

            final MyApplication myApplication = MyApplication.instance();

            radioGroup = (RadioGroup) view.findViewById(R.id.rGroup);
            radioButton_Mon = (RadioButton) view.findViewById(R.id.rButton_Mon);
            radioButton_Tue = (RadioButton) view.findViewById(R.id.rButton_Tue);
            radioButton_Web = (RadioButton) view.findViewById(R.id.rButton_Web);
            radioButton_Tur = (RadioButton) view.findViewById(R.id.rButton_Tur);
            radioButton_Fri = (RadioButton) view.findViewById(R.id.rButton_Fri);
            radioButton_Sat = (RadioButton) view.findViewById(R.id.rButton_Sat);

            timetable = (TextView) view.findViewById(R.id.TimetableView);
            radioGroup.setOnCheckedChangeListener(this);




            Mon = myApplication.JINRIMonClassRoom;
            Tuse = myApplication.JINRItuseClassRoom;
            Wends = myApplication.JINRIwensClassRoom;
            Thur = myApplication.JINRIthursClassRoom;
            Fri = myApplication.JINRIfriClassRoom;
            Satur = myApplication.JINRIsaturClassRoom;

            /*button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    switch(radioGroup.getCheckedRadioButtonId()) {
                        case R.id.rButton_Mon:
                            timetable.setText(myApplication.JINRIMonClassRoom);
                            break;
                        case R.id.rButton_Tue:
                            timetable.setText(myApplication.JINRItuseClassRoom);
                            break;
                        case R.id.rButton_Web:
                            timetable.setText(myApplication.JINRIwensClassRoom);
                            break;
                        case R.id.rButton_Tur:
                            timetable.setText(myApplication.JINRIthursClassRoom);
                            break;
                        case R.id.rButton_Fri:
                            timetable.setText(myApplication.JINRIfriClassRoom);
                            break;
                        case R.id.rButton_Sat:
                            timetable.setText(myApplication.JINRIsaturClassRoom);
                            break;
                    }
                }
            });*/

            return view;

        }

    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(group.getCheckedRadioButtonId()) {
            case R.id.rButton_Mon:
               /* Log.e("로그",Mon);
                Log.e("로그","1");*/
                timetable.setText(Mon);
                break;
            case R.id.rButton_Tue:
                timetable.setText(Tuse);
                break;
            case R.id.rButton_Web:
                timetable.setText(Wends);
                break;
            case R.id.rButton_Tur:
                timetable.setText(Thur);
                break;
            case R.id.rButton_Fri:
                timetable.setText(Fri);
                break;
            case R.id.rButton_Sat:
                timetable.setText(Satur);
                break;
        }
        }




}
