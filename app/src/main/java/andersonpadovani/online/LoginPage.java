package andersonpadovani.online;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        getSupportActionBar().hide();

        Intent SignUpPage = new Intent(this, SignUpPage.class);


        EditText email = (EditText) findViewById(R.id.txt_nome);
        EditText passw = (EditText) findViewById(R.id.txt_passw);

        Button cadastro = findViewById(R.id.txt_cadastre_se);
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SignUpPage);
            }
        });

        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Api.verificaLogin(getApplicationContext(), email.getText().toString(), passw.getText().toString());
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        if(sharedPref.getString("email", "") != ""){
            Intent HomePage = new Intent(this, HomePage.class);
            startActivity(HomePage);
        }

    }
}