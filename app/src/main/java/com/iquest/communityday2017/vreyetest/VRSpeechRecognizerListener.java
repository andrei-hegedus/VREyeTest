com.iquest.communityday2017.vreyetest;

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
