package com.iquest.communityday2017.vreyetest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * Created by robert.fejer on 7/1/17.
 */
public class EyeTestActivity extends Activity {

    private VRSpeechRecognizer vrSpeechRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_test_layout);

        vrSpeechRecognizer = new VRSpeechRecognizer(this, new Listener());
    }

    public void onListenBtnClicked(View view) {
        vrSpeechRecognizer.startListening();
    }

    @Override
    protected void onDestroy() {
        vrSpeechRecognizer.destroy();
        super.onDestroy();
    }

    private class Listener implements VRSpeechRecognizerListener {

        @Override
        public void onReadyForSpeech() {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onSpeechRecognized(String speech) {
            EyeTest eyeTest = new EyeTest();
            int verificationResult = eyeTest.verify(speech, 1);
            Toast.makeText(EyeTestActivity.this, "Verification result for: '" + speech + "' is " + verificationResult + "%", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(int error) {
            Toast.makeText(EyeTestActivity.this, "Error occurred: " + error, Toast.LENGTH_SHORT).show();
        }
    }
}
