package net.zjueva.bytedance_hw1;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;
    private TextView mSDKVersionView;
    private EditText mPersonEditText;
    private ImageView mAvatarImg;
    private RadioGroup mStatusGroup;
    private ProgressBar mProgressShow;
    private TextView mProgressText;

    private static final String KEY_INDEX="index";
    private static final String TAG="HW1";
    private static final  String EXTRA_ANSWER_IS_TRUE="net.zjueva.android.answer_is_true";
    private static final int REQUEST_CODE_CHEAT=0 ;
    private boolean mIsCheater;


    private Question[]mQuestions=new Question[]{
            new Question(R.string.question_oceans,true),
            new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)
    };
    private int mCurrentIndex=0;


    private void updateProgress(int progressId,int delta){
        mProgressShow=(ProgressBar)findViewById(progressId);
        if(mProgressShow!=null){
            mProgressShow.incrementProgressBy(delta);
            int progressInt=mProgressShow.getProgress();
            mProgressText.setText(getString(R.string.progress_now,progressInt));
        }
        return;
    }
    private void updateQuestion(){
        int question=mQuestions[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        return;
    }
    private void checkAnswer(boolean usePress){
        boolean isRight=mQuestions[mCurrentIndex].isAnswerTrue();
        int messageId=0;
        if(mIsCheater){
            messageId=R.string.judgment_toast;
        } else if(usePress==isRight){
            messageId=R.string.correct_toast;
        } else{
            messageId=R.string.incorrect_toast;
        }
        Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
    }
    ActivityResultLauncher<Intent> mStartForResult= registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult  result) {
            if (result.getResultCode() == RESULT_OK) {
                Intent intent = result.getData();
                if(intent==null){
                    return;
                }
                mIsCheater=CheatActivity.wasAnswerShow(intent);

            }

        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");

        setContentView(R.layout.activity_main);

        mQuestionTextView=(TextView)findViewById(R.id.question_text_view);
        mSDKVersionView=(TextView)findViewById(R.id.version_sdk_int);
        mSDKVersionView.setText("API level "+ Build.VERSION.SDK_INT);

        mTrueButton=(Button)findViewById(R.id.true_button);
        mFalseButton=(Button)findViewById(R.id.false_button);
        mNextButton=(Button)findViewById(R.id.next_button);
        mCheatButton=(Button)findViewById(R.id.cheat_button);

        mPersonEditText=(EditText)findViewById(R.id.person_description);

        mAvatarImg=(ImageView)findViewById(R.id.person_avatar);
        mStatusGroup=(RadioGroup)findViewById(R.id.radio_group_status);

        mProgressShow=(ProgressBar)findViewById(R.id.progress_now);
        mProgressText=(TextView)findViewById(R.id.progress_text);

        mTrueButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG,"click true button");
                checkAnswer(true);
                updateProgress(R.id.progress_now,1);

            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                updateProgress(R.id.progress_now,1);

            }
        });
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG,"click next button");
                mCurrentIndex=(mCurrentIndex+1)%mQuestions.length;
                mIsCheater=false;
                updateQuestion();
                updateProgress(R.id.progress_now,1);
            }
        });
        mCheatButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG,"click cheat button");
                boolean answerIsTrue=mQuestions[mCurrentIndex].isAnswerTrue();
                Intent i=CheatActivity.newIntent(MainActivity.this,answerIsTrue);
                mStartForResult.launch(i);
                updateProgress(R.id.progress_now,2);


            }
        });

        mPersonEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: s = " + s + ", start = " + start +
                        ", count = " + count + ", after = " + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "onTextChanged: s = " + s + ", start = " + start +
                        ", before = " + before + ", count = " + count);
                updateProgress(R.id.progress_now,3);

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);

            }
        });
        mAvatarImg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d(TAG,"click avatar");
                updateProgress(R.id.progress_now,4);

            }

        });
        mStatusGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton mRadbtn = (RadioButton) findViewById(checkedId);
                Toast.makeText(getApplicationContext(), mRadbtn.getText(), Toast.LENGTH_LONG).show();
                Log.d(TAG,mRadbtn.getText().toString());
                updateProgress(R.id.progress_now,1);

            }
        });

        if(savedInstanceState!=null){
            mCurrentIndex=savedInstanceState.getInt(KEY_INDEX,0);
        }
        updateQuestion();
        updateProgress(R.id.progress_now,0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,"onStart() called");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"onStop() called");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestory() called");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,"onPause() called");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,"onResume() called");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG,"onSaveInstance");
        outState.putInt(KEY_INDEX,mCurrentIndex);
    }
}