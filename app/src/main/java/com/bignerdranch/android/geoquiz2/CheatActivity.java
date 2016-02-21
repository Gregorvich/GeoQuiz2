package com.bignerdranch.android.geoquiz2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.util.Log;

public class CheatActivity extends AppCompatActivity {

    private static final String TAG = "CheatActivity";
    private static final String KEY_CHEAT_STATUS="cheat_status";
    private boolean mCurrentStatus=false;

    /* key that is used for the public Intent putExtra(String name, boolean value) method, which
    is created so that QuizActivity is able to pass information to CheatActivity */
    private static final String EXTRA_ANSWER_IS_TRUE = "com.bignerdranch.android.geoquiz2.answer_is_true";

    /* key that is used for the extra that is sent back to QuizActivity in order for QuizActivity
    to know whether the user pressed the show answer button */
    private static final String EXTRA_ANSWER_SHOWN = "com.bignerdranch.android.geoquiz2.geoquiz2.answer_shown";

    private boolean mAnswerIsTrue;

    /* A member variable that is used for storing the boolean extra that is retrieved from
    QuizActivity */
    private TextView mAnswerTextView;
    private Button mShowAnswer;

    /*This method takes the package name that QuizActivity is in and whether the answer is true
    or not. It creates a new intent using the package name, and, for the second parameter
    in the intent, it sets the default class to CheatActivity. After this, it adds an extra
    to the intent, using the key created earlier and the boolean answerIsTrue. The boolean
    is retrieved from QuizActivity class, which is how QuizActivity is able to communicate
    with CheatActivity when it comes to whether the answer that CheatActivity is viewing
    is true or false. Finally, this method then returns the intent, thus successfully creating
    a "new intent."*/
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return i;
    }

    //Method that will be used by QuizActivity to retrieve the intent that was sent back to
    //QuizActivity. It will then retrieve the boolean value from this intent.
    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        /* Retrieves the intent that started CheatActivity, and gets the boolean value from
        that intent */
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);

        mShowAnswer = (Button) findViewById(R.id.show_answer_button);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                //Calls the method that tells QuizActivity whether the user cheated or not
                setAnswerShownResult(true);
                mCurrentStatus=true;
            }
        });
        if (savedInstanceState != null) {
            mCurrentStatus=savedInstanceState.getBoolean(KEY_CHEAT_STATUS, false);
            setAnswerShownResult(mCurrentStatus);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putBoolean(KEY_CHEAT_STATUS, mCurrentStatus);
    }

    //Creates a new intent with no parameters, adds an extra onto it that uses the answer shown
    //key and the "true" boolean, and sends this boolean to QuizActivity
    private void setAnswerShownResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

}
