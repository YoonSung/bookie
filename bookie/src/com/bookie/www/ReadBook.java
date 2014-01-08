package com.bookie.www;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ReadBook extends Activity implements OnClickListener{

	EditText edtTitle, edtAuthor;
	Button btnStartRead;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.read_book);
	    
	    edtTitle = (EditText) findViewById(R.id.readbook_edt_title);
	    edtAuthor = (EditText) findViewById(R.id.readbook_edt_author);
	    
	    btnStartRead = (Button) findViewById(R.id.readbook_btn_startRead);
	    
	    edtTitle.setOnClickListener(this);
	    edtAuthor.setOnClickListener(this);
	    btnStartRead.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String title = edtTitle.getText().toString().trim();
		String author = edtTitle.getText().toString().trim();
		
	}

}
