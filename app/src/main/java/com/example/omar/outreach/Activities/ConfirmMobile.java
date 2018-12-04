package com.example.omar.outreach.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.VerificationHandler;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Managers.AuthManager;
import com.example.omar.outreach.R;

import java.util.Timer;
import java.util.TimerTask;



public class ConfirmMobile extends AppCompatActivity {

    private static final String TAG = ConfirmMobile.class.getSimpleName();


    //activity
    Activity mThis = this;

    Button mConfirmButton;
    EditText mConfirmCodeView;
    TextView mMobileNumberView;

    // Auth
    AuthManager authManager;

    //flags
    boolean canResend = false;

    //constants
    final int TIME_TO_RESEND = 59;

    // temps
    int remainingTime = TIME_TO_RESEND;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_mobile);

        authManager = AuthManager.getInstance(this);

        // stupui
        setupUI();

        //setup listeners
        setupListeners();

    }

    @Override
    public void onBackPressed() {
        // do nothing
    }

    private void setupUI() {

        mConfirmButton = findViewById(R.id.sendButton);
        mConfirmCodeView = findViewById(R.id.codeView);
        mMobileNumberView = findViewById(R.id.mobileNumberView);

        //get mobile number from extras

        String mobileNumebr = getIntent().getExtras().getString("Mobile");

        if (TextUtils.isEmpty(mobileNumebr)) {
            goToMainScreen();
            return;
        } else {
            mMobileNumberView.setText(mobileNumebr);
        }

        // disable the button
        mConfirmButton.setEnabled(false);

        // resend code
        resetResendTimer();


    }

    private void setupListeners() {

        mConfirmCodeView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(mConfirmCodeView.getText().toString())){
                    mConfirmButton.setEnabled(true);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

    }

    public void confirmClicked(View view) {


        // get the text from the view

        final String code = mConfirmCodeView.getText().toString();

        if(TextUtils.isEmpty(code)){
            Toast.makeText(this,"Please write the code",Toast.LENGTH_SHORT).show();
            return;
        }

        // submit requests
        final String uid = getIntent().getStringExtra("uid");
        final CognitoUser user = authManager.getUser(uid);

        showProgress(true);

        final GenericHandler handler = new GenericHandler() {

            @Override
            public void onSuccess() {
                App.USER_ID = user.getUserId();
                goToMainScreen();
            }

            @Override
            public void onFailure(Exception exception) {

                showProgress(false);

                String errorMessage = "";

                if(exception instanceof AmazonServiceException){

                    AmazonServiceException awsException = (AmazonServiceException) exception;
                    errorMessage = awsException.getErrorMessage();

                }else if (exception instanceof  AmazonClientException){

                    AmazonClientException awsException = (AmazonClientException) exception;
                    errorMessage = awsException.getMessage();

                    if (!App.hasActiveInternetConnection(mThis)) {
                        errorMessage = "Make sure you are connected to the internet";
                    }

                }


                Log.d(TAG,errorMessage);
                TextView errorView = findViewById(R.id.errorText);
                errorView.setText(errorMessage);
                errorView.setVisibility(View.VISIBLE);

                mConfirmCodeView.setText(null);
                mConfirmCodeView.requestFocus();
            }
        };

        user.confirmSignUpInBackground(code,false,handler);

    }

    private void showProgress(boolean show){

        View prog = findViewById(R.id.progress);
        View confirmButton = findViewById(R.id.sendButton);
        View resendBtn = findViewById(R.id.resendCodeLabel);

        if(show){
            prog.setVisibility(View.VISIBLE);
            confirmButton.setVisibility(View.GONE);
            resendBtn.setVisibility(View.GONE);

        }else{
            prog.setVisibility(View.GONE);
            confirmButton.setVisibility(View.VISIBLE);
            resendBtn.setVisibility(View.VISIBLE);

        }

    }


    private void goToMainScreen() {

        Intent intent = new Intent(this,PermissionsActivity.class);
        startActivity(intent);

    }

    private void resetResendTimer(){

        canResend = false;
        remainingTime = TIME_TO_RESEND;
        final TextView resendCode = findViewById(R.id.resendCodeLabel);
        resendCode.setTextColor(Color.LTGRAY);
        final String baseText = "Resend Code After %d seconds";

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {

                Log.d(TAG,"");

                // run in main loop
                new Handler(getMainLooper()).post(new Runnable() {

                    @Override
                    public void run() {

                        if(remainingTime == 0){
                            canResend = true;
                            resendCode.setText("Resend Code Now");
                            resendCode.setTextColor(getColor(R.color.colorAccent));
                            timer.cancel();
                            return;
                        }

                        String formattedText = String.format(baseText, remainingTime);
                        remainingTime--;
                        resendCode.setText(formattedText);

                    }
                });

            }

        },0,1000);

    }

    public void resendCodeClicked(View view) {
        if(canResend){
            resendCode();
        }
    }

    private void resendCode(){

        // submit requests
        final String uid = getIntent().getStringExtra("uid");
        final CognitoUser user = authManager.getUser(uid);

        VerificationHandler handler = new VerificationHandler() {

            @Override
            public void onSuccess(CognitoUserCodeDeliveryDetails verificationCodeDeliveryMedium) {
                Log.d(TAG,"resent");
                resetResendTimer();
            }

            @Override
            public void onFailure(Exception exception) {
                Log.d(TAG,"cannot resend");
            }
        };

        user.resendConfirmationCodeInBackground(handler);

    }
}
