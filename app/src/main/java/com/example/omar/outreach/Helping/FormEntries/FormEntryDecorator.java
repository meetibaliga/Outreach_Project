package com.example.omar.outreach.Helping.FormEntries;

import android.content.Context;
import android.view.View;

import java.util.List;

public abstract class FormEntryDecorator extends FormEntry {

    FormEntry formEntry;

    public FormEntryDecorator(FormEntry formEntry){
        super(formEntry.getContext());
        this.formEntry = formEntry;
    }

    @Override
    public View getView() {
        return formEntry.getView();
    }

    @Override
    public List<String> getValues() {
        return formEntry.getValues();
    }
}
