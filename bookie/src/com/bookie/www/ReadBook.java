package com.bookie.www;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ReadBook extends Activity implements OnClickListener{

	EditText edtTitle, edtAuthor;
	Button btnStartRead;
	String mEmail;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.read_book);
	    
	    edtTitle = (EditText) findViewById(R.id.readbook_edt_title);
	    edtAuthor = (EditText) findViewById(R.id.readbook_edt_author);
	    
	    btnStartRead = (Button) findViewById(R.id.readbook_btn_startRead);
	    
	    SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE); 
		mEmail = prefs.getString("email", null);
	    
	    //edtTitle.setOnClickListener(this);
	   //edtAuthor.setOnClickListener(this);
	    btnStartRead.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		UpdateToServer();
	}

	public void UpdateToServer() {
		/******************************************************************************************/
		String query = //"START TRANSACTION;"
								"INSERT INTO book(name, author) VALUES("
								+ "'"
								+ edtTitle.getText().toString().trim()
								+ "'"
								+ ","
								+ "'"
								+ edtAuthor.getText().toString().trim()
								+ "');";
//								+ "INSERT INTO books_read_by(name, author) VALUES("
//								+ "'"
//								+ edtTitle.getText().toString().trim()
//								+ "'"
//								+ ","
//								+ "'"
//								+ edtAuthor.getText().toString().trim()
//								+ "');"
								//+ "COMMIT;";
		
		Log.e("ReadBook", "sql : " + query);
		DAO.getInstance().setNextExecuteQuery(query);
		Handler myHandler = new Handler();
		Runnable myRunner = new Runnable(){
		     public void run() {
		    	 new AsyncTask<Void, Void, String> () {
		    		 	DAO dao;
		    			@Override
		    			protected void onPreExecute() {
		    			}
		    			
		    			@Override
		    			protected String doInBackground(Void... params) {
		    				dao = DAO.getInstance();
		    				String jsonString = dao.getJsonFromServer();
		    				return jsonString;
		    			}
		    			
		    			@Override
		    			protected void onPostExecute(String result) {
		    				
		    				if (result != "error" ) {
		    					new AlertDialog.Builder(ReadBook.this)
		    					.setTitle("도서가 등록되었습니다.")
		    					.setPositiveButton("책보러 가기", new DialogInterface.OnClickListener() {
		    						@Override
		    						public void onClick(DialogInterface dialog, int which) {
		    							finish();
		    						}
		    					}).create().show();
		    				}
		    			}
		    		}.execute();
		}};
		myHandler.post(myRunner);
/******************************************************************************************/
	}
	
	
}
