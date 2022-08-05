package com.example.kahoot;

public class FillIn extends Question {

    @Override
    public String toString(){
        return getDescription().replace("{blank}", "_______");
    }
}
