package com.bookie.www;

import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Statistic extends Activity implements OnClickListener {

	private Button btn1, btn2, btn3, btn4;
	private TextView txtResult;
	private ArrayList<Map<String,String>> list;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.statistic);

		btn1 = (Button) findViewById(R.id.statistic_btn_1);
		btn2 = (Button) findViewById(R.id.statistic_btn_2);
		btn3 = (Button) findViewById(R.id.statistic_btn_3);
		btn4 = (Button) findViewById(R.id.statistic_btn_4);
		txtResult = (TextView) findViewById(R.id.statistic_txt_result);

		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.statistic_btn_1:
			UpdateToServer("CREATE VIEW pagecount as SELECT reader_no, SUM(totalpages) as readpages FROM history GROUP BY reader_no;");
			UpdateToServer("SELECT r.nickname, MAX(pc.readpages) FROM reader r INNER JOIN pagecount pc WHERE r.reader_no = pc.reader_no;");
			break;
		case R.id.statistic_btn_2:
			UpdateToServer("CREATE VIEW bookcount as SELECT reader_reader_no, COUNT(book_book_no) as books FROM books_read_by GROUP BY reader_reader_no;");
			UpdateToServer("SELECT r.nickname, MAX(bc.books) FROM reader r INNER JOIN bookcount bc WHERE r.reader_no = bc.reader_reader_no;");
			break;
		case R.id.statistic_btn_3:
			UpdateToServer("CREATE VIEW countdone_t as SELECT reader_reader_no as reader_no, SUM(rab.done) as countdone FROM books_read_by GROUP BY reader_reader_no;");
			UpdateToServer("SELECT r.nickname MAX(cdt.countdone) FROM reader r INNER JOIN countdone_t cdt ON r.reader_no = cdt.reader_no;");
			break;
		case R.id.statistic_btn_4:
			UpdateToServer("CREATE VIEW bookmost as SELECT book_no,COUNT(reader_reader_no) as readercount as FROM books_read_by GROUP BY book_no;");
			UpdateToServer("SELECT b.name, b.arthor, MAX(bm.readercount) FROM book b INNER JOIN bookmost bm WHERE b.book_no = bm.book_no;");
			break;
		}
	}

	public void UpdateToServer(String query) {
		/******************************************************************************************/
		// String query = //"START TRANSACTION;"
		// "INSERT INTO book(name, author) VALUES("
		// + "'"
		// + edtTitle.getText().toString().trim()
		// + "'"
		// + ","
		// + "'"
		// + edtAuthor.getText().toString().trim()
		// + "');";
		// // + "INSERT INTO books_read_by(name, author) VALUES("
		// // + "'"
		// // + edtTitle.getText().toString().trim()
		// // + "'"
		// // + ","
		// // + "'"
		// // + edtAuthor.getText().toString().trim()
		// // + "');"
		// //+ "COMMIT;";

		Log.e("ReadBook", "sql : " + query);
		DAO.getInstance().setNextExecuteQuery(query);
		Handler myHandler = new Handler();
		Runnable myRunner = new Runnable() {
			public void run() {
				new AsyncTask<Void, Void, String>() {
					DAO dao;
					private ProgressDialog dialog;

					@Override
					protected void onPreExecute() {
						txtResult.setText("통계분석을 시작합니다.\n잠시만 기다려 주세요");

						dialog = new ProgressDialog(Statistic.this);
						this.dialog.setMessage("분석중..");
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
						if (this.dialog.isShowing() == true)
							this.dialog.dismiss();

						if (result != "error") {
							//list = dao.getMapFromJsonString(result);
							txtResult.setText(result);
						}
					}
				}.execute();
			}
		};
		myHandler.post(myRunner);
		/******************************************************************************************/
	}
}