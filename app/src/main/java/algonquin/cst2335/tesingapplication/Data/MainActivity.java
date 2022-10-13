package algonquin.cst2335.tesingapplication.Data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import algonquin.cst2335.tesingapplication.R;
import algonquin.cst2335.tesingapplication.UI.MainViewModel;

public class MainActivity extends AppCompatActivity {
private MainViewModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        model = new ViewModelProvider(this).get(MainViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // comment

        Button button = findViewById(R.id.button);
        TextView textview = findViewById(R.id.textView);
        EditText edittext = findViewById(R.id.editText);
        RadioButton radiobutton1 = findViewById(R.id.radioButton1);
        RadioButton radiobutton2 = findViewById(R.id.radioButton2);
        RadioButton radiobutton3 = findViewById(R.id.radioButton3);

        button.setOnClickListener(( Button) ->
        {
            textview.setText("Your edit text has: " + edittext.getText());
            model.TextView.postValue(edittext.getText().toString());

        }
        );

        model.TextView.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });







    }
}