package com.bookie.www;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Join extends Activity implements OnClickListener{

	Button btnReset, btnRegister;
	EditText edtEmail, edtPw, edtPwConfirm, edtNickname;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.join);
	    
	    btnReset = (Button)findViewById(R.id.join_btnReset);
	    btnRegister = (Button)findViewById(R.id.join_btnJoin);
	    
	    edtEmail = (EditText)findViewById(R.id.join_edtEmail);
	    edtPw = (EditText)findViewById(R.id.join_edtPassword);
	    edtPwConfirm = (EditText)findViewById(R.id.join_edtPasswordRe);
	    edtNickname = (EditText)findViewById(R.id.join_edtNickname);
	    
	    btnReset.setOnClickListener(this);
	    btnRegister.setOnClickListener(this);
	} 

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.join_btnJoin) {
			
			String email = edtEmail.getText().toString().trim();
			String pw = edtPw.getText().toString();
			String pwConfirm = edtPwConfirm.getText().toString();
			String nickname  = edtNickname.getText().toString();
			
			if ( ! pw.equals(pwConfirm) ) {
				
				Toast.makeText(this, "비밀번호입력과 비밀번호 입력확인란을\n다시한번 확인해 주세요", 2000).show();
				return;
			}
			
			//만약 아이디가 이미 존재할경우..처리
			//여기서 디비에 저장한다.
			
			
		} else if (v.getId() == R.id.join_btnReset) {
			edtEmail.setText("");
			edtPw.setText("");
			edtPwConfirm.setText("");
			edtNickname.setText("");
		}
			
	}

}
