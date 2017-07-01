package day.community.iq.com.myapplication;

/**
 * Created by robert.fejer on 7/1/17.
 */
public interface VRSpeechRecognizerListener {
    void onReadyForSpeech();

    void onBeginningOfSpeech();

    void onEndOfSpeech();

    void onSpeechRecognized(String speech);

    void onError(int error);
}
