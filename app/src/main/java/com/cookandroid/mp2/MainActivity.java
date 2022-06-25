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
    EditText editText_user; //사용자 답변
    TextView Question; // 질문
    Button analysis_text; // 버튼
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
                 // 자신이 쓴 텍스트 배열에 넣기
                //String textToAnalyze = send_text[num];
                if (TextUtils.isEmpty(q_a.Answer[num])) {
                    editText_user.setError("empty_text_error_msg");
                } else {
                    editText_user.setError(null);
                    analyzeSentiment(q_a.Answer[num]);
                    myRef.child("Results").child(id_LoginActivity).child(q_a.Question[num] + "="+q_a.Answer[num]);

                    num++;
                    if(num > 11){
                        Question.setText("상담질문이 끝났습니다. 제출버튼을 눌러주세요.");
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
        // 안드로이드 6.0버전 이상인지 체크해서 퍼미션 체크
        if(Build.VERSION.SDK_INT >= 23){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO},PERMISSION);
        }
        // RecognizerIntent 생성
        stt_intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        stt_intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName()); // 여분의 키
        stt_intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR"); // 언어 설정

        // TTS를 생성하고 OnInitListener로 초기화 한다.
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != ERROR) {
                    // 언어를 선택한다.
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
                mRecognizer = SpeechRecognizer.createSpeechRecognizer(MainActivity.this); // 새 SpeechRecognizer 를 만드는 팩토리 메서드
                mRecognizer.setRecognitionListener(listener); // 리스너 설정
                if(!recording) {
                    recording = true;
                    mRecognizer.startListening(intent); // 듣기 시작
                    record.setBackgroundResource(R.drawable.stop);
                }else{
                    recording = false;
                    mRecognizer.stopListening();
                    Toast.makeText(getApplicationContext(), "음성 기록을 중지합니다.", Toast.LENGTH_SHORT).show();
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
            Log.e("tag", "감정 분석 실패", e);
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
            // 말하기 시작할 준비가되면 호출
            Toast.makeText(getApplicationContext(),"음성인식 시작",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onBeginningOfSpeech() {
            // 말하기 시작했을 때 호출
        }

        @Override
        public void onRmsChanged(float rmsdB) {
            // 입력받는 소리의 크기를 알려줌
        }

        @Override
        public void onBufferReceived(byte[] buffer) {
            // 말을 시작하고 인식이 된 단어를 buffer에 담음
        }

        @Override
        public void onEndOfSpeech() {
            // 말하기를 중지하면 호출
        }

        @Override
        public void onError(int error) {
            // 네트워크 또는 인식 오류가 발생했을 때 호출
            String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "클라이언트 에러";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "퍼미션 없음";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "네트워크 에러";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "네트웍 타임아웃";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    message = "찾을 수 없음";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RECOGNIZER 가 바쁨";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "서버가 이상함";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "말하는 시간초과";
                    break;
                default:
                    message = "알 수 없는 오류임";
                    break;
            }
            Toast.makeText(getApplicationContext(), "에러 발생 : " + message,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {
            // 인식 결과가 준비되면 호출
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줌
            ArrayList<String> matches =
                    results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

            for(int i = 0; i < matches.size() ; i++){
                editText_user.setText(matches.get(i));
            }
        }
        @Override
        public void onPartialResults(Bundle partialResults) {
            // 부분 인식 결과를 사용할 수 있을 때 호출
        }
        @Override
        public void onEvent(int eventType, Bundle params) {
            // 향후 이벤트를 추가하기 위해 예약
        }
    };
}