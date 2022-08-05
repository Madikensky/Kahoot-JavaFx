package com.example.kahoot;

import java.util.ArrayList;

public class Test extends Question {
    private String[] options;
    private int numOfOptions;
    ArrayList<Character> labels;

    public Test(){
        numOfOptions = 4;
        labels = new ArrayList<>();
        for(int i = 0; i < 4; i++){
            labels.add((char)('A' + i));
        }

    }
    public void setOptions(String[] options){
        this.options = options;
    }
    public String getOptionAt(int indexes){
        return options[indexes];
    }

    @Override
    public String toString(){
        String showAll = getDescription();
        System.out.println();

        for(int i = 0; i < labels.size(); i++){
            showAll += labels.get(i) + ") " + getOptionAt(i);
            System.out.println(labels.get(i) + ") " + getOptionAt(i));
        }

        return showAll;
    }
}