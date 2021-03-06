package com.cookandroid.mp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.language.v1.CloudNaturalLanguage;
import com.google.api.services.language.v1.CloudNaturalLanguageRequest;
import com.google.api.services.language.v1.CloudNaturalLanguageScopes;
import com.google.api.services.language.v1.model.AnalyzeSentimentRequest;
import com.google.api.services.language.v1.model.AnnotateTextRequest;
import com.google.api.services.language.v1.model.Document;
import com.google.api.services.language.v1.model.Features;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import static android.speech.tts.TextToSpeech.ERROR;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;



public class MainActivity extends AppCompatActivity {
    EditText editText_user; //????????? ??????
    TextView Question; // ??????
    Button analysis_text; // ??????
    Button next;

    Button listen; // tts
    Button record; // stt
    boolean recording;
    //stt
    Intent stt_intent;
    SpeechRecognizer mRecognizer;
    final int PERMISSION = 1;
    //tts
    private TextToSpeech tts;



    private static final int LOADER_ACCESS_TOKEN = 1; // Token used to initiate the request loader
    private GoogleCredential mCredential = null; //GoogleCredential object so that the requests for NLP Api could be made

    public static Context mContext;
    Question_Answer q_a = new Question_Answer();
    private int num = 0;
    private int num2 = 0;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private String id_LoginActivity;

    //private String [] check_array = getString(check).split(" ");
    // A  Thread on which the Api request will be made and results will be delivered. As network calls cannot be made on the amin thread, so we are creating a separate thread for the network calls
    private Thread mThread;

    // Google Request for the NLP Api. This actually is acting like a Http client queue that will process each request and response from the Google Cloud server
    private final BlockingQueue<CloudNaturalLanguageRequest<? extends GenericJson>> mRequests
            = new ArrayBlockingQueue<>(3);


