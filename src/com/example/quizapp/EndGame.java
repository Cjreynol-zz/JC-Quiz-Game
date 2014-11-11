package com.example.quizapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class EndGame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_game);	
		showScore();
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.end_game, menu);
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
	
	public void showScore()
	{
		Bundle info = getIntent().getExtras();
		double score = info.getDouble("Score");
		TextView tv = (TextView) findViewById(R.id.GameFinished);
		tv.setText("Your Score is: "+ score + "%");
	}
	
	@Override
	public void onBackPressed()
	{
		TextView tv = (TextView) findViewById(R.id.PlayAgain);
		tv.setText("You cannot return to the quiz. Please select Yes to play again, or No to exit.");
	}
	
	public void playAgain(View view)
	{
		int btn = view.getId();
		
		if (btn == findViewById(R.id.button1).getId())
		{
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
		}
		
		if(btn == findViewById(R.id.button2).getId())
		{
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
		}
	}

}
