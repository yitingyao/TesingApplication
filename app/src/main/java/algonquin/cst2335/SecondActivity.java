package algonquin.cst2335;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class SecondActivity extends AppCompatActivity {

    private static final String SHARED_PREF_NAME = "MyData";
    private static final String KEY_PHONE = "phone";
    TextView textView;
    EditText editTextPhone;
    Button buttonCallNumber;
    Button buttonChangePicture;
    SharedPreferences prefs;
    ImageView cameraImage;

    ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        //result holds what is coming back from the activity opened, in this case it would hold picture taken from camera
        if (result.getResultCode() == Activity.RESULT_OK) {
            //has to check if photo is taken or not
            Intent data = result.getData();
            //if photo is taken, getData will get photo
            Bitmap thumbnail = data.getParcelableExtra("data");
            //bitmap is android object that stores visuals
            cameraImage.setImageBitmap(thumbnail);
            //set the bitmap into imageview, the bitmap holds the visual
            saveImage(thumbnail);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textView = findViewById(R.id.textView);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonCallNumber = findViewById(R.id.buttonCallNumber);
        buttonChangePicture = findViewById(R.id.buttonChangePicture);
        cameraImage = findViewById(R.id.cameraImage);
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        textView.setText("Welcome back " + emailAddress);
        prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String phone = prefs.getString(KEY_PHONE, null);
        editTextPhone.setText(phone);
        buttonCallNumber.setOnClickListener(view -> {
            savePhoneNumber();
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:" + editTextPhone.getText().toString()));
            startActivity(call);
        });

        buttonChangePicture.setOnClickListener(view -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            cameraResult.launch(cameraIntent);

        });
        File file = new File(getFilesDir(), "Picture.png");

        if (file.exists()) {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            //pulling the file, extracting the bitmap for the old saved image
            cameraImage.setImageBitmap(theImage);

        }

    }

    private void savePhoneNumber() {
        SharedPreferences.Editor editor = prefs.edit();
        //declaring our editor
        //the editor edits the shared preferencces
        editor.putString(KEY_PHONE, editTextPhone.getText().toString());
        //getting the editEmail that was already set to the email user inputed
        //editor.putString(yao@hotmail.com) that stores the email I put as a shared preference so that SecondActivity can access
        //Then in SecondActivity we will get the value from our previous activity from our shared preferences
        //only putString is related to SharedPreferencews
        editor.apply();
    }

    private void saveImage(Bitmap mBitmap) {
        FileOutputStream fOut = null;
        try {
            fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
            mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}