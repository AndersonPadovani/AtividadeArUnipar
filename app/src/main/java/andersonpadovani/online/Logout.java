package andersonpadovani.online;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class Logout {
    public static void logout(Context mContext){
        Intent Login = new Intent(mContext, LoginPage.class);
        Login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SharedPreferences sharedPref = mContext.getSharedPreferences(
                mContext.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        sharedPref.edit().clear().commit();
        mContext.startActivity(Login);
    }
}
