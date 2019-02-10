package com.hammad.omar.outreach.Managers;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunctionException;
import com.amazonaws.mobileconnectors.lambdainvoker.LambdaInvokerFactory;
import com.hammad.omar.outreach.Interfaces.CallBackLambda;
import com.hammad.omar.outreach.Interfaces.LambdaInterface;

import java.util.Map;

public class LambdaManager {

    private static final String TAG = LambdaManager.class.getSimpleName();

    // members
    CognitoCachingCredentialsProvider credentialsProvider;
    LambdaInvokerFactory factory;
    LambdaInterface proxy;

    //callback
    CallBackLambda callBackLambda;

    public static LambdaManager instance;

    public static LambdaManager getInstance(Context context,CallBackLambda callBackLambda){

        if(instance == null){
            instance = new LambdaManager(context,callBackLambda);
        }

        return instance;
    }

    private LambdaManager(Context context,CallBackLambda callBackLambda){

        // Create an instance of CognitoCachingCredentialsProvider
        CognitoCachingCredentialsProvider credentialsProvider =
                new CognitoCachingCredentialsProvider(
                        context.getApplicationContext(),
                        AuthManager.getIdentityPoolId(),
                        AuthManager.getCognitoRegion());

        // Create a LambdaInvokerFactory, to be used to instantiate the Lambda proxy
        LambdaInvokerFactory factory = new LambdaInvokerFactory(
                context.getApplicationContext(),
                AuthManager.getCognitoRegion(),
                credentialsProvider);

        // Create the Lambda proxy object with default Json data binder.
        // You can provide your own data binder by implementing
        // LambdaDataBinder
        proxy = factory.build(LambdaInterface.class);

        // call back
        this.callBackLambda = callBackLambda;

    }

    public void getNumOfEmotionsToday() {

        // The Lambda function invocation results in a network call
        // Make sure it is not called from the main thread

        Log.d(TAG,"in get num of emotions today");

        new AsyncTask<Object,Integer,Map>(){

            @Override
            protected Map doInBackground(Object[] objects) {

                Log.d(TAG,"in do in background exc");

                // invoke "echo" method. In case it fails, it will throw a
                // LambdaFunctionException.

                try {

                    Log.d(TAG,"trying to call proxy");
                    return proxy.getNumOfEmotionsToday();

                } catch (LambdaFunctionException lfe) {
                    Log.d(TAG,"failed to call echo");
                    Log.e(TAG, "Failed to invoke echo", lfe);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Map result) {

                Log.d(TAG,"in post exc");

                // null results
                if(result == null){
                    callBackLambda.callbackLambda(null,new Error("no Results"),"");
                    return;
                }

                // there are results
                callBackLambda.callbackLambda(result,null,"");

            }
        }.execute();

    }
}
