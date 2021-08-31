package com.mr.chucknorris.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mr.chucknorris.R;
import com.mr.chucknorris.model.Joke;
import com.mr.chucknorris.services.JokesService;
import com.mr.chucknorris.services.ServiceGenerator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG  = MainActivity.class.getName();

    private ImageView ivImage;
    private TextView jokeText;
    private Button btnMoreJoke;

    private JokesService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setDataJoke();

    }

    private void setDataJoke() {
        ivImage = findViewById(R.id.iv_icon);
        jokeText = findViewById(R.id.tv_textJoke);
        btnMoreJoke = findViewById(R.id.btn_more);
        service = ServiceGenerator.createService(JokesService.class);

        moreJoke();
        btnMoreJoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreJoke();
            }
        });
    }

    private void moreJoke() {
        Call<Joke> jokeResponse = service.getRandomJoke();
        jokeResponse.enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                Joke joke = response.body();
                Glide.with(getApplicationContext())
                        .load(joke.getIconUrl()).into(ivImage);
                jokeText.setText(joke.getValue());
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                Log.e(TAG, t.toString());
                String message = "Failed to get more joke, please check your connection.";
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }


}