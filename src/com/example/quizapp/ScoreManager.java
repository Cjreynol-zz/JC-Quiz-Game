package com.example.quizapp;

import android.widget.Toast;

public class ScoreManager {
	private int numCorrect;
	private int numWrong;
	private int result;
	private double score;
	private QuizGame QGA;
	private Query_Manager qM;
	
	public ScoreManager(QuizGame qga, Query_Manager qm)
	{
		QGA = qga;
		qM = qm;
		numCorrect = 0;
		numWrong = 0;
		result = 0;
		score = 0;
	}
	
	public void checkAnswer(String answer)
	{
		if(answer.equals(qM.getAnswer()))
		{
			result = 1;
			Toast.makeText(QGA,"Correct! :)", Toast.LENGTH_SHORT).show();
		}
		else
		{
			result = 0;
			Toast.makeText(QGA,"Incorrect :(", Toast.LENGTH_SHORT).show();
		}
		
		updateScore(result);
		QGA.removeButtons();
		qM.LoadQuestion();
	}
	public void updateScore(int result)
	{
		if(result == 1)
			numCorrect++;	
		else
			numWrong++;
	}
	
	public double getScore()
	{
		score = ((double) numCorrect /(double)(numCorrect + numWrong)) * 100;
		return score;
	}
}
