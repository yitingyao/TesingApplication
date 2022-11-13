package algonquin.cst2335;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {


    Button loginButton;
    EditText editEmail;
    SharedPreferences prefs;
    //SharedPreferences is telling the application that this is default and to save it, such that it's there you open it again
    private static final String SHARED_PREF_NAME = "MyData";
    private static final String KEY_LOGIN = "email";
    //these are private final because they can only be accessed by this class and won't change

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.loginButton);
        editEmail = findViewById(R.id.editEmail);
        loginButton.setOnClickListener(click -> {
            secondActivity();
        });

        prefs = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        //getSharedPreferences is an android method that looks for the sharedpreferences with the name given in the parameters
        //assigns mode to it, in this case private
        //all our SharedPreferences is stored in Android, and right now we just want those two to be stored in prefs

        String email = prefs.getString(KEY_LOGIN, null);
        //inside prefs look for variable KEY_LOGIN which holds string value "email" in our case
        //value of variable email, which is the email that user inputted and storing it in email


        editEmail.setText(email);
        //this sets the defaut login page each time you open to the email you had last time
    }

    public void secondActivity() {
        //secondActivity always means another page
        SharedPreferences.Editor editor = prefs.edit();
        //declaring our editor
        //the editor edits the shared preferencces
        editor.putString(KEY_LOGIN, editEmail.getText().toString());
        //getting the editEmail that was already set to the email user inputed
        //editor.putString(yao@hotmail.com) that stores the email I put as a shared preference so that SecondActivity can access
        //Then in SecondActivity we will get the value from our previous activity from our shared preferences
        //only putString is related to SharedPreferencews
        editor.apply();
        //need to call apply to save putString
        Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
        //Intent is a box (in this case emails) delivering this to SecondAcitivity
        nextPage.putExtra("EmailAddress", editEmail.getText().toString());
        //nextPage is delivering the stored email into SecondActivity
        startActivity(nextPage);
        //secondActivity triggers nextPage intent
        //purpose of this method is to switch from main activity to second activity
        //purpose of secondActivity.class is to take phone number input from user, and call phone number
        //we switch between activities to get to another app
        //An intent allows you to start an activity in another app by describing a simple action you'd like to perform
        //This intent says that you want to transition from MainActivity to SecondActivity. Then to actually make the transition, you call startActivity with the intent object:///
    }
}