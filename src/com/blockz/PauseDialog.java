package com.blockz;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PauseDialog extends Dialog {

	Game _game;
	
    public PauseDialog(Context context, Game game) {
        super(context,R.style.FullHeightDialog);
        
        _game = game;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pause);
        
        setTitle("Game Paused!");
        //setGravity(Gravity.CENTER)
        Button btnContinue = (Button) findViewById(R.id.resume);
        btnContinue.setOnClickListener(new resumeListener());
        Button btnRestart = (Button) findViewById(R.id.restart);
        btnRestart.setOnClickListener(new restartListener());
    }

    private class resumeListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            PauseDialog.this.dismiss();
            _game.pause();
        }
    }
    private class restartListener implements android.view.View.OnClickListener {
        @Override
        public void onClick(View v) {
            PauseDialog.this.dismiss();
            _game.reset();
            _game.pause();
            
        }
    }

}
