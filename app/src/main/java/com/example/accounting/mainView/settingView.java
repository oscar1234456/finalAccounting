package com.example.accounting.mainView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.accounting.Member;
import com.example.accounting.R;

import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.NumberFormat;


/**
 * class: settingView
 *
 * used to state the setting page in the tabLayout
 */
public class settingView extends PageView {

    private Member user;
    TextView txvUsername,txvUseremail;
    ImageView imguser;


    /**
     * Constructor for settingView
     * @param context the root activity
     */
    public settingView(Context context,Member tempuser) {
        super(context);
        user = tempuser;
        //Create the new view object, and Inflate by the Inflater from context which you send
        //the root will be set to itself.
        View view = LayoutInflater.from(context).inflate(R.layout.setting_view, null);

        txvUseremail = view.findViewById(R.id.textUserMail);
        txvUsername = view.findViewById(R.id.textUserName);
        imguser = view.findViewById(R.id.userimg);

        txvUseremail.setText(user.showemail());
        txvUsername.setText(user.showName());
        new AsyncTask<String, Void, Bitmap>()
        {
            @Override
            protected Bitmap doInBackground(String... params)
            {
                String url = params[0];
                return getBitmapFromURL(url);
            }

            @Override
            protected void onPostExecute(Bitmap result)
            {
                imguser. setImageBitmap (result);
                super.onPostExecute(result);
            }
        }.execute(user.showPhoto());

        //imguser.setImageBitmap(getBitmapFromURL(user.showPhoto()));

        //add view to current viewGroup (RelativeLayout)
        addView(view);
    }
    @Override
    public void refreshView() {

    }

    public void getUserData(Member tempUser){
        user = tempUser;
    }



    public static Bitmap getBitmapFromURL(String imageUrl){
        try
        {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }



}
