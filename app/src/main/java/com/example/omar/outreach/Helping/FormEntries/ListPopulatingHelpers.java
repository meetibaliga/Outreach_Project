package com.example.omar.outreach.Helping.FormEntries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListPopulatingHelpers {

    public static List<String> getListOf(String ...t){

        ArrayList<String> list = new ArrayList<>();

        for(String element : t){
            list.add(element);
        }

        return list;
    }

    public static List<String> getListOfYears(){
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int minAge = 6;
        for (int i = thisYear - minAge ; i > 1920; i--) {
            years.add(Integer.toString(i));
        }
        return years;
    }
}
