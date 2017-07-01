package com.iquest.communityday2017.vreyetest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by robert.fejer on 7/1/17.
 */
public class VRSpeechRecognizer {

    private static final String TAG = VRSpeechRecognizer.class.getSimpleName();
    private Context context;
    private VRSpeechRecognizerListener vrSpeechRecognizerListener;
    private SpeechRecognizer speechRecognizer;

    public VRSpeechRecognizer(Context context, VRSpeechRecognizerListener vrSpeechRecognizerListener) {
        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        this.context = context;
        this.vrSpeechRecognizerListener = vrSpeechRecognizerListener;

        speechRecognizer.setRecognitionListener(new SpeechRecognitionListener());
    }

    public void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.iquest.communityday2017.vreyetest");

        speechRecognizer.startListening(intent);
    }

    public void destroy() {
        speechRecognizer.destroy();
    }

    private class SpeechRecognitionListener implements RecognitionListener {
        public void onReadyForSpeech(Bundle params) {
            Log.d(TAG, "onReadyForSpeech");
            vrSpeechRecognizerListener.onReadyForSpeech();
        }

        public void onBeginningOfSpeech() {
            Log.d(TAG, "onBeginningOfSpeech");
            vrSpeechRecognizerListener.onBeginningOfSpeech();
        }

        public void onRmsChanged(float rmsdB) {
            Log.d(TAG, "onRmsChanged");
        }

        public void onBufferReceived(byte[] buffer) {
            Log.d(TAG, "onBufferReceived");
        }

        public void onEndOfSpeech() {
            Log.d(TAG, "onEndOfSpeech");
            vrSpeechRecognizerListener.onEndOfSpeech();
        }

        public void onError(int error) {
            Log.d(TAG, "error " + error);
            vrSpeechRecognizerListener.onError(error);
        }

        public void onResults(Bundle results) {
            Log.d(TAG, "onResults " + results);
            ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            if (data != null && data.size() > 0) {
                vrSpeechRecognizerListener.onSpeechRecognized(data.get(0));
            }
            else {
                vrSpeechRecognizerListener.onSpeechRecognized("Speech not recognized!");
            }
        }

        public void onPartialResults(Bundle partialResults) {
            Log.d(TAG, "onPartialResults");
        }

        public void onEvent(int eventType, Bundle params) {
            Log.d(TAG, "onEvent " + eventType);
        }
    }
}
