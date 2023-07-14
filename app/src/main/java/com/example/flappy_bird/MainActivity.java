package com.example.flappy_bird;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.text.HtmlCompat;
import android.animation.ValueAnimator;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    private float velocity = 0,difference=0,height,lowerbound,upperbound,progress,width,translationX;
    private boolean change = true,first=true,first1=true,stop=false,start=false,firstap=true,play=true,musicplay=true;
    private ImageView bird,pipeu0,pipeb0,back0,back1,back2;
    private Space sp0,sp1;
    private ValueAnimator animator;
    private Animation flash;
    private Random rand;
    private TextView t,t1,t2;
    private Rect birdr,pipebr,pipeur,floor0r,floor1r,floor2r,sp1r;
    private int score=0,maxscore=0,r;
    private DisplayMetrics display;
    private Button restart;
    private ImageButton pauseplay,music,leave;
    private SharedPreferences.Editor editor;
    private MediaPlayer mp;
    public void screenTapped(View view) {
        if(!stop)
            velocity=-30;
        if(firstap)
        {
            start=true;
            firstap=false;
            pauseplay.setVisibility(View.VISIBLE);
            t2.setVisibility(View.GONE);
            flash.cancel();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rand= new Random();
        back0 = findViewById(R.id.imageView0);
        back1 = findViewById(R.id.imageView1);
        back2 = findViewById(R.id.imageView2);
        pipeb0 = findViewById(R.id.pipeb0);
        pipeu0 = findViewById(R.id.pipeu0);
        bird = findViewById(R.id.birdi);
        sp0= findViewById(R.id.space0);
        sp1 =findViewById(R.id.space1);
        t= findViewById(R.id.textView);
        t1= findViewById(R.id.textView1);
        t2=findViewById(R.id.textView2);
        flash = new AlphaAnimation(0.0f,1.0f);
        flash.setDuration(500);
        flash.setRepeatMode(Animation.REVERSE);
        flash.setRepeatCount(Animation.INFINITE);
        t2.startAnimation(flash);
        pauseplay =  findViewById(R.id.imageButton0);
        music =  findViewById(R.id.imageButton1);
        leave =  findViewById(R.id.imageButton2);
        r =rand.nextInt(40);
        animator = ValueAnimator.ofFloat(0.0f, -1.35f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(2000);
        birdr= new Rect();
        pipebr = new Rect();
        pipeur = new Rect();
        floor0r = new Rect();
        floor1r = new Rect();
        floor2r = new Rect();
        sp1r= new Rect();
        display = getResources().getDisplayMetrics();
        height= display.heightPixels;
        restart = findViewById(R.id.button0);
        SharedPreferences sharedp = getSharedPreferences("score", MODE_PRIVATE);
        editor = sharedp.edit();
        maxscore = sharedp.getInt("best",0);
        musicplay = sharedp.getBoolean("music",true);
        mp =MediaPlayer.create(this,R.raw.sillychipsong);
        mp.setLooping(true);
        mp.setVolume((float)0.25,(float)0.25);
        if(musicplay)
            mp.start();
        else
            music.setImageResource(R.drawable.musicoff);
        score=0;
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) sp0.getLayoutParams();
        leave.setOnClickListener(v -> {
            editor.putBoolean("music",musicplay);
            editor.apply();
            finish();
            System.exit(0);
        });
        pauseplay.setOnClickListener(v -> {
            if(play) {
                animator.pause();
                t.setVisibility(View.INVISIBLE);
                restart.setVisibility(View.VISIBLE);
                leave.setVisibility(View.VISIBLE);
                music.setVisibility(View.VISIBLE);
                pauseplay.setImageResource(R.drawable.play);
                String temp= "<font color=#fd7d6d>Score</font>"+"<br>"+score+"<br>"+"<font color=#fd7d6d>Best</font>"+"<br>"+maxscore;
                t1.setText(Html.fromHtml(temp, HtmlCompat.FROM_HTML_MODE_LEGACY));
                t1.setVisibility(View.VISIBLE);
                play=false;
            }
            else{
                t.setVisibility(View.VISIBLE);
                restart.setVisibility(View.GONE);
                leave.setVisibility(View.GONE);
                music.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                pauseplay.setImageResource(R.drawable.pause);
                play=true;
                animator.resume();
            }
        });
        music.setOnClickListener(view -> {
            if(musicplay) {
                mp.pause();
                music.setImageResource(R.drawable.musicoff);
                musicplay=false;
                editor.putBoolean("music",false);
            }
            else {
                mp.start();
                music.setImageResource(R.drawable.musicon);
                musicplay=true;
                editor.putBoolean("music",true);
            }
            editor.apply();
        });
        restart.setOnClickListener(v -> {
            score=0;
            t.setVisibility(View.VISIBLE);
            t.setText("0");
            start=false;
            stop=false;
            firstap=true;
            change = true;
            first=true;
            first1=true;
            play=true;
            velocity=0;
            pauseplay.setImageResource(R.drawable.pause);
            t1.setVisibility(View.GONE);
            restart.setVisibility(View.GONE);
            music.setVisibility(View.GONE);
            leave.setVisibility(View.GONE);
            pauseplay.setVisibility(View.GONE);
            animator.end();
            pipeu0.setVisibility(View.GONE);
            pipeb0.setVisibility(View.GONE);
            pipeb0.setTranslationY(0);
            pipeu0.setTranslationY(0);
            bird.setTranslationY(0);
            bird.setRotation(0);
            pipeb0.setTranslationX(0);
            pipeu0.setTranslationX(0);
            back0.setTranslationX(0);
            back1.setTranslationX(0);
            back2.setTranslationX(0);
            t2.setVisibility(View.VISIBLE);
            t2.startAnimation(flash);
            animator.start();
        });
        animator.addUpdateListener(animation -> {
            bird.getHitRect(birdr);
            pipeb0.getHitRect(pipebr);
            pipeu0.getHitRect(pipeur);
            back0.getHitRect(floor0r);
            back1.getHitRect(floor1r);
            back2.getHitRect(floor2r);
            if (!stop) {
                progress = (float) animation.getAnimatedValue();
                width = back0.getWidth();
                translationX = width * progress;
                back0.setTranslationX(translationX);
                back1.setTranslationX(translationX + width);
                back2.setTranslationX(translationX + 2 * width);
                if (start) {
                    pipeb0.setTranslationX(translationX);
                    pipeu0.setTranslationX(translationX);
                    if (change) {
                        r = rand.nextInt(40);
                        params.verticalBias = 0.5f + ((float) (r - 20) / (float) 100);
                        sp0.setLayoutParams(params);
                        pipeb0.setTranslationY(difference);
                        pipeu0.setTranslationY(-difference);
                        change = false;
                    }
                    if (progress < -1.29)
                        change = true;
                    if (first && progress < -1.3) {
                        pipeu0.setVisibility(View.VISIBLE);
                        pipeb0.setVisibility(View.VISIBLE);
                        first = false;
                    }
                    if (birdr.intersect(sp1r) || (!first && (birdr.intersect(pipebr) || birdr.intersect(pipeur)))) {
                        stop = true;
                        maxscore = Math.max(score, maxscore);
                        pauseplay.setVisibility(View.GONE);
                        editor.putInt("best", maxscore);
                        editor.apply();
                    }
                    if (progress < -1.2 && first1 && !first && progress > -1.3) {
                        score++;
                        t.setText(String.valueOf(score));
                        first1 = false;
                    }
                    if (progress > -0.1)
                        first1 = true;
                }
            }
            if (start) {
                if (birdr.intersect(floor0r) || birdr.intersect(floor1r) || birdr.intersect(floor2r)) {
                    animator.pause();
                    pauseplay.setVisibility(View.GONE);
                    t.setVisibility(View.INVISIBLE);
                    restart.setVisibility(View.VISIBLE);
                    leave.setVisibility(View.VISIBLE);
                    music.setVisibility(View.VISIBLE);
                    maxscore = Math.max(maxscore, score);
                    editor.putInt("best", maxscore);
                    editor.apply();
                    String temp = "<font color=#fd7d6d>Score</font>" + "<br>" + score + "<br>" + "<font color=#fd7d6d>Best</font>" + "<br>" + maxscore;
                    t1.setText(Html.fromHtml(temp, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    t1.setVisibility(View.VISIBLE);
                }
                float birdchange = bird.getTranslationY() + velocity;
                if (birdchange <= lowerbound && birdchange >= upperbound)
                    bird.setTranslationY(birdchange);
                else {
                    if (birdchange > 0)
                        bird.setTranslationY(lowerbound);
                    else
                        bird.setTranslationY(upperbound);
                }
                velocity += 1.5;
                if (!stop) {
                    if (velocity <= 0)
                        bird.setRotation((float) -45);
                    else if (velocity > 30)
                        bird.setRotation(Math.min(bird.getRotation() + 5, 90));
                } else
                    bird.setRotation(90);
            }
        });
        animator.start();
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        height=display.heightPixels - back0.getHeight();
        difference = (float) 2.8 * (float) bird.getHeight();
        lowerbound=(height/2)-((bird.getWidth()/(float)2)-3);
        upperbound=(-1*(height/2));
        sp1.getHitRect(sp1r);
        if(!hasFocus)
            mp.pause();
        else if(musicplay)
            mp.start();
    }
}