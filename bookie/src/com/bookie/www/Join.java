package com.bookie.www;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends Activity implements OnClickListener {

	Button btnReset, btnRegister;
	EditText edtEmail, edtPw, edtPwConfirm, edtNickname;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);

		btnReset = (Button) findViewById(R.id.join_btn_reset);
		btnRegister = (Button) findViewById(R.id.join_btn_join);

		edtEmail = (EditText) findViewById(R.id.join_edt_email);
		edtPw = (EditText) findViewById(R.id.join_edt_password);
		edtPwConfirm = (EditText) findViewById(R.id.join_edt_passwordConfirm);
		edtNickname = (EditText) findViewById(R.id.join_edt_nickname);

		btnReset.setOnClickListener(this);
		btnRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.join_btn_join) {
			
			String email = edtEmail.getText().toString().trim();
			String pw = edtPw.getText().toString();
			String pwConfirm = edtPwConfirm.getText().toString();
			String nickname  = edtNickname.getText().toString();
			
			
			
			if ( pw.equalsIgnoreCase(pwConfirm ) == false ) {
				Toast.makeText(this, "비밀번호입력과 비밀번호 입력확인란을\n다시한번 확인해 주세요", 3000).show();
				return;
			}
			
			if ( email.equalsIgnoreCase("") ||
				  pw.equalsIgnoreCase("") ||
				  pwConfirm.equalsIgnoreCase("") ||
				  nickname.equalsIgnoreCase("")
					) {
				Toast.makeText(this, "빈공백을 모두 채워주세요", 3000).show();
				return;
			}
			
			
			//만약 아이디가 이미 존재할경우..처리
			//여기서 디비에 저장한다.
			
			String query = 
					"INSERT INTO reader (email, password, nickname ) VALUES ("
			+"'"
			+email
			+"'"
			+", "
			+"'"
			+pw
			+"'"
			+", "
			+"'"
			+nickname
			+"'"
			+" );";
			DAO.getInstance().setNextExecuteQuery( query ); 
			
			Handler myHandler = new Handler();
			Runnable myRunner = new Runnable(){
			     public void run() {
			    	 new AsyncTask<Void, Void, String> () {
			    		 
			    		 	private ProgressDialog dialog;
			    		 
			    			@Override
			    			protected void onPreExecute() {
			    				dialog = new ProgressDialog(Join.this);
			    				this.dialog.setMessage("회원가입 중입니다...");
			    				   this.dialog.setCancelable(false);
			    				   this.dialog.show();
			    			}
			    			
			    			@Override
			    			protected String doInBackground(Void... params) {
			    				String jsonString = DAO.getInstance().getJsonFromServer();
			    				return jsonString;
			    			}
			    			
			    			@Override
			    			protected void onPostExecute(String result) {
			    				
			    				this.dialog.setCancelable(true);
			    				   if(this.dialog.isShowing()==true)
			    				         this.dialog.dismiss();
			    				
			    				if (result != "error" ) {
			    					new AlertDialog.Builder(Join.this)
			    					.setTitle("회원가입을 축하합니다.")
			    					.setPositiveButton("로그인하러 가기", new DialogInterface.OnClickListener() {
			    						@Override
			    						public void onClick(DialogInterface dialog, int which) {
			    							finish();
			    						}
			    					}).create().show();
			    				} else {
			    					new AlertDialog.Builder(Join.this)
			    					.setTitle("오류")
			    					.setMessage("네트워크 오류로 회원가입에 실패하였습니다.\n잠시후 다시 시도해 주세요..")
			    					.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			    						@Override
			    						public void onClick(DialogInterface dialog, int which) {
			    						}
			    					}).create().show();
			    				}
			    			}
			    		}.execute();
			}};
			myHandler.post(myRunner);
			
		} else if (v.getId() == R.id.join_btn_reset) {
			edtEmail.setText("");
			edtPw.setText("");
			edtPwConfirm.setText("");
			edtNickname.setText("");
		}
			
	}
}
