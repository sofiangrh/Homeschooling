package com.almanara.homeschool.exercises.speech;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.almanara.ali.homeschool.R;
import com.almanara.homeschool.Answer;
import com.almanara.homeschool.Constants;

import java.util.ArrayList;


/**
 * Created by Ali on 6/4/2017.
 */

public class Speech extends Activity{
    Answer answer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        answer = new Answer();
        Intent intent = getIntent();
        if(intent !=null){
            String answerString = intent.getStringExtra("Answer");
            answer.setAnswer(answerString.trim().toLowerCase());
        }
        start();
    }

    public void start(){
        Intent voicerecogize = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        voicerecogize.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getClass().getPackage().getName());
        voicerecogize.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        Log.v("Test","Answer" + answer.getAnswer());
        if(answer.getAnswer().matches("[a-zA-Z]+")){
            voicerecogize.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            Log.v("Test","English");

        }else {
            voicerecogize.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ar-EG");
            Log.v("Test","Arabic");
        }
        startActivityForResult(voicerecogize,33);

    }
    public void checkResult(ArrayList<String> results){
        boolean isCorrect =false;
        for (String word:results){
            if(word.equals(answer.getAnswer())){
                isCorrect =true;
                Intent intent = new Intent();
                intent.setData(Uri.parse(word));
                setResult(Constants.CORRECTANSWER,intent);
                finish();
                Log.v("talayabni","t3ala ya nela");
                Toast.makeText(this, word + getString(R.string.good), Toast.LENGTH_LONG).show();
            }
        }
        if(!isCorrect){
            Intent intent = new Intent();
            intent.setData(Uri.parse(results.toString()));
            setResult(Constants.WRONGANSWER,intent);
            finish();
           Toast.makeText(this,getString(R.string.try_again), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == 33){
                ArrayList<String> results = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                checkResult(results);
//                Toast.makeText(this, results.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
