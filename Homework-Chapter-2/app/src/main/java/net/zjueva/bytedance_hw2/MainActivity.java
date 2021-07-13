package net.zjueva.bytedance_hw2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="HW2";
    private static final int REQUEST_COUNT_CODE=0;


    private Button mPhoneButton;
    private Button mPracticeActivity;
    private Button mBaidu;
    private Button mSimplePage;
    private Button mRecyclePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"on create");
        setContentView(R.layout.activity_main);

        mPhoneButton=(Button)findViewById(R.id.btn_phone);
        mPracticeActivity=(Button)findViewById(R.id.btn_practiceActivity);
        mBaidu=(Button)findViewById(R.id.btn_baidu);
        mSimplePage=(Button)findViewById(R.id.btn_to_simplepage);
        mRecyclePage=(Button)findViewById(R.id.btn_recycler_view);

        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:15839882205"));
                startActivity(intent);
            }
        });

        mBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.baidu.com");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        mPracticeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent=new Intent(MainActivity.this,PracticeActivity.class);
                startActivityForResult(newIntent,REQUEST_COUNT_CODE);
            }
        });
        mSimplePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent=new Intent(MainActivity.this,SimpleActivity.class);
                startActivity(newIntent);
            }
        });
        mRecyclePage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent newIntent=new Intent(MainActivity.this,RecycleActivity.class);
                startActivity(newIntent);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_COUNT_CODE) {
            if (data == null) {
                return;
            }
            int count = PracticeActivity.getCount(data);
            Toast.makeText(MainActivity.this, "您一共点了" + count+"次", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"on start");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"on stop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"on destory");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"on pause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"on resume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"on restart");

    }
}
