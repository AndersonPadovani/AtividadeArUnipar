package andersonpadovani.online;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.number.FormattedNumber;
import android.os.Bundle;
import android.text.BoringLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        getSupportActionBar().hide();
    }

    @Override
    protected void onResume() {
        super.onResume();

        final String[] acept = {"0"};

        EditText email = findViewById(R.id.edit_email);
        EditText passw = findViewById(R.id.edit_pass);
        EditText phone = findViewById(R.id.edit_phone);
        CheckBox checkBox = findViewById(R.id.checkBox_accept);
        Button btnCadastro = findViewById(R.id.btn_salvar);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acept = "0";

                if(checkBox.isChecked()){
                    acept = "1";
                }

                Api.insertUser(getApplicationContext(), email.getText().toString(), phone.getText().toString(), passw.getText().toString(), acept );
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String number = phone.getText().toString();
                String numberOnly= number.replaceAll("(\\d{2})(\\d{1})(\\d{4})(\\d{4})", "($1) $2 $3-$4");

                if (numberOnly.length() < 16){
                    phone.setError("Numero de telefone invalido!");
                }else{
                    phone.setText(numberOnly);
                }
            }
        });

        passw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(passw.length() < 4){
                    passw.setError("Senha muito curta!");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    email.setError("Email digitado é invalido!");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }

}