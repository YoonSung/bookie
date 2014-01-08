package com.bookie.www;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {

	Button btnJoin, btnLogin;
	EditText edtEmail, edtPassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btnJoin = (Button) findViewById(R.id.main_btnJoin);
		btnLogin = (Button) findViewById(R.id.main_btnLogin);
		edtEmail = (EditText) findViewById(R.id.main_edtEmail);
		edtPassword = (EditText) findViewById(R.id.main_edtPassword);

		btnJoin.setOnClickListener(this);
		btnLogin.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		Intent intent = null;

		if (v.getId() == R.id.main_btnJoin) {
			startActivity(new Intent(this, Join.class));
		} else if (v.getId() == R.id.main_btnLogin) {
			String email = edtEmail.getText().toString().trim();
			String password = edtPassword.getText().toString().trim();

			// 아이디 비밀번호 체크.
			if (email.length() == 0 || password.length() == 0) {
				Toast.makeText(this, "아이디와 비밀번호를 모두 작성해 주세요", 2000).show();
				return;
			}

/******************************************************************************************/
			String query = "SELECT email, password FROM reader WHERE email="
					+ "'" + email + "'" + " AND password= " + "'" + password
					+ "'" + ";";
			
			Log.e("Main", "sql : " + query);
			DAO.getInstance().setNextExecuteQuery(query);
			Handler myHandler = new Handler();
			Runnable myRunner = new Runnable(){
			     public void run() {
			    	 new AsyncTask<Void, Void, String> () {
			    		 
			    		 	private ProgressDialog dialog;
			    		 	DAO dao;
			    			@Override
			    			protected void onPreExecute() {
			    				dialog = new ProgressDialog(Main.this);
			    				this.dialog.setMessage("로그인 중입니다...");
			    				   this.dialog.setCancelable(false);
			    				   this.dialog.show();
			    			}
			    			
			    			@Override
			    			protected String doInBackground(Void... params) {
			    				dao = DAO.getInstance();
			    				String jsonString = dao.getJsonFromServer();
			    				return jsonString;
			    			}
			    			
			    			@Override
			    			protected void onPostExecute(String result) {
			    				
			    				this.dialog.setCancelable(true);
			    				   if(this.dialog.isShowing()==true)
			    				         this.dialog.dismiss();
			    				
			    				if (result != "error" ) {
			    					ArrayList<Map<String,String>> list = dao.getMapFromJsonString(result);
			    					Map<String,String> map = list.get(0);
			    					if (list.get(0) == null) {
			    						Toast.makeText(Main.this, "아이디아 비밀번호를 다시한번 확인해 주세요", 3000).show();
			    						return;
			    					}
			    						
			    					//로그인 성공시
			    					if ( map.get("email").equalsIgnoreCase(edtEmail.getText().toString().trim()) &&
			    						 map.get("password").equalsIgnoreCase(edtPassword.getText().toString().trim())) {
			    						
			    						//프리퍼런스로 이메일 저장.
			    						//dao.savePreference(Main.this, "email", edtEmail.getText().toString().trim());
			    						
			    						SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
			    				        SharedPreferences.Editor editor = pref.edit();
			    				        editor.putString("email", edtEmail.getText().toString().trim());
			    				        editor.commit();
			    						
			    						startActivity(new Intent(Main.this, BookList.class));
			    					} else {
			    						Toast.makeText(Main.this, "아이디아 비밀번호를 다시한번 확인해 주세요", 3000).show();
			    					}
			    				}
			    			}
			    		}.execute();
			}};
			myHandler.post(myRunner);
/******************************************************************************************/
		}
		
	}
}
