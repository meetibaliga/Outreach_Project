package com.hammad.omar.outreach.Interfaces;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hammad.omar.outreach.Activities.AuthActivity;

public interface AuthState {

    void attemptAuth(Context context);
    int getExtraViewVisibility();
    String getSwitchTextViewText();
    String getAuthButtonText();
    float[] getExtraViewAlpha();
    int getForgetPassVisibily();
    String getTitle();
    int getTermsVisibility();

    class Login implements AuthState {

        private static final String TAG = Login.class.getSimpleName();

        @Override
        public int getExtraViewVisibility() {
            return View.GONE;
        }

        @Override
        public String getSwitchTextViewText() {
            return "No account? Click here to create one";
        }

        @Override
        public String getAuthButtonText() {
            return "SIGN IN";
        }

        @Override
        public float[] getExtraViewAlpha() {
            float[] values = {1f,0f};
            return values;
        }

        @Override
        public void attemptAuth(Context context) {
            Log.d(TAG,"attempting .. log in from interface ");
            AuthActivity activity = (AuthActivity) context;
            activity.attemptLogin();
        }

        @Override
        public int getForgetPassVisibily() {
            return View.VISIBLE;
        }

        @Override
        public String getTitle() {
            return "Sign in";
        }

        @Override
        public int getTermsVisibility() {
            return View.GONE;
        }
    }

    class Signup implements AuthState {

        private static final String TAG = Signup.class.getSimpleName();

        @Override
        public int getExtraViewVisibility() {
            return 0;
        }

        @Override
        public String getSwitchTextViewText() {
            return "Already have an account ? Click here to sign in";
        }

        @Override
        public String getAuthButtonText() {
            return "CREATE ACCOUNT";
        }

        @Override
        public float[] getExtraViewAlpha() {
            float[] values = {0f,1f};
            return values;
        }

        @Override
        public void attemptAuth(Context context) {
            Log.d(TAG,"attempting .. sign up from interface ");
            AuthActivity activity = (AuthActivity) context;
            activity.attemptSignup();
        }

        @Override
        public int getForgetPassVisibily() {
            return View.GONE;
        }

        @Override
        public String getTitle() {
            return "Sign up";
        }

        @Override
        public int getTermsVisibility() {
            return View.VISIBLE;
        }
    }
}



