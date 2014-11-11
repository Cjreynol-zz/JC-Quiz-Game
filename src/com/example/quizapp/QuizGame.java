package com.example.quizapp;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class QuizGame extends Activity {
	public int qNum = 1;
	public ArrayList<String> QA;
	public Query_Manager qM;
	private ScoreManager sM;
	private MyDatabase db;
	private SQLiteDatabase sqldb;
	private String subject;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz_game);
		Bundle information = getIntent().getExtras();
		subject = information.getString("Subject");
		
		//if statements to determine layout based on category passed from Main Activity
		if (subject.equals("Technology"))
		{
			this.getWindow().setBackgroundDrawableResource(R.drawable.technology);
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Technology Quiz!");
		}
		if (subject.equals("Literature"))
		{
			this.getWindow().setBackgroundDrawableResource(R.drawable.literature);
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText("Literature Quiz!");
		}

		//Instantiate SQLite database object, Query Manage, 
		db = new MyDatabase(this);
		sqldb = db.getWritableDatabase();
		qM = new Query_Manager(this, sqldb, subject);
		sM = new ScoreManager(this, qM);
		qM.LoadQuestion();
		
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz_game, menu);
				
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void Submit(View view)
	{
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		if (rg.getCheckedRadioButtonId() != -1) {
			RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
			sM.checkAnswer((String) rb.getText());	
			}
		
	}
	
	public void UpdateScreen(ArrayList<String> info)
	{
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		rg.clearCheck(); //clears any checks on the radio buttons including hidden ones
		
		TextView tv = (TextView) findViewById(R.id.textView2);
		tv.setText(qM.getQuestion());//qM.getQuestion());
		
		for(int i = 0; i < info.size(); i++)
		{
			 RadioButton rb = (RadioButton) getLayoutInflater().inflate(R.layout.abc_list_menu_item_radio,null);
			 rb.setId(i);
			 rb.setClickable(true);
			 rb.setText(info.get(i));
			 rb.setTextColor(Color.WHITE);
			 rg.addView(rb);
		}	
	}
	
	public void removeButtons()
	{
		RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);
		int j = rg.getChildCount();
		
		for(int i = 0; i < j; i++)
		{
			rg.removeViewAt(0);
		}
	}

	public void EndGame() 
	{
		Intent intent = new Intent(this, EndGame.class);
		intent.putExtra("Score", sM.getScore());
		startActivity(intent);		
	}

	}

	

