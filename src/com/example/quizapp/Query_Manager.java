package com.example.quizapp;

import java.util.ArrayList;
import java.util.Random;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Query_Manager extends QuizGame{
	
private int QuestionNumber = 0;
private String CurrentQuestion;
private String CurrentAnswer;

private ArrayList<String> Answers;
private ArrayList<String> Randomize;
private QuizGame QGA;

private SQLiteDatabase db;
private String subject;
public String Category;

private Cursor c;

//Method responsible for loading questions into the QuizGame Activity
public void LoadQuestion()
{
	if(QuestionNumber < 10)
	{
		if (QuestionNumber == 0)
			QueryDb();
		UpdateInfo();
		UpdateDisplay(Answers);
	}
	else
		QGA.EndGame();

}

//Method that handles database queries that return new question and answers.	
public Cursor QueryDb()
{
	if (subject.equals("Technology"))	
	{
	c = db.rawQuery("Select Question, C_Answer, Answer1, Answer2, Answer3 From Questions Q, Answers A"
			+ " Where Q.id = A.Q_id AND Category='Technology' Order By Random() Limit 10;", null);
	}	
	
	if (subject.equals("Literature"))	
	{
	c = db.rawQuery("Select Question, C_Answer, Answer1, Answer2, Answer3 From Questions Q, Answers A"
			+ " Where Q.id = A.Q_id AND Category ='Literature' Order By Random() Limit 10;", null);
	}	
	c.moveToFirst();
	return c;
}

//Method to update variables containing current question and answer choices
public void UpdateInfo()
{
	if(QuestionNumber != 0)
		c.moveToNext();

	CurrentQuestion = c.getString(0);
	CurrentAnswer = c.getString(1);
	
	Randomize.clear(); //Removes the question from the list
	Answers.clear();//Clears the answers array of any old answers from other questions
	
	for(int i = 1; i < c.getColumnCount(); i++ )
	{
		if(c.isNull(i)  == false)
			Randomize.add(c.getString(i));
	}

	for(int i = 1; i < c.getColumnCount(); i++ )
	{
		if(c.isNull(i))
			Randomize.remove(c.getString(i));
	}
	
	Random answerGen = new Random();
	
	int qNumber;
	while(Randomize.size() > 0)
	{
		qNumber = answerGen.nextInt(Randomize.size());
	
		Answers.add(Randomize.get(qNumber));
		Randomize.remove(qNumber);
	}
	
	QuestionNumber++;
	
}

public String getAnswer()
{
	return CurrentAnswer;
}

public String getQuestion()
{
	return CurrentQuestion;
}

//Method responsible for updating the Display in the QuizGame Activity
public void UpdateDisplay(ArrayList<String> information)
{
	QGA.UpdateScreen(information);
}

public Query_Manager(QuizGame qa, SQLiteDatabase database, String sub)
{
	QGA = qa;
	Answers = new ArrayList<String>();
	Randomize = new ArrayList<String>();
	db = database;
	subject = sub;
	
}

}