    // Api for CloudNaturalLanguage from the Google Client library, this is the instance of our request that we will make to analyze the text.
    private CloudNaturalLanguage mApi = new CloudNaturalLanguage.Builder(
            new NetHttpTransport(),
            JacksonFactory.getDefaultInstance(),
            new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    mCredential.initialize(request);
                }
            }).build();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Question = (TextView) findViewById(R.id.textView_question);
        analysis_text = (Button) findViewById(R.id.button_submit);
        editText_user = (EditText) findViewById(R.id.editText_send);
        next = (Button) findViewById(R.id.button_next);
        record = (Button) findViewById(R.id.record);
        listen = (Button) findViewById(R.id.listen);

        Intent intent = getIntent();
        id_LoginActivity = intent.getStringExtra("ID");
        prepareApi();
       Question.setText(q_a.Question[num]);

        analysis_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 q_a.Answer[num] = editText_user.getText().toString();
                 // ????????? ??? ????????? ????????? ??????
                //String textToAnalyze = send_text[num];
                if (TextUtils.isEmpty(q_a.Answer[num])) {
                    editText_user.setError("empty_text_error_msg");
                } else {
                    editText_user.setError(null);
                    analyzeSentiment(q_a.Answer[num]);
                    myRef.child("Results").child(id_LoginActivity).child(q_a.Question[num] + "="+q_a.Answer[num]);

                    num++;
                    if(num > 11){
                        Question.setText("??????????????? ???????????????. ??????????????? ???????????????.");
                        analysis_text.setEnabled(false);
                        record.setEnabled(false);
                        listen.setEnabled(false);

                    }else {
                        Question.setText(q_a.Question[num]);
                        editText_user.setText("");
                    }
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(),ChildResultActivity.class);
                startActivity(intent);
            }
        });

        //stt
        // ??????????????? 6.0?????? ???????????? ???????????? ????????? ??????
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }
        // RecognizerIntent ??????
        stt_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // ????????? ???
        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // ?????? ??????

        // TTS??? ???????????? OnInitListener??? ????????? ??????.
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // ????????? ????????????.
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(Question.getText().toString(),TextToSpeech.QUEUE_FLUSH, null);
            }
        });
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this); // ??? SpeechRecognizer ??? ????????? ????????? ?????????
                mRecognizer.setRecognitionListener(listener); // ????????? ??????
                if(!recording) {
                    recording = true;
                    mRecognizer.startListening(intent); // ?????? ??????
                    record.setBackgroundResource(R.drawable.stop);
                }else{
                    recording = false;
                    mRecognizer.stopListening();
                    Toast.makeText(getApplicationContext(), "?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    record.setBackgroundResource(R.drawable.record);

                }
            }
        });
        mContext = this;
    }
    public void analyzeSentiment(String text) {
        try {
            mRequests.add(mApi
                    .documents()
                    .analyzeSentiment(new AnalyzeSentimentRequest()
                            .setDocument(new Document()
                                    .setContent(text)
                                    .setType("PLAIN_TEXT"))));
        } catch (IOException e) {
            Log.e("tag", "?????? ?????? ??????", e);
        }
    }
    /**
     * Preparing the Cloud Api before maiking the actual request.
     * This method will actually initiate the AccessTokenLoader async task on completion
     * of which we will recieve the token that should be set in our request for Cloud NLP Api.
     */
    private void prepareApi() {
        // Initiate token refresh
        getSupportLoaderManager().initLoader(LOADER_ACCESS_TOKEN, null,
                new LoaderManager.LoaderCallbacks<String>() {
                    @Override
                    public Loader<String> onCreateLoader(int id, Bundle args) {
                        return new AccessTokenLoader(MainActivity.this);
                    }

                    @Override
                    public void onLoadFinished(Loader<String> loader, String token) {
                        setAccessToken(token);
                    }

                    @Override
                    public void onLoaderReset(Loader<String> loader) {
                    }
                });
    }


    /**
     * This method will set the token from the Credentials.json file to the Google credential object.
     * @param token -> token recieved from the Credentials.json file.
     */
    public void setAccessToken(String token) {
        mCredential = new GoogleCredential()
                .setAccessToken(token)
                .createScoped(CloudNaturalLanguageScopes.all());
        startWorkerThread();
    }


    /**
     * This method will actually initiate a Thread and on this thread we will execute our Api request
     * and responses.
     *
     * Responses recieved will be delivered from here.
     */
    private void startWorkerThread() {
        if (mThread != null) {
            return;
        }
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (mThread == null) {
                        break;
                    }
                    try {
                        // API calls are executed here in this worker thread
                        deliverResponse(mRequests.take().execute());
                    } catch (InterruptedException e) {
                        Log.e("TAG", "Interrupted.", e);
                        break;
                    } catch (IOException e) {
                        Log.e("TAG", "Failed to execute a request.", e);
                    }
                }
            }
        });
        mThread.start();
    }


    /**
     * this method will handle the response recieved from the Cloud NLP request.
     * The response is a JSON object only.
     * This has been casted to GenericJson from Google Cloud so that the developers can easily parse through the same and can understand the response.
     *
     *
     * @param response --> the JSON object recieved as a response for the cloud NLP Api request
     */
    private void deliverResponse(final GenericJson response) {
        Log.d("TAG", "Generic Response --> " + response);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(MainActivity.this, "Response Recieved from Cloud NLP API", Toast.LENGTH_SHORT).show();
                try {
                    q_a.Result_tx[num2] = response.toPrettyString();
                    String split =q_a.Result_tx[num2].substring(65 ,70);
                    q_a.Result_Score[num2] = Double.parseDouble(split);
                    myRef.child("Results").child(id_LoginActivity).child(q_a.Question[num2]).child(q_a.Answer[num2]).setValue(q_a.Result_Score[num2]);
                    num2++;
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override
        public void onReadyForSpeech(Bundle params) {
            // ????????? ????????? ??????????????? ??????
            Toast.makeText(getApplicationContext(),"???????????? ??????",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // ????????? ???????????? ??? ??????
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // ???????????? ????????? ????????? ?????????
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // ?????? ???????????? ????????? ??? ????????? buffer??? ??????
        }

        @Override
        public void onEndOfSpeech() {
            // ???????????? ???????????? ??????
        }

        @Override
        public void onError(int error) {
            // ???????????? ?????? ?????? ????????? ???????????? ??? ??????
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "??????????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "???????????? ??????";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "?????? ??? ??????";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER ??? ??????";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "????????? ?????????";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "????????? ????????????";
                    break;
                default:
                    message = "??? ??? ?????? ?????????";
                    break;
            }
            Toast.makeText(getApplicationContext(), "?????? ?????? : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // ?????? ????????? ???????????? ??????
            // ?????? ?????? ArrayList??? ????????? ?????? textView??? ????????? ?????????
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
                editText_user.setText(matches.get(i));
            }
        }
        @Override
        public void onPartialResults(Bundle partialResults) {
            // ?????? ?????? ????????? ????????? ??? ?????? ??? ??????
        }
        @Override
        public void onEvent(int eventType, Bundle params) {
            // ?????? ???????????? ???????????? ?????? ??????
        }
    };
}