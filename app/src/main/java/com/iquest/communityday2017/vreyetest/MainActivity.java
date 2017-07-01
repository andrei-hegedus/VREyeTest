package com.iquest.communityday2017.vreyetest;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.pano.VrPanoramaEventListener;

import java.util.Locale;

public class MainActivity extends Activity {

    private VREyeTestView vrEyeTestView;

    public boolean loadImageSuccessful;

    private TextToSpeech textToSpeech;
    private Bundle params = new Bundle();
    private String startTest = "Welcome to VR eye test. Your tests will start shortly. Please follow the instructions.";
    private String endTest = "Thank you for taking this test. Your result will be shown shortly.";
    private String firstLine = "Please read the characters on the first line. Once you reach the end of the line please wait for further instructions.";
    private String secondLine = "Please read the characters on the second line. Once you reach the end of the line please wait for further instructions.";
    private String thirdLine = "Please read the characters on the third line. Once you reach the end of the line please wait for further instructions.";
    private String fourthLine = "Please read the characters on the fourth line. Once you reach the end of the line please wait for further instructions.";
    private String fifthLine = "Please read the characters on the fifth line. Once you reach the end of the line please wait for further instructions.";
    private String sixthLine = "Please read the characters on the sixth line. Once you reach the end of the line please wait for further instructions.";
    private String seventhLine = "Please read the characters on the seventh line. Once you reach the end of the line please wait for further instructions.";
    private String eighthLine = "Please read the characters on the eighth line. Once you reach the end of the line please wait for further instructions.";
    private String ninthLine = "Please read the characters on the ninth line. Once you reach the end of the line please wait for further instructions.";
    private String tenthLine = "Please read the characters on the tenth line. Once you reach the end of the line please wait for further instructions.";
    private String testResult = "Your percentage for this line was ";
    private String goodVision = "You have a good vision.";
    private String fairVision = "You have a fair vision. You should consult a doctor.";
    private String badVision = "You have a poor vision. Consult a doctor as soon as possible.";
    private VRSpeechRecognizer vrSpeechRecognizer;
    private boolean startListening = true;
    private TestProgress currentTestProgress = TestProgress.TEST_START;


    private enum TestProgress {
        TEST_START,
        SECOND_LINE,
        THIRD_LINE,
        FOURTH_LINE,
        FIFTH_LINE,
        SIXTH_LINE,
        SEVENTH_LINE,
        EIGHTH_LINE,
        NINTH_LINE,
        TENTH_LINE,
        END_TEST
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vrEyeTestView = (VREyeTestView) findViewById(R.id.pano_view);
        vrEyeTestView.setEventListener(new ActivityEventListener());
        vrEyeTestView.setEyeTest(VREyeTestView.EyeTest.STEREO_EYE_TEST);

        initializeTextToSpeech();
        initializeSpeechToText();

        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        playInstructions(currentTestProgress);
                    }
                }
                , 5000);
    }

    private void initializeSpeechToText() {
        vrSpeechRecognizer = new VRSpeechRecognizer(this, new MainActivity.Listener());
    }

    private void initializeTextToSpeech() {
        params.putInt(TextToSpeech.Engine.KEY_PARAM_STREAM,
                AudioManager.STREAM_MUSIC);
        params.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, 100);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (startListening) {
                            Log.d("Test", "startListening");
                            vrSpeechRecognizer.startListening();
                        } else if (currentTestProgress != TestProgress.END_TEST) {
                            Log.d("Test", "play next");
                            playNextInstruction();
                        }
                    }
                });

            }

            @Override
            public void onError(String utteranceId) {

            }
        });

    }

    private void playInstructions(TestProgress testProgress) {
        switch (testProgress) {
            case TEST_START:
                textToSpeech.speak(startTest + firstLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case SECOND_LINE:
                textToSpeech.speak(secondLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case THIRD_LINE:
                textToSpeech.speak(thirdLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case FOURTH_LINE:
                textToSpeech.speak(fourthLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case FIFTH_LINE:
                textToSpeech.speak(fifthLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case SIXTH_LINE:
                textToSpeech.speak(sixthLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case SEVENTH_LINE:
                textToSpeech.speak(seventhLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case EIGHTH_LINE:
                textToSpeech.speak(eighthLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case NINTH_LINE:
                textToSpeech.speak(ninthLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case TENTH_LINE:
                textToSpeech.speak(tenthLine, TextToSpeech.QUEUE_FLUSH, params, "test");
                startListening = true;
                break;
            case END_TEST:
                textToSpeech.speak(endTest, TextToSpeech.QUEUE_FLUSH, params, "test");
                break;
            default:
                break;
        }
    }


    @Override
    protected void onPause() {
        vrEyeTestView.pauseRendering();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrEyeTestView.resumeRendering();
        vrEyeTestView.setTransitionViewEnabled(true);
        vrEyeTestView.setDisplayMode(VrWidgetView.DisplayMode.FULLSCREEN_STEREO);
    }


    @Override
    protected void onDestroy() {
        // Destroy the widget and free memory.
        vrEyeTestView.shutdown();
        vrSpeechRecognizer.destroy();
        super.onDestroy();
    }


    /**
     * Listen to the important events from widget.
     */
    private class ActivityEventListener extends VrPanoramaEventListener {
        /**
         * Called by pano widget on the UI thread when it's done loading the image.
         */
        @Override
        public void onLoadSuccess() {
            loadImageSuccessful = true;
        }

        /**
         * Called by pano widget on the UI thread on any asynchronous error.
         */
        @Override
        public void onLoadError(String errorMessage) {
            loadImageSuccessful = false;
            Toast.makeText(MainActivity.this, "Error loading pano: " + errorMessage, Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void showResults(int percentage) {
        Log.d("showResults: ", "" + percentage);
        startListening = false;

        if (percentage >= 80) {
            Log.d("speak", "80");
            textToSpeech.speak(testResult + percentage + "   " + goodVision, TextToSpeech.QUEUE_FLUSH, params, "test");
        } else if (percentage < 80 && percentage >= 50) {
            Log.d("speak", "50");
            textToSpeech.speak(testResult + percentage + "   " + fairVision, TextToSpeech.QUEUE_FLUSH, params, "test");
        } else {
            Log.d("speak", "0");
            textToSpeech.speak(testResult + percentage + "  " + badVision, TextToSpeech.QUEUE_FLUSH, params, "test");
        }
    }

    private void playNextInstruction() {
        int ordinal = currentTestProgress.ordinal();
        TestProgress[] val = TestProgress.values();
        currentTestProgress = val[ordinal + 1];

        Log.d("TEST", currentTestProgress.name());

        playInstructions(currentTestProgress);
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
            Log.d("TEST", speech);
            EyeTest eyeTest = new EyeTest();
            int verificationResult = eyeTest.verify(speech, 0);
            showResults(verificationResult);
        }

        @Override
        public void onError(int error) {
            Toast.makeText(MainActivity.this, "Error occurred: " + error, Toast.LENGTH_SHORT).show();
        }
    }
}
