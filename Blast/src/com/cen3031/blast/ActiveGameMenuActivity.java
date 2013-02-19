package com.cen3031.blast;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ActiveGameMenuActivity extends Activity {
	//int size = 4;
	private ListView listView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //Dynamic ListView
	    setContentView(R.layout.activity_active_game_menu);
	    List<GameState> tv_games =  new ArrayList<GameState>();
	    //Test Cases
	    GameState game1 = new GameState("Patrick","Pedro");
	    GameState game2 = new GameState("Patrick","Park");
	    GameState game3 = new GameState("Pedro","Patrick");
	    GameState game4 = new GameState("Patrick","Jeff");
	    GameState game5 = new GameState("Patrick","Michael");
	    tv_games.add(game1);
	    tv_games.add(game2);
	    tv_games.add(game3);
	    tv_games.add(game4);
	    tv_games.add(game5);
	    GameStateArrayAdaptor adapter = new GameStateArrayAdaptor(this,tv_games );
 
	    listView = (ListView)findViewById(R.id.yourLayout);
	    listView.setAdapter(adapter);
	    listView.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView<?> parent, View view,
	            int position, long id) {
	            
	        		Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
	         
	        	  }
	      });
	}
	View.OnClickListener myhandler = new View.OnClickListener() {
	    public void onClick(View v) {
	    	Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
	    }
	};
	 public void onClick(View v) {
		 Intent intent = new Intent(this, PlayMenuActivity.class);
         startActivity(intent);
	 }
	 
}
