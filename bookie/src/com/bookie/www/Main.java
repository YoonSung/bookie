package com.bookie.www;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener{

	Button btnJoin, btnLogin;
	EditText edtEmail, edtPassword;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	
	    btnJoin = (Button)findViewById(R.id.main_btnJoin);
	    btnLogin = (Button)findViewById(R.id.main_btnLogin);
	    edtEmail = (EditText)findViewById(R.id.main_edtEmail);
	    edtPassword = (EditText)findViewById(R.id.main_edtPassword);
	    
	    
	    btnJoin.setOnClickListener(this);
	    btnLogin.setOnClickListener(this);
	    
	}
	@Override
	public void onClick(View v) {
		
		Intent intent = null;
		
		
		if ( v.getId() == R.id.main_btnJoin ) {
			intent = new Intent(this, Join.class);
		} else if ( v.getId() == R.id.main_btnLogin ) {
			String email = edtEmail.getText().toString().trim();
			String password = edtPassword.getText().toString().trim();
			
			//아이디 비밀번호 체크.
			if ( email.length() == 0 || password.length() == 0 ) {
				Toast.makeText(this, "아이디와 비밀번호를 모두 작성해 주세요", 2000).show();
				return;
			}
			intent = new Intent ( this, BookList.class );
		}
		
		if (intent != null)
			startActivity(intent);
	}

}
