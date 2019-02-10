package com.hammad.omar.outreach.Interfaces;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

import java.util.Map;

public interface LambdaInterface {
    /**
     * Invoke lambda function "echo". The function name is the method name
     */
    @LambdaFunction
    Map<String,Object> getNumOfEmotionsToday();
}