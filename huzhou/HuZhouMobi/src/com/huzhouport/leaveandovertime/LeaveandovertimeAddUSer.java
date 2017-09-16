package com.huzhouport.leaveandovertime;

import java.util.ArrayList;
import java.util.HashMap;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.example.huzhouport.R;


import com.huzhouport.common.util.Query;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class LeaveandovertimeAddUSer extends Activity {
	private TextView tel;
	private Query query = new Query();
    private String departmentId;
    private String adduserid; //第几个人员选择
    private String adduserid1;//第一个的id
    private String adduserid2;
    private String adduserid3;
    private String userid; //用户id
    private ArrayList<HashMap<String ,Object>> departmentlist;
	private ArrayList<HashMap<String ,Object>> userlist;
	private ListView departmentlv,userlv;
	private int de=0; //用来判断是否没有数据   0you  1mei you 
	private int us=0;  //用来判断是否没有数据
	private EditText searchtext; // 搜索框
	private  String content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addresslist_newlist);
		
		TextView title=(TextView) findViewById(R.id.addresslist_new_title);
		title.setFocusable(true);
		title.setText("人员选择");
		
		Intent intent = getIntent();
		departmentId = intent.getStringExtra("departmentId");
		adduserid=intent.getStringExtra("adduserid");
		adduserid1=intent.getStringExtra("adduserid1");
		adduserid2=intent.getStringExtra("adduserid2");
		adduserid3=intent.getStringExtra("adduserid3");
		userid=intent.getStringExtra("userid");
		System.out.println("adduserid2=="+adduserid2+" and"+userid);
		
		departmentlv=(ListView) findViewById(R.id.addresslist_new_viewlist);
		userlv=(ListView) findViewById(R.id.addresslist_new_viewlist1);
		//获取显示列表信息
		ListTask task = new ListTask(this);  // 异步
		task.execute();

		

		
		// 返回
		ImageButton back = (ImageButton) findViewById(R.id.addresslist_new_back);
		back.setOnClickListener(new Back());

        //子部门button
		Button departmentitemgone=(Button) findViewById(R.id.address_newbutton);
        Button useritemgone=(Button) findViewById(R.id.address_newbutton1);
		departmentitemgone.setOnClickListener(new Departmenttiemgone());
		useritemgone.setOnClickListener(new Useritemgone());
		
		
		
		//搜索的图标	
	    ImageButton search=(ImageButton)findViewById(R.id.addresslist_new_searchbutton);
	 	search.setOnClickListener(new Search());
		
		searchtext=(EditText) findViewById(R.id.addresslist_new_searchtextname);
		searchtext.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				content=searchtext.getText().toString();
		       if(content.length()==0){
		    	departmentId="-1";
		    	ListTask task = new ListTask(LeaveandovertimeAddUSer.this);  // 异步
		    	task.execute();
		       }else{
		    	
		       }
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
				
			}
		});
		
		
		//初始界面	
	    ImageButton zhuye=(ImageButton)findViewById(R.id.addresslist_new_zhuye);
	    zhuye.setOnClickListener(new Zhuye());
		


	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
            //可以根据多个请求代码来作相应的操作
            if(20==resultCode)
            {
               setResult(20);
               if(departmentId.equals("-1")){
            	   
               }else{
         	   finish();}
            }
            if(30==resultCode)
            {
            	adduserid = data.getStringExtra("adduserid"); 
    		String	adduser_name = data.getStringExtra("adduser_name"); 
    	    String 	adduser_id = data.getStringExtra("adduser_id"); 
            	
    	    Intent intent = new Intent();
			intent.putExtra("adduser_id", adduser_id);// 设置回传的意图
			intent.putExtra("adduser_name", adduser_name);// 设置回传的意图
			intent.putExtra("adduserid", adduserid);
			setResult(30, intent);
         	finish();
            }
            
            
            
            super.onActivityResult(requestCode, resultCode, data);
    }

	public class Zhuye implements View.OnClickListener {
		public void onClick(View v) {
			
			setResult(20);
			finish();
			
			
		}
	}
	
	
	public class Departmenttiemgone implements View.OnClickListener {
		public void onClick(View v) {
			
			if(departmentlv.getVisibility()==0){
			 departmentlv.setVisibility(View.GONE);	
			}else{
				departmentlv.setVisibility(View.VISIBLE);
			}
			
			
			
		}
	}
	public class Useritemgone implements View.OnClickListener {
		public void onClick(View v) {

			if(userlv.getVisibility()==0){
				userlv.setVisibility(View.GONE);	
				}else{
					userlv.setVisibility(View.VISIBLE);
				}
		}
	}
	

	

	public class ButtonTel1 implements View.OnClickListener {
		public void onClick(View v) {
			String address = tel.getText().toString();
			System.out.println("address" + address);
		}
	}

	public class Back implements View.OnClickListener {
		public void onClick(View v) {
//			Intent intent = new Intent(AddresslistView.this, OfficOA.class);
//			startActivity(intent);
			finish();
		}
	}

	public class Search implements View.OnClickListener {
		public void onClick(View v) {
			content=searchtext.getText().toString();
		       if(content.length()==0){
		    		Toast.makeText(LeaveandovertimeAddUSer.this, "请先在搜索框里输入内容", Toast.LENGTH_SHORT).show();
		       }else{
		    	   ListTask1 task = new ListTask1(LeaveandovertimeAddUSer.this);  // 异步
				    task.execute();
		       }

		}
	}


	public class SearchButton implements View.OnClickListener {
		public void onClick(View v) {
			Intent intent = new Intent(LeaveandovertimeAddUSer.this,
					LeaveandovertimeAddUSer.class);
			intent.putExtra("name", searchtext.getText().toString());
			startActivity(intent);
			
		}
	}
	
	class ListTask extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask(Context context){
	    	  pDialog = new ProgressDialog(LeaveandovertimeAddUSer.this);
			  pDialog.setTitle("提示");
			  pDialog.setMessage("正在加载中，请稍候。。。");
			  pDialog.setCancelable(true);
			  pDialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.setButton("取消", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask.this != null && ListTask.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask.this.cancel(true);
						
					}
				});
				pDialog.show();
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
			if(isCancelled()) return null;//取消异步
			
			
			return query.newaddresslist(departmentId);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if(result==null){
			Toast.makeText(LeaveandovertimeAddUSer.this, "网络异常", Toast.LENGTH_SHORT).show();
			}else{
			getdepartmentlist(result);
			showdepartmentlist();
			getuserlist(result);
			showuserlist();
			if(de==1&&us==1){
				Toast.makeText(LeaveandovertimeAddUSer.this, "没有数据", Toast.LENGTH_SHORT).show();
			}
			}
			super.onPostExecute(result);
		}
		  
	  }
	
	public void  getdepartmentlist(String result){
			 
			JSONTokener jsonParser_User = new JSONTokener(result);
			try {
			 JSONObject data = (JSONObject) jsonParser_User.nextValue();
			 JSONArray	jsonArray=data.getJSONArray("listdepartment");
			 departmentlist=new ArrayList<HashMap<String, Object>>();
			 if(jsonArray.length()==0){
				 findViewById(R.id.address_newbutton).setVisibility(View.GONE);
				 findViewById(R.id.addresslist_new_viewlist).setVisibility(View.GONE);
				 de=1;
				 }else{
					 de=0; 
				findViewById(R.id.address_newbutton).setVisibility(View.VISIBLE);
				findViewById(R.id.addresslist_new_viewlist).setVisibility(View.VISIBLE);
			 for(int i=0;i<jsonArray.length();i++){
				 HashMap<String, Object> departmentlistmap = new HashMap<String, Object>();	
				 JSONObject jsonObject =(JSONObject) jsonArray.opt(i);
			 String name=jsonObject.getString("departmentName");
	         String id=jsonObject.getString("departmentId");
			 departmentlistmap.put("name", name);
			 departmentlistmap.put("id", id);
			 departmentlist.add(departmentlistmap);
			 }
			}
			} catch (Exception e) {
				Toast.makeText(LeaveandovertimeAddUSer.this, "网络异常", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
	 }
	
	public void  getuserlist(String result){
		 
		JSONTokener jsonParser_User = new JSONTokener(result);
		try {
		 JSONObject data = (JSONObject) jsonParser_User.nextValue();
		 JSONArray	jsonArray=data.getJSONArray("listuser");
		 userlist=new ArrayList<HashMap<String, Object>>();
		 if(jsonArray.length()==0){
			 findViewById(R.id.address_newbutton1).setVisibility(View.GONE);
			 findViewById(R.id.addresslist_new_viewlist1).setVisibility(View.GONE);
			 us=1;
			 }else{us=0;
		findViewById(R.id.address_newbutton1).setVisibility(View.VISIBLE);		 
		findViewById(R.id.addresslist_new_viewlist1).setVisibility(View.VISIBLE);
		 for(int i=0;i<jsonArray.length();i++){
			 HashMap<String, Object> userlistmap = new HashMap<String, Object>();	
			 JSONObject jsonObject =(JSONObject) jsonArray.opt(i);
		 String name=jsonObject.getString("name");
         String id=jsonObject.getString("userId");
         userlistmap.put("name", name);
         userlistmap.put("id", id);
		 userlist.add(userlistmap);
		 }
		}
		} catch (Exception e) {
			Toast.makeText(LeaveandovertimeAddUSer.this, "网络异常", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
 }	
	
	public void showdepartmentlist(){

		SimpleAdapter adapter = new SimpleAdapter(LeaveandovertimeAddUSer.this,departmentlist, R.layout.addresslist_departmentitem, new String[] {"name", "id" }, new int[] { R.id.address_departmentitem_name,R.id.address_departmentitem_id});
		departmentlv.setAdapter(adapter);
		departmentlv.setOnItemClickListener(new Departmentlistitem());

}	
	
	class Departmentlistitem implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			
	       TextView tv_id=(TextView) arg1.findViewById(R.id.address_departmentitem_id);
			 Intent intent=new Intent(LeaveandovertimeAddUSer.this,LeaveandovertimeAddUSer.class);
		     intent.putExtra("departmentId", tv_id.getText().toString());
		     intent.putExtra("adduserid", adduserid);
		     intent.putExtra("userid", userid);
			 intent.putExtra("adduserid1",adduserid1);
			 intent.putExtra("adduserid2",adduserid2);
			 intent.putExtra("adduserid3",adduserid3);
		     startActivityForResult(intent,100);
		}
		  
	  }
	class Userlistitem implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			TextView usertv_id=(TextView) arg1.findViewById(R.id.leaveandovertime_adduser_id);
			TextView usertv_name=(TextView) arg1.findViewById(R.id.leaveandovertime_adduser_name);
			
			if(userid.equals(usertv_id.getText().toString())){
				Toast.makeText(LeaveandovertimeAddUSer.this, "不能选择自己", Toast.LENGTH_SHORT).show();
			}else{
			
			if(adduserid.equals("1")){
				if(adduserid2.equals(usertv_id.getText().toString())||adduserid3.equals(usertv_id.getText().toString())){
					Toast.makeText(LeaveandovertimeAddUSer.this, "审批人不能选择同一个人", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent();
					intent.putExtra("adduser_id", usertv_id.getText().toString());// 设置回传的意图
					intent.putExtra("adduser_name", usertv_name.getText().toString());// 设置回传的意图
					intent.putExtra("adduserid", adduserid);
					setResult(30, intent);
					finish();
				}
			}
			if(adduserid.equals("2")){
				if(adduserid1.equals(usertv_id.getText().toString())||adduserid3.equals(usertv_id.getText().toString())){
					Toast.makeText(LeaveandovertimeAddUSer.this, "审批人不能选择同一个人", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent();
					intent.putExtra("adduser_id", usertv_id.getText().toString());// 设置回传的意图
					intent.putExtra("adduser_name", usertv_name.getText().toString());// 设置回传的意图
					intent.putExtra("adduserid", adduserid);
					setResult(30, intent);
					finish();
				}
			}
			if(adduserid.equals("3")){
				if(adduserid2.equals(usertv_id.getText().toString())||adduserid1.equals(usertv_id.getText().toString())){
					Toast.makeText(LeaveandovertimeAddUSer.this, "审批人不能选择同一个人", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent();
					intent.putExtra("adduser_id", usertv_id.getText().toString());// 设置回传的意图
					intent.putExtra("adduser_name", usertv_name.getText().toString());// 设置回传的意图
					intent.putExtra("adduserid", adduserid);
					setResult(30, intent);
					finish();
				}
			}
			
			
			}
			
			//Toast.makeText(LeaveandovertimeAddUSer.this, usertv_name.getText().toString(), Toast.LENGTH_SHORT).show();
		/*	Intent intent = new Intent();
			intent.putExtra("adduser_id", usertv_id.getText().toString());// 设置回传的意图
			intent.putExtra("adduser_name", usertv_name.getText().toString());// 设置回传的意图
			intent.putExtra("adduserid", adduserid);
			setResult(30, intent);
			finish();
			*/
			
			
			/*Intent intent = new Intent(LeaveandovertimeAddUSer.this,AddresslistShowID.class);
			intent.putExtra("id",usertv_id.getText().toString());
			intent.putExtra("name",usertv_name.getText().toString());
			mContext.startActivity(intent);*/
		}
		  
	  }
	public void showuserlist(){

		SimpleAdapter adapter = new SimpleAdapter(LeaveandovertimeAddUSer.this,userlist, R.layout.leaveandovertime_adduser, new String[] {"name", "id"}, new int[] { R.id.leaveandovertime_adduser_name,R.id.leaveandovertime_adduser_id});
		userlv.setAdapter(adapter);
		userlv.setOnItemClickListener(new Userlistitem());

}	
	
	class ListTask1 extends AsyncTask<String ,Integer,String>{
		  ProgressDialog	pDialog	= null;
		  public ListTask1(){
			  
		  }
	      @SuppressWarnings("deprecation")
		public ListTask1(Context context){
	    	  pDialog = new ProgressDialog(LeaveandovertimeAddUSer.this);
			  pDialog.setTitle("提示");
			  pDialog.setMessage("正在加载中，请稍候。。。");
			  pDialog.setCancelable(true);
			  pDialog.setOnCancelListener(new OnCancelListener()
				{
					
					@Override
					public void onCancel(DialogInterface dialog)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask1.this != null && ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask1.this.cancel(true);
						
					}
				});
				pDialog.setButton("取消", new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog,int which)
					{
						if (pDialog != null)
							pDialog.dismiss();
						if (ListTask1.this != null && ListTask1.this.getStatus() == AsyncTask.Status.RUNNING)
							ListTask1.this.cancel(true);
						
					}
				});
				pDialog.show();	
		  }
		  
		@Override
		protected String  doInBackground(String... params) {
		
			
			if(isCancelled()) return null;//取消异步
			return query.selectnewaddresslist(content);
		}
		@Override
		protected void onPostExecute(String result) {
			if (pDialog != null)
				pDialog.dismiss();
			if(result==null){
				Toast.makeText(LeaveandovertimeAddUSer.this, "网络异常", Toast.LENGTH_SHORT).show();
				}else{
			getuserlist(result);
			showuserlist();
			findViewById(R.id.address_newbutton).setVisibility(View.GONE);
			findViewById(R.id.addresslist_new_viewlist).setVisibility(View.GONE);
			findViewById(R.id.address_newbutton1).setVisibility(View.GONE);
			if(us==1){
				Toast.makeText(LeaveandovertimeAddUSer.this, "没有数据", Toast.LENGTH_SHORT).show();
			}
			}
			super.onPostExecute(result);
		}
		  
	  }
	
	

}
