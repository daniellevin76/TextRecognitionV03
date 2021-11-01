package io.gritacademy.textrecognitionv3;

import static com.google.mlkit.vision.common.InputImage.fromBitmap;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

public class ReadInActivity extends AppCompatActivity {

    String text;
   // InputImage inputImage;
    ImageView imageView;
    private static final int CHOOSE_FILE_FROM_DEVICE = 101;
    TextToSpeech toSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_read);

        imageView = findViewById(R.id.imageView);


        Button readFileButton = findViewById(R.id.readButton);

        Button playFileButton = findViewById(R.id.playButton);

        playFileButton.setOnClickListener(v -> {

            try {

                recognizeText(getInputImageFromBitmap());

                Log.d("imageinfo", getInputImageFromBitmap().toString());
                Log.d("TAG", "***************************************");
                Log.d("text", text);
                // switchActivity(text);
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        readFileButton.setOnClickListener(v -> {
            mGetContent.launch("image/*");
            // inputImage = getInputImageFromBitmap();
        });

    }




/*
    private void switchActivity(String text) {
        Intent intent = new Intent(this, TextRecognitionActivity.class);
        intent.putExtra("text", text);
        startActivity(intent);
    }
*/




    public InputImage getInputImageFromBitmap() {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        return fromBitmap(bitmap, 0);
    }





    private void recognizeText(InputImage image){

        // [START get_detector_default]
        TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
        // [END get_detector_default]

        // [START run_detector]
        Task<Text> result =
                recognizer.process(image)
                        .addOnSuccessListener(visionText -> {

                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                Rect boundingBox = block.getBoundingBox();
                                Point[] cornerPoints = block.getCornerPoints();
                                text = block.getText();
                                for (Text.Line line : block.getLines()) {

                                    for (Text.Element element : line.getElements()) {

                                    }
                                }
                            }
                            // [END get_text]
                            // [END_EXCLUDE]
                        })
                        .addOnFailureListener(
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
        // [END run_detector]
    }





    private void processTextBlock(Text result) {
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
    }

    /*
public void chooseFileFromDevice(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_FILE_FROM_DEVICE);
    Log.d("TAG", "***+++++++++++++++++++++++++++++++++++++++++***");


}
*/

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri result) {

                    if (result != null) {
                        imageView.setImageURI(result);
                    }

                }
            });

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String pathStr = String.valueOf(data.getData());

            //Log.d("tag", pathStr);
        }
    }

}
