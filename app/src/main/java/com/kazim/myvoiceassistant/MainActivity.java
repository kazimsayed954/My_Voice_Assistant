package com.kazim.myvoiceassistant;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Typeface typeface;
    private static final int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 1;
    private TextToSpeech tts;
    private SpeechRecognizer speechRecog;
    String gmail="com.google.android.gm";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//textView=findViewById(R.id.);
//typeface=Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
//textView.setTypeface(typeface);
//
       FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Permission is not granted
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.RECORD_AUDIO)) {
                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                    } else {
                        // No explanation needed; request the permission
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                } else {
                    // Permission has already been granted
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
                    speechRecog.startListening(intent);
                }
            }
        });

        initializeTextToSpeech();
        initializeSpeechRecognizer();
    }

    private void initializeSpeechRecognizer() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            speechRecog = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecog.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    List<String> result_arr = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    processResult(result_arr.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void processResult(String result_message) {
        result_message = result_message.toLowerCase();

//        Handle at least four sample cases

//        First: What is your Name?
//        Second: What is the time?
//        Third: Is the earth flat or a sphere?
//        Fourth: Open a browser and open url
        if(result_message.indexOf("what") != -1){
            if(result_message.indexOf("your name") != -1){
                speak("My Name is KARA ASSISTANT BOT. Nice to meet you!");
            }
            if (result_message.indexOf("time") != -1){
                String time_now = DateUtils.formatDateTime(this, new Date().getTime(),DateUtils.FORMAT_SHOW_TIME);
                speak("The time is now: " + time_now);
            }
        } else if (result_message.indexOf("tell me") != -1){
            if(result_message.indexOf("about yourself") != -1){
                speak("Hello my Name is KARA ASSISTANT BOT.i am your personal assistant . i have been developed by sayed mohd kazim mehdi my version is about 1.0 .nice to meet you ");
            }

        }
        else if ((result_message.indexOf("browser") != -1)||(result_message.indexOf("google chrome") != -1)){
            speak("Opening a browser right away master.");
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.co.in/"));
            startActivity(intent);
        }else if (result_message.indexOf("search") != -1){
            speak("Opening a browser right away master.");

            Intent intent=new Intent(this,webview.class);
            intent.putExtra("result",result_message);
            startActivity(intent);

           // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q="+result_message ));
           // startActivity(intent);
        }

        else if (result_message.indexOf("set") != -1){
            if(result_message.indexOf("set alarm") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.coloros.alarmclock");
                startActivity(intent);
            }
            if((result_message.indexOf("set reminder") != -1)){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.calendar");
                startActivity(intent);
            }

        }
        else if(result_message.indexOf("open") != -1){
            if(result_message.indexOf("gmail") != -1) {
                speak("Opening a gmail for You");
                Intent intent = getPackageManager().getLaunchIntentForPackage(gmail);
                startActivity(intent);
            }

            if(result_message.indexOf("telegram") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("org.telegram.messenger");
                startActivity(intent);
            }
            if(result_message.indexOf("divinepearls") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.namazandduas");
                startActivity(intent);
            }
            if((result_message.indexOf("camera") != -1)){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.oppo.camera");
                startActivity(intent);
            }
            if(result_message.indexOf("contacts") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.contacts");
                startActivity(intent);
            }
            if(result_message.indexOf("youtube") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                startActivity(intent);
            }
            if(result_message.indexOf("settings") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.settings");
                startActivity(intent);
            }
            if(result_message.indexOf("youtube") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.youtube");
                startActivity(intent);
            }

            if(result_message.indexOf("photos") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.coloros.gallery3d");
                startActivity(intent);
            }
            if((result_message.indexOf("maps") != -1) ||(result_message.indexOf("google maps")!=-1)){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
                startActivity(intent);
            }
            if(result_message.indexOf("messages") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.mms");
                startActivity(intent);
            }
            if(result_message.indexOf("google playstore") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.vending");
                startActivity(intent);
            }
            if(result_message.indexOf("files manager") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.coloros.filemanager");
                startActivity(intent);
            }
            if(result_message.indexOf("download manager") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.providers.downloads");
                startActivity(intent);
            }

            if(result_message.indexOf("fm radio") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.caf.fmradio");
                startActivity(intent);
            }
            if(result_message.indexOf("voice recorder") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.coloros.soundrecorder");
                startActivity(intent);
            }
            if(result_message.indexOf("video player") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.coloros.video");
                startActivity(intent);
            }
            if(result_message.indexOf("weather") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.coloros.weather.service");
                startActivity(intent);
            }
            if(result_message.indexOf("instagram") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.instagram.android");
                startActivity(intent);
            }
            if(result_message.indexOf("bluetooth") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.android.bluetooth");
                startActivity(intent);
            }
            if((result_message.indexOf("calendar") != -1) ||(result_message.indexOf("google calendar")!=-1)){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.calendar");
                startActivity(intent);
            }
            if(result_message.indexOf("shareit") != -1){

                Intent intent = getPackageManager().getLaunchIntentForPackage("com.lenovo.anyshare.gps");
                startActivity(intent);
            }



        }
    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0 ){
                    Toast.makeText(MainActivity.this, "Sorry There are No search engine is installed in your phone",Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    tts.setLanguage(Locale.getDefault());
                    speak("Hello there, I am ready to start our conversation");
                }
            }
        });
    }

    private void speak(String message) {
        if(Build.VERSION.SDK_INT >= 21){
            tts.speak(message,TextToSpeech.QUEUE_FLUSH,null,null);
        } else {
            tts.speak(message, TextToSpeech.QUEUE_FLUSH,null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.shutdown();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Reinitialize the recognizer and tts engines upon resuming from background such as after openning the browser
        initializeSpeechRecognizer();
        initializeTextToSpeech();
    }
}
