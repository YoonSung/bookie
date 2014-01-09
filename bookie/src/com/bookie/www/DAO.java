package com.bookie.www;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DAO {
	
	private static DAO instance = new DAO();
	private String nextExecuteQuery;
	
	private SharedPreferences spf;
	private SharedPreferences.Editor editor;
	
	public static void centerToast(Context context, String message){
		Toast toast=Toast.makeText(context, message, Toast.LENGTH_LONG);
	      toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
	      toast.show();
	}
	
	public ArrayList<Map<String,String>> getMapFromJsonString(String json) {
		//Log.e("DAO", "json : "+json);
		//String temp = "{ \"list\" : "+json+"}";
		//String temp = json.substring(1, json.length()-2);
		//Log.e("DAO", "parsing json : "+temp);
		Gson gson = new Gson();
		//HashMap<String, String> mapData= gson.fromJson(temp, new TypeToken<HashMap<String,String>>(){}.getType());
		
		ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Type listType = new TypeToken<List<Map<String, String>>>() {}.getType();
		list = gson.fromJson(json, listType);
		
		System.out.println(list);
        //Log.e("DAO", "MAP : "+Hash);

		return list;
	}
	
	
	public void setNextExecuteQuery(String nextExecuteQuery) {
		this.nextExecuteQuery = nextExecuteQuery;
	}
	
	public static DAO getInstance() {
		return instance;
	}
	
	private DAO() {};
	
	public String getJsonFromServer() {

		BufferedReader br = null;
		StringBuilder sb = null;
		String resultJsonString = "[]";
		try {
			String tempURL = "http://10.73.43.166:3080/DBProject/soeun";// "http://192.168.1.135:3080/DBProject/";
			URL url = new URL(tempURL); 	

			// HttpURLConnection으로 url의 주소를 연결합니다.
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// 서버 접속시의 Time out(ms)
			conn.setConnectTimeout(10 * 1000);
			// Read시의 Time out(ms)
			conn.setReadTimeout(10 * 1000);

			conn.setDoOutput(true);
			// once you set the output to true, you don't really need to set the
			// request method to post, but I'm doing it anyway

			// 요청 방식 선택
			conn.setRequestMethod("POST");
			// 연결을 지속하도록 함
			conn.setRequestProperty("Connection", "Keep-Alive");
			// 캐릭터셋을 UTF-8로 요청
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			//String qr = URLEncoder.encode(query,"UTF-8");
			Log.e("Common", "sql = "+nextExecuteQuery);
			// 캐시된 데이터를 사용하지 않고 매번 서버로부터 다시 받음
			conn.setRequestProperty("Cache-Controll", "no-cache");
			// 서버로부터 JSON 형식의 타입으로 데이터 요청
			conn.setRequestProperty("Accept", "application/json");

			// InputStream으로 서버에서부터 응답을 받겠다는 옵션
			conn.setDoInput(true);

			PrintWriter out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
			//out.print(query);
			out.print(nextExecuteQuery);
			out.flush();
			out.close();

			// 위에서 Request Header정보를 설정해 주고 connect()로 연결을 한다.
			//conn.connect();

			int status = conn.getResponseCode();
			System.out.println("status : " + status);
			switch (status) {
			case 200:
			case 201:
				br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				sb = new StringBuilder();
				String line = br.readLine();

				
				if ( line == null ) {
					return resultJsonString;
				}
				Log.e("DAO", "line : "+line);
				
				while ( line != null) {
					sb.append(line + "\n");
					line = br.readLine();
				}
				br.close();
				
				Log.e("DAO","result : " + sb.toString());
				resultJsonString = sb.toString();
				break;
			case 400:
				resultJsonString = "error";
			}

			
		} catch (Exception e) {
			e.printStackTrace();
			resultJsonString = "error";
		}
		Log.e("DAO", "resultJsonString : "+resultJsonString);
		return resultJsonString;
	}
}
