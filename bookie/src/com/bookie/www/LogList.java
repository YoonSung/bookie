package com.bookie.www;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class LogList extends Activity {

	private ArrayList<LogData> logDataArrayList = new ArrayList<LogData>();
	private Button btnSaveRecord;
	private TextView txtDate, txtPage, txtContent;
	private String mBookTitle;
	private String mEmail;
	private ListView listView; 
	private ArrayList<Map<String,String>> list;
	private LogListAdapter logListAdapter ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loglist);

		Intent intent = getIntent();
		mBookTitle = intent.getStringExtra("bookTitle");
		SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE); 
		mEmail = prefs.getString("email", null);
		
		
		listView = (ListView) findViewById(R.id.loglist_list);
		
		
		/******************************************************************************************/
		String query = "select * from history where reader_no = " 
								+ "(select reader_no from reader where email ="
								+ "'"
								+ mEmail
								+ "'"
								+ ")"
								+ " and book_no = (select book_no from book where name ="
								+ "'"
								+ mBookTitle
								+ "'"
								+ ");";
		
		Log.e("Main", "sql : " + query);
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
		    					list = dao.getMapFromJsonString(result);
		    					updateView();
		    				}
		    			}
		    		}.execute();
		}};
		myHandler.post(myRunner);
/******************************************************************************************/
	}

	public void updateView () {
		if (list != null ) {
			for ( int i = 0 ; i < list.size() ; i++ ) {
				Map<String, String> map = list.get(i);
				logDataArrayList.add(
						new LogData(
								map.get("date"), 									
								Integer.parseInt(map.get("startpage")),
								Integer.parseInt(map.get("endpage")),
								map.get("comment")
								));
			}
		}
		
		logListAdapter = new LogListAdapter(this,R.layout.loglist_cell, logDataArrayList);
		listView.setAdapter(logListAdapter);
	}
	
	class LogListAdapter extends ArrayAdapter<LogData>  {

		private ArrayList<LogData> items;
		private LogData logData;
		public LogListAdapter(Context context, int textViewResourceId,
				ArrayList<LogData> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = vi.inflate(R.layout.loglist_cell, null);
			}

			logData = items.get(position);
			if (logData != null) {

				
				txtDate = (TextView) view.findViewById(R.id.loglist_custom_readDate);
				txtPage = (TextView) view.findViewById(R.id.loglist_custom_page);
				txtContent = (TextView) view.findViewById(R.id.loglist_custom_content);
				
				//글자 저장하는 부분 구현해야 함.
				txtDate.setText("독서날짜 : "+logData.getDateFormat());
				txtPage.setText("독서량 : "+logData.getStartPage()+"pg  ~  "+logData.getEndPage()+"pg");
				txtContent.setText(logData.getContent());
				
			}

			return view;
		}
	}
}
