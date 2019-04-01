package com.hammad.omar.outreach.Interfaces;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.hammad.omar.outreach.Activities.AuthActivity;
import com.hammad.omar.outreach.R;

public interface AuthState {

    void attemptAuth(Context context);
    int getExtraViewVisibility();
    String getSwitchTextViewText(Context context);
    String getAuthButtonText(Context context);
    float[] getExtraViewAlpha();
    int getForgetPassVisibily();
    String getTitle(Context context);
    int getTermsVisibility();

    class Login implements AuthState {

        private static final String TAG = Login.class.getSimpleName();

        @Override
        public int getExtraViewVisibility() {
            return View.GONE;
        }

        @Override
        public String getSwitchTextViewText(Context context) {
            return context.getResources().getString(R.string.noAccount);
        }

        @Override
        public String getAuthButtonText(Context context) {
            return context.getResources().getString(R.string.signInCap);
        }

        @Override
        public float[] getExtraViewAlpha() {
            float[] values = {1f,0f};
            return values;
        }

        @Override
        public void attemptAuth(Context context) {
            AuthActivity activity = (AuthActivity) context;
            activity.attemptLogin();
        }

        @Override
        public int getForgetPassVisibily() {
            return View.VISIBLE;
        }

        @Override
        public String getTitle(Context context) {
            return context.getResources().getString(R.string.signIn);
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
        public String getSwitchTextViewText(Context context) {
            return context.getResources().getString(R.string.already);
        }

        @Override
        public String getAuthButtonText(Context context) {
            return context.getResources().getString(R.string.createAccount);
        }

        @Override
        public float[] getExtraViewAlpha() {
            float[] values = {0f,1f};
            return values;
        }

        @Override
        public void attemptAuth(Context context) {
            AuthActivity activity = (AuthActivity) context;
            activity.attemptSignup();
        }

        @Override
        public int getForgetPassVisibily() {
            return View.GONE;
        }

        @Override
        public String getTitle(Context context) {
            return context.getResources().getString(R.string.createAnAccount);
        }

        @Override
        public int getTermsVisibility() {
            return View.VISIBLE;
        }
    }
}




