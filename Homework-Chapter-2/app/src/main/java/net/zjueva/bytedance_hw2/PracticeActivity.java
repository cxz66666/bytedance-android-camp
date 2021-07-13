package net.zjueva.bytedance_hw2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {

    private Button btn_add_text;
    private LinearLayout ll_container;
    private int count = 0;
    private static final String TAG="HW2";
    private static final String ExtraCount="COUNT";



    public static int getCount(Intent result){
        return result.getIntExtra(ExtraCount,0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        Log.d(TAG,"practice start");
        initView();
    }
    private void initView(){
        btn_add_text = findViewById(R.id.btn_add_text);
        ll_container = findViewById(R.id.ll_container);


        findViewById(R.id.iv_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PracticeActivity.this, "Click Img", Toast.LENGTH_SHORT).show();
            }
        });
        btn_add_text.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                count++;
                Intent data=new Intent();
                data.putExtra(ExtraCount,count);
                setResult(RESULT_OK,data);
                TextView newTv=new TextView(PracticeActivity.this);
                newTv.setText("动态添加控件" + count);
                ll_container.addView(newTv);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"practicity stop");

    }
}
