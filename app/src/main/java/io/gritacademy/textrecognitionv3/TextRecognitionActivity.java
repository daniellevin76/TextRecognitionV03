package io.gritacademy.textrecognitionv3;

import static com.google.mlkit.vision.common.InputImage.fromBitmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.util.Locale;

public class TextRecognitionActivity extends AppCompatActivity {

    private EditText editTextMultiLine;
    private TextToSpeech toSpeech;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);


        editTextMultiLine = findViewById(R.id.editTextMultiLine);
        Button readBtn = findViewById(R.id.readButton);

        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){
           String inputText = bundle.getString("text");
           Log.d("Tag", inputText);
           // editTextMultiLine.setText(text);
        }

        /*
        readBtn.setOnClickListener(v -> {
            String toRead = editTextMultiLine.getText().toString();
            toSpeech.speak(toRead, TextToSpeech.QUEUE_FLUSH, null);

        });

         */
        toSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                try {
                    toSpeech.setLanguage(new Locale("en", "US"));
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("speak", "onInit: " + TextToSpeech.ERROR);
                }
            }
        });

    }

    /*
    private void processTextBlock (Text result) {
        // [START mlkit_process_text_block]
        String resultText = result.getText();
        for (Text.TextBlock block : result.getTextBlocks()) {
            String blockText = block.getText();
            Point[] blockCornerPoints = block.getCornerPoints();
            Rect blockFrame = block.getBoundingBox();
            for (Text.Line line : block.getLines()) {
                String lineText = line.getText();
                Point[] lineCornerPoints = line.getCornerPoints();
                Rect lineFrame = line.getBoundingBox();
                for (Text.Element element : line.getElements()) {
                    String elementText = element.getText();
                    Point[] elementCornerPoints = element.getCornerPoints();
                    Rect elementFrame = element.getBoundingBox();
                }
            }
        }
        // [END mlkit_process_text_block]
    }
*/

    @Override
    protected void onStop() {
        super.onStop();
        toSpeech.stop();
        toSpeech.shutdown();
    }
    }
