package com.example.kahoot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Scanner;
import java.util.ArrayList;

public class QuizFx {
    private String name;
    private ArrayList<Question> questions;

    QuizFx(){
        questions = new ArrayList<>();
    }
    public Question getQ(int ind){
        return questions.get(ind);
    }
    public  int getN(){
        return questions.size();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public static QuizFx loadFromFile(String s) throws FileNotFoundException, InvalidQuizFormatException {
        QuizFx quiz = new QuizFx();
        File file = new File(s);
        quiz.setName(s.split("\\.")[0]);
        Scanner in =  new Scanner(file);
        if(!in.hasNextLine()) throw new InvalidQuizFormatException("No line found!");
        while(in.hasNextLine()){
            String description = in.nextLine();
            if(description.equals("")) break;
            if(description.contains("{blank}")){
                FillIn fill= new FillIn();
                fill.setDescription(description);
                fill.setDescription(fill.toString());
                fill.setAnswer(in.nextLine());
                if(in.hasNextLine())
                    in.nextLine();
                quiz.addQuestion(fill);
            }
            else{
                Test test = new Test();
                test.setDescription(description);
                ArrayList<String> options = new ArrayList();
                for(int i = 0; i < 4; i++){
                    options.add(in.nextLine());
                }
                if(in.hasNextLine()) in.nextLine();
                test.setAnswer(options.get(0));
                // Collections.shuffle(options);
                test.setOptions(options.toArray(new String[0]));
                quiz.addQuestion(test);
            }
        }
        return quiz;

    }
    public void start(){
        Scanner in = new Scanner(System.in);
        int correctAnswers = 0;
        System.out.println("WELCOME TO " + getName() + " QUIZ!");
        for(int i = 0; i<questions.size();i++){
            System.out.println(questions.get(i).getDescription());
            if(questions.get(i) instanceof Test){
                Test test = (Test)questions.get(i);
                test.toString();
                System.out.print("Print your answer: ");

                char answer = in.next().charAt(0);
                while(true){
                    try{
                        if(test.getOptionAt(((int) answer) -65).equals(test.getAnswer())){
                            System.out.println("Correct");
                            correctAnswers++;
                        }
                        else{
                            System.out.println("Incorrect");
                        }
                        break;
                    }
                    catch(Exception exception){
                        System.out.print("Invalid input:");
                        answer=in.next().charAt(0);
                    }
                }
            }
            else{
                FillIn fill = (FillIn) questions.get(i);
                String answerF = in.next();
                if(answerF.equalsIgnoreCase(fill.getAnswer())){
                    System.out.println("Correct!");
                    correctAnswers++;
                }
                else{
                    System.out.println("Incorrect!");
                }
            }
        }
        System.out.println("Correct Answers: " + correctAnswers + "/5" + " " + "(" + (correctAnswers * 100) / 5 + "%)");
    }

    @Override
    public String toString(){
        return "";
    }
}

