package andersonpadovani.online;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
//    Url para acesso global das requisiçoes para api
    static String baseUrl = "https://andersonpadovani.online/api/api.php";

//    Função de comunicação com API para validação da entrada de dados do usuario com o banco de dados!
    public static void verificaLogin(Context mContext, String email, String passw){
        Intent HomePage = new Intent(mContext, HomePage.class);
        HomePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        OkHttpClient client = new OkHttpClient();

        Md5Hash md5 = new Md5Hash();

        RequestBody form = new FormBody.Builder()
                .add("type", "authUser")
                .add("email", email)
                .add("password", md5.md5(passw))
                .build();

        Request request = new Request.Builder()
                .url(baseUrl)
                .post(form)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    try {
                        String dataResp =  response.body().string();
                        String data = new JSONObject(dataResp).getString("data");
                        JSONObject apiResp = new JSONObject(data);


                        if(apiResp.getJSONObject("resp").getInt("status") == 200){

                            SharedPreferences sharedPref = mContext.getSharedPreferences(
                                    mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();

                            JSONObject dados = apiResp.getJSONObject("resp").getJSONObject("dados");
                            for (Iterator<String> it = dados.keys(); it.hasNext(); ) {
                                String k = it.next();
                                editor.putString(k, dados.getString(k));
                            }
                            editor.commit();

                            mContext.startActivity(HomePage);
                        }else{
                            toastMsgAlert(mContext, apiResp.getJSONObject("resp").getString("msg"));
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

        });

    };

    public static void insertUser(Context mContext, String email, String phone, String passw, String accept){
        Intent LoginPage = new Intent(mContext, LoginPage.class);
        LoginPage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        OkHttpClient client = new OkHttpClient();

        Md5Hash md5 = new Md5Hash();

        RequestBody form = new FormBody.Builder()
                .add("type", "insertUser")
                .add("email", email)
                .add("phone", phone)
                .add("password", md5.md5(passw))
                .add("acept", accept)
                .build();

        Request request = new Request.Builder()
                .url(baseUrl)
                .post(form)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String dataResp = response.body().string();

                try {
                    String data = new JSONObject(dataResp).getString("data");
                    JSONObject respApi = new JSONObject(data);

                    if(respApi.getJSONObject("resp").getInt("status") != 200){
                        toastMsgAlert(mContext, respApi.getJSONObject("resp").getString("msg"));

                    }else{
                        toastMsgAlert(mContext, respApi.getJSONObject("resp").getString("msg"));
                        mContext.startActivity(LoginPage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                Log.d("Resp", dataResp);
            }
        });

    }

    public static void selectAllUsers(Context mContext){

        OkHttpClient client = new OkHttpClient();

        RequestBody form = new FormBody.Builder()
                .add("type", "selectAllUser")
                .build();

        Request request = new Request.Builder()
                .url(baseUrl)
                .post(form)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                Intent HomePage = new Intent(mContext, HomePage.class);
                HomePage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                SharedPreferences sharedPref = mContext.getSharedPreferences(
                        mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();

                String data = response.body().string();

                editor.putString("selectAllUser", data);
                editor.commit();

//                    String dadosApi = new JSONObject(data).getString("data");
//                    JSONObject dados = new JSONObject(dadosApi).getJSONObject("resp");
//                    JSONArray arrayDados = dados.getJSONArray("dados");
//
//                    for (int i=0; i < arrayDados.length(); i++) {
//
//                        JSONObject respDados = new JSONObject(arrayDados.getString(i));
//
//                        for (Iterator<String> it = respDados.keys(); it.hasNext(); ) {
//                            String l = it.next();
//
//                            Log.d("selectAllUsers:", l + ": " +respDados.getString(l));
//
//
//                        }
//
//                    }

            }
        });

    }

    public static void toastMsgAlert(Context mContext, String msg){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
            }
        });
    }
}
