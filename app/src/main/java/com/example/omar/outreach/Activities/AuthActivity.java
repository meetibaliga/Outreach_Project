package com.example.omar.outreach.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
import com.example.omar.outreach.App;
import com.example.omar.outreach.Interfaces.AuthState;
import com.example.omar.outreach.Interfaces.CallBackAuth;
import com.example.omar.outreach.Managers.AuthManager;
import com.example.omar.outreach.Managers.LocationManager;
import com.example.omar.outreach.R;

/**
 * A login screen that offers login via email/password.
 */
public class AuthActivity extends AppCompatActivity implements CallBackAuth {

    private static final String TAG = AuthActivity.class.getSimpleName();

    // members
    private AuthManager authManager;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private AutoCompleteTextView mMobileView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private TextView mErrorText;

    //temp
    View focusView;
    boolean error;
    String userName;
    String password;

    //state
    private AuthState authState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);


        // Set up the login form.
        setupUI();
        authManager = AuthManager.getInstance(this);

        //default state
        authState = new AuthState.Signup();
        modifyAuthUIAfterSwitch();

    }


    private void setupUI() {

        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mMobileView = (AutoCompleteTextView) findViewById(R.id.mobile);
        mPasswordView = (EditText) findViewById(R.id.password);
        mErrorText = findViewById(R.id.errorText);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        setPasswordAction();
    }

    private void setPasswordAction(){

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    authState.attemptAuth(AuthActivity.this);
                    return true;
                }
                return false;
            }
        });
    }

    private void attemptAuth(){
        authState.attemptAuth(this);
    }

    public void attemptLogin() {

        Log.d(TAG," attempting login ..");

        checkLoginFormErrors();

        String normalizedMobile = normalizeMobileNumber(mMobileView.getText().toString());

        if(normalizedMobile == null){
            mMobileView.setError("Wrong Number");
            return;
        }

        if(!error){
            showProgress(true);
            userName = normalizedMobile;
            password = mPasswordView.getText().toString();
            authManager.signinUser(userName,password,this);
        }else{
            focusView.setFocusable(true);
        }
    }

    public void attemptSignup(){

        Log.d(TAG," attempting signup ..");

        checkLoginFormErrors();
        checkSignupFormErrors();

        String normalizedMobile = normalizeMobileNumber(mMobileView.getText().toString());

        if(normalizedMobile == null){
            mMobileView.setError("Wrong Number");
            return;
        }

        if(!error){
            showProgress(true);
            userName = normalizedMobile;
            password = mPasswordView.getText().toString();
            authManager.signupUser(userName,password,userName,mEmailView.getText().toString(),"","",this);
        }else{
            focusView.setFocusable(true);
        }

    }

    public String normalizeMobileNumber(String mobile){

        if(TextUtils.isEmpty(mobile)){
            return null;
        }

        if ( mobile.substring(0,2).equals("+1") && mobile.length() == 12 ) {

            return mobile;

        } else if (mobile.length() == 10) {

            return "+1" + mobile;

        } else if (mobile.substring(0,3).equals("001") && mobile.length() == 13){

            return "+" + mobile.substring(2);

        } else if (mobile.substring(0,1).equals("1") && mobile.length() == 11){

            return "+" + mobile.substring(1);

        }

        return null;

    }

    private void checkSignupFormErrors(){

        // Reset errors.
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();

        // Check for a valid mobile number

        if (TextUtils.isEmpty(email)) {
            mMobileView.setError("This field is required");
            focusView = mEmailView;
            error = true;

        } else if (!isEmailValid(email)) {
            mMobileView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            error = true;
        }

    }

    private void checkLoginFormErrors(){

        // Reset errors.
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String mobile = mMobileView.getText().toString();
        String password = mPasswordView.getText().toString();

        error = false;
        focusView = null;

        // Check for a valid password, if the user entered one.

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError("Password cannot be empty");
            focusView = mPasswordView;
            error = true;
        }else if (!isPasswordValid(password)){
            mPasswordView.setError("Password must be at least 8 characters");
            focusView = mPasswordView;
            error = true;
        }

        // Check for a valid email address
        if (TextUtils.isEmpty(mobile)) {
            mMobileView.setError("This field is required");
            focusView = mMobileView;
            error = true;

        } else if (!isMobileValid(mobile)) {
            mMobileView.setError("This mobile number is incorrect");
            focusView = mMobileView;
            error = true;
        }

    }



    @Override
    public void callbackAuth(Object object, int callbackId) {

        if (callbackId == AuthManager.CALL_BACK_ID_LOGIN){
            callbackLogin(object);

        }else if (callbackId == AuthManager.CALL_BACK_ID_SIGNUP){
            callbackSignup(object);

        }else if (callbackId == AuthManager.CALL_BACK_ID_CHALLENGE){
            callbackChallenge(object);
        }

    }

    private void callbackLogin(Object object) {

        if (object instanceof Exception){

            String errorMessage = "";
            String errorCode = "";

            if(object instanceof AmazonServiceException){

                AmazonServiceException awsException = (AmazonServiceException) object;
                errorMessage = awsException.getErrorMessage();
                errorCode = awsException.getErrorCode();

            }else if (object instanceof AmazonClientException){

                AmazonClientException awsException = (AmazonClientException) object;
                errorMessage = awsException.getMessage();

                if (!App.hasActiveInternetConnection(this)) {
                    errorMessage = "Make sure you are connected to the internet";
                }

            }

            if (errorCode.equalsIgnoreCase("userNotConfirmedException")){

                goToMobileAuthentication();

            }else {

                showProgress(false);
                mLoginFormView.setVisibility(View.VISIBLE);
                mPasswordView.requestFocus();
                mErrorText.setVisibility(View.VISIBLE);
                mErrorText.setText(errorMessage);

            }
            return;
        }

        // SUCCESS

        Log.d(TAG,"success log in ");
        CognitoUser user = (CognitoUser) object;
        App.USER_ID = user.getUserId();
        goToMainScreen();
    }

    private void callbackChallenge(Object object){

    }

    private void callbackSignup(Object object){

        if (object instanceof Exception){

            String errorMessage = "";

            if(object instanceof AmazonServiceException){

                AmazonServiceException awsException = (AmazonServiceException) object;
                errorMessage = awsException.getErrorMessage();

            }else if (object instanceof AmazonClientException){

                AmazonClientException awsException = (AmazonClientException) object;
                errorMessage = awsException.getMessage();

                if (!App.hasActiveInternetConnection(this)) {
                    errorMessage = "Make sure you are connected to the internet";
                }

            }

            showProgress(false);
            mLoginFormView.setVisibility(View.VISIBLE);
            mErrorText.setVisibility(View.VISIBLE);
            mErrorText.setText(errorMessage);

            return;
        }

        // success
        Log.d(TAG,"success Signup");
        CognitoUser user = (CognitoUser) object;
        App.USER_ID = user.getUserId();


        // sign in user
        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
            return;
        }

        authManager.signinUser(userName,password,this);

    }

    private void goToMobileAuthentication() {

        Intent intent = new Intent(this,ConfirmMobile.class);
        intent.putExtra("Mobile",mMobileView.getText().toString());

        String userId = normalizeMobileNumber(mMobileView.getText().toString());

        intent.putExtra("uid",userId);
        startActivity(intent);

    }


    private boolean isMobileValid(String mobile) {

        return (mobile.contains("+1")&&mobile.length() == 12) || mobile.length() == 10 || (mobile.contains("00") && mobile.length() == 13) || (mobile.substring(0,1).equals("1") && mobile.length() == 11);
    }

    private boolean isEmailValid(String email){
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });



            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });



        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void goToMainScreen(){

        // check location
        if(LocationManager.isLocationEnabled(this)){
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this,PermissionsActivity.class);
            startActivity(intent);
        }

    }

    public void modifyAuthUIAfterSwitch(){

        // add the mobile number field
        View emailView = findViewById(R.id.emailView);
        emailView.setVisibility(authState.getExtraViewVisibility());
        emailView.setAlpha(authState.getExtraViewAlpha()[0]);

        emailView.animate().setDuration(500).alpha(authState.getExtraViewAlpha()[1]);

        TextView clickHere = findViewById(R.id.switchText);
        clickHere.setText(authState.getSwitchTextViewText());

        Button authButton = findViewById(R.id.email_sign_in_button);
        authButton.setText(authState.getAuthButtonText());

        View forgetPass = findViewById(R.id.forgotPasswordLabel);
        forgetPass.setVisibility(authState.getForgetPassVisibily());

        setPasswordAction();

        changeTitle();

    }

    private void changeTitle() {
        setTitle(authState.getTitle());
    }


    public void toggleState(){

        if(authState instanceof AuthState.Login){
            // change state
            authState = new AuthState.Signup();
        }else{
            // change state
            authState = new AuthState.Login();

        }

    }


    public void switchStateLabelClicked(View view) {
        toggleState();
        modifyAuthUIAfterSwitch();
    }

    public void authenticate(View view) {
        authState.attemptAuth(this);
    }

    public void forgotClicked(View view) {

        String userName = normalizeMobileNumber(mMobileView.getText().toString());

        if(TextUtils.isEmpty(userName)){
            Toast.makeText(this,"Please enter you mobile number",Toast.LENGTH_SHORT)
                    .show();
            mMobileView.requestFocus();
            return;
        }

        CognitoUser user = authManager.getUser(userName);

        if(user == null){

            Toast.makeText(this,"No user with this mobile number",Toast.LENGTH_SHORT)
                    .show();
            mMobileView.requestFocus();
            return;
        }

        Intent intent = new Intent(this,ResetPasswordActivitiy.class);
        intent.putExtra("userName",userName);
        startActivity(intent);

    }
}

