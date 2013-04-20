package com.cen3031.blast;

import java.util.List;
import android.content.Context;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class GameStateArrayAdaptor extends ArrayAdapter<GameState>{
	   private final Context context;
	  private final List<GameState> games;  

	  public GameStateArrayAdaptor(Context context, List<GameState> games) {
	    super(context, R.layout.gamestate, games);
	    this.context = context;
	    this.games = games;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
		View gameView = convertView; 
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		gameView = inflater.inflate(R.layout.gamestate, parent, false);
		GameState game = games.get(position);
	    TextView textView = (TextView) gameView.findViewById(R.id.text);
	    textView.setText(game.user1name + " vs. " +game.user2name);
	    textView.setTextSize(20);
	    textView.setGravity(Gravity.CENTER);
	    gameView.setTag(game);
	    return gameView;
	  }

}
