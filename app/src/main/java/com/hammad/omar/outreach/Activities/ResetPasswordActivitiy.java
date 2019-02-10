package com.hammad.omar.outreach.Activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.hammad.omar.outreach.App;
import com.hammad.omar.outreach.Managers.AuthManager;
import com.hammad.omar.outreach.R;


public class ResetPasswordActivitiy extends AppCompatActivity {

    Activity mThis = this;

    //model
    CognitoUser mUser;
    AuthManager mAuthManager;
    ForgotPasswordContinuation mContinuation;

    // ui
    EditText mNewPassView;
    EditText mVerifCodeView;
    TextView mSentToView;
    TextView mErrorTextView;
    ProgressBar mProgressView;
    Button mSendButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_activitiy);

        //UI
        mNewPassView = findViewById(R.id.newPassEditText);
        mVerifCodeView = findViewById(R.id.codeEditText);
        mSentToView = findViewById(R.id.sentToView);
        mErrorTextView = findViewById(R.id.errorText);
        mProgressView = findViewById(R.id.progress);
        mSendButton = findViewById(R.id.sendButton);

        // user
        mAuthManager = AuthManager.getInstance(this);
        String userName = getIntent().getStringExtra("userName");
        mUser = mAuthManager.getUser(userName);

        if(mUser==null){
            goBack();
        }

        //setup Listeners
        setupListeners();

        //send verification code
        sendVerificationCode();


    }

    private void setupListeners() {

        mNewPassView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnable();
            }

        });

        mVerifCodeView.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setButtonEnable();
            }
        });



    }

    private void setButtonEnable(){

        boolean enabled = true;

        if (TextUtils.isEmpty(mVerifCodeView.getText().toString()) || TextUtils.isEmpty(mVerifCodeView.getText().toString())) {
                enabled = false;
        }

        mSendButton.setEnabled(enabled);

    }

    public void sendVerificationCode(){


        mUser.forgotPasswordInBackground(new ForgotPasswordHandler() {

            @Override
            public void onSuccess() {
                Toast.makeText(mThis, R.string.passResetSuccess,Toast.LENGTH_SHORT);
                finish();
            }

            @Override
            public void getResetCode(ForgotPasswordContinuation continuation) {

                // get the parameter
                String medium = continuation.getParameters().getDeliveryMedium();
                mSentToView.setText(medium);

                // set the continuation
                mContinuation = continuation;

            }

            @Override
            public void onFailure(Exception exception) {

                String errorMessage = "";

                if(exception instanceof AmazonServiceException){

                    AmazonServiceException awsException = (AmazonServiceException) exception;
                    errorMessage = awsException.getErrorMessage();

                }else if (exception instanceof AmazonClientException){

                    AmazonClientException awsException = (AmazonClientException) exception;
                    errorMessage = awsException.getMessage();

                    if (!App.hasActiveInternetConnection(mThis)) {
                        errorMessage = getString(R.string.makeSureConnected);
                    }

                }

                if(errorMessage == null){
                    return;
                }

                mErrorTextView.setVisibility(View.VISIBLE);
                mErrorTextView.setText(errorMessage);

            }
        });

    }

    private void goBack(){
        finish();
    }

    public void resetClicked(View view) {

        // read the new pass

        String newPass = mNewPassView.getText().toString();

        if(TextUtils.isEmpty(newPass)){
            Toast.makeText(mThis, R.string.enterNewPass,Toast.LENGTH_SHORT).show();
            return;
        }

        // get the code

        String code = mVerifCodeView.getText().toString();

        if(TextUtils.isEmpty(code)){
            Toast.makeText(mThis, R.string.enterVerif,Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        showProgress(true);

        // set the parameters
        mContinuation.setPassword(newPass);
        mContinuation.setVerificationCode(code);
        mContinuation.continueTask();

    }

    private void showProgress(boolean showProgress) {

        View formLayout = findViewById(R.id.formsLayout);

        if(showProgress){
            mProgressView.setVisibility(View.VISIBLE);
            formLayout.setVisibility(View.GONE);

        }else{
            mProgressView.setVisibility(View.GONE);
            formLayout.setVisibility(View.VISIBLE);
        }

    }
}
