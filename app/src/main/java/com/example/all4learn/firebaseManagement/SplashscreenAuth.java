package com.example.all4learn.firebaseManagement;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.all4learn.R;
import com.example.all4learn.notesManagement.ActivityNotes;
import com.example.all4learn.notesManagement.SchedulersHandler;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;
import java.util.List;

public class SplashscreenAuth extends AppCompatActivity {

    private static final long DELAY_MS = 1000;

    private static final int RC_SIGN_IN = 1;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private Handler main = SchedulersHandler.getHandler();


    private Runnable exitRunnable = () -> {
        if (firebaseAuth.getCurrentUser() != null) {
            startNotesActivity();
        } else {
            startAuth();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        main.postDelayed(exitRunnable, DELAY_MS);
    }

    private void startAuth() {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );


        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.AppTheme)
                        .setLogo(R.mipmap.ic_launcher)
                        .build(),
                RC_SIGN_IN
        );
    }

    private void startNotesActivity() {
        startActivity(ActivityNotes.createIntent(this));
        finish();
    }
}
