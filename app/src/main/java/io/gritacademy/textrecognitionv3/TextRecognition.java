package io.gritacademy.textrecognitionv3;

import static com.google.mlkit.vision.common.InputImage.fromBitmap;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.DocumentsProvider;
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
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.latin.TextRecognizerOptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.BreakIterator;
import java.util.Locale;

public class TextRecognition extends AppCompatActivity{

    InputImage inputImage;
    ImageView imageView;
    private static final int CHOOSE_FILE_FROM_DEVICE = 101;
    TextToSpeech toSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognition);

        imageView = findViewById(R.id.imageView);

        Button readFileButton = findViewById(R.id.readButton);

        Button playFileButton = findViewById(R.id.playButton);

        playFileButton.setOnClickListener(v -> {

            try {
                inputImage = getInputImageFromBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        readFileButton.setOnClickListener(v -> {
          mGetContent.launch("image/*");
        // inputImage = getInputImageFromBitmap();
        });




    }

    public InputImage getInputImageFromBitmap(){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        return fromBitmap(bitmap,0);
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

                if(result != null){
                    imageView.setImageURI(result);
                }

            }
        });

    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            String pathStr = String.valueOf(data.getData());
            Log.d("TAG", "***************************************");
            Log.d("tag", pathStr);
        }
    }

    }
