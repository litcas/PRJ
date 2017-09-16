package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.hxkg.network.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.ShipCircleListModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.PopShipNameAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle; 
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;

/**
 * 船货圈发布船舶出售信息界面
 * @author hztuen7
 *
 */
public class SendShipSellActivity extends Activity implements OnClickListener{

	private Button face_talk,make_price,btn_post;
	private EditText edit_price;
	private TextView text_yuan,tv_line,tv_Shiptype;
	private RelativeLayout relative_title_final,relativie_ship_type;
	private EditText tv_Title,tv_Shipname,tv_Load,tv_Linkman,tv_Tel,tv_Remark,tv_Shipage;
	public ArrayList<String> from_lists;
	private PopShipNameAdapter fromAdapter;
	public PopupWindow popupWindowArea;
	private View contentView;
	private ListView listview_start;
	int typeid=1,id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_ship_sell_msg);
		init();
	}
	private void init()
	{
		face_talk=(Button)findViewById(R.id.face_talk);	
		make_price=(Button)findViewById(R.id.make_price);	
		edit_price=(EditText)findViewById(R.id.edit_price);
		text_yuan=(TextView)findViewById(R.id.text_yuan);
		tv_line=(TextView)findViewById(R.id.tv_line);
		
		face_talk.setBackgroundColor(getResources().getColor(R.color.home_second_header_color));
		make_price.setBackgroundColor(getResources().getColor(R.color.text_color_c));
		face_talk.setTextColor(getResources().getColor(R.color.text_color_a));
		make_price.setTextColor(getResources().getColor(R.color.text_color_b));
		edit_price.setVisibility(View.GONE);
		text_yuan.setVisibility(View.GONE);
		tv_line.setVisibility(View.GONE);
		
		
		face_talk.setOnClickListener(this);
		make_price.setOnClickListener(this);
		
		tv_Title=(EditText)findViewById(R.id.text1_context);
		tv_Shiptype=(TextView)findViewById(R.id.text2_context);
		tv_Shipname=(EditText)findViewById(R.id.text3_context);
		tv_Load=(EditText)findViewById(R.id.text15_context);
		
		tv_Shipage=(EditText)findViewById(R.id.text4_context);
		
		tv_Linkman=(EditText)findViewById(R.id.text6_context);
		tv_Tel=(EditText)findViewById(R.id.text8_context);
		
		tv_Tel.setText(SystemStatic.phoneNum);
		tv_Remark=(EditText)findViewById(R.id.text9_context);
		
		relativie_ship_type = (RelativeLayout) findViewById(R.id.relative2);
		relativie_ship_type.setOnClickListener(this);
		
		btn_post = (Button) findViewById(R.id.btn_post);
		btn_post.setOnClickListener(this);
		
		from_lists = new ArrayList<String>();
		fromAdapter = new PopShipNameAdapter(this, from_lists, 6);
		
		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		dataInit();
	}
	
	private void dataInit()
	{
		ShipCircleListModel model=(ShipCircleListModel) getIntent().getSerializableExtra("ShipCircleListModel");
		if(model==null)
			return;
		id=Integer.valueOf(model.getid());
		tv_Title.setText(model.gettitle());
		tv_Shiptype.setText(model.shiptypename);
		typeid=Integer.valueOf(model.shiptypeid);
		
		tv_Shipname.setText(model.shipname);
		tv_Load.setText(model.load);  
		tv_Linkman.setText(model.getcontent());
		tv_Tel.setText(model.tel);
		tv_Remark.setText(model.remark); 
		
		tv_Shipage.setText(model.age);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//点击面议
		case R.id.face_talk:
			face_talk.setBackgroundColor(getResources().getColor(R.color.home_second_header_color));
			make_price.setBackgroundColor(getResources().getColor(R.color.text_color_c));
			face_talk.setTextColor(getResources().getColor(R.color.text_color_a));
			make_price.setTextColor(getResources().getColor(R.color.text_color_b));
			edit_price.setVisibility(View.GONE);
			text_yuan.setVisibility(View.GONE);
			tv_line.setVisibility(View.GONE);
			
			break;
		//点击自定义价格
		case R.id.make_price:
			make_price.setBackgroundColor(getResources().getColor(R.color.home_second_header_color));
			face_talk.setBackgroundColor(getResources().getColor(R.color.text_color_c));
			make_price.setTextColor(getResources().getColor(R.color.text_color_a));
			face_talk.setTextColor(getResources().getColor(R.color.text_color_b));
			edit_price.setVisibility(View.VISIBLE);
			text_yuan.setVisibility(View.VISIBLE);
			tv_line.setVisibility(View.VISIBLE);
			break;
		//点击提交按钮
		case R.id.btn_post:
			
			SendShipSellTask();
			break;
		//点击船型	
		case R.id.relative2:
			
			HttpRequest hr1=new HttpRequest(new HttpRequest.onResult() 
			{
				
				@Override
				public void onSuccess(String result) 
				{
					try
					{
						JSONArray array=new JSONArray(result.trim());
						for(int i=0;i<array.length();i++)
						{
							JSONObject object=array.getJSONObject(i);
							from_lists.add(object.getString("shiptype"));								
						}
						listview_start.setAdapter(fromAdapter);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
				@Override
				public void onError(int httpcode) {
					// TODO Auto-generated method stub
					
				}
			});
			Map<String, Object> map=new HashMap<>();
			hr1.post(contants.baseUrl+"ShipTypeList", map);
			
			 contentView = getLayoutInflater().inflate(R.layout.pop_from, null);
			 TextView pop_dis6 = (TextView) contentView.findViewById(R.id.pop_dis);
			 listview_start=(ListView) contentView.findViewById(R.id.listview_start);
			 TextView tv_title=(TextView)contentView.findViewById(R.id.textView2);
			 tv_title.setText("请选择船型");
			 listview_start.setOnItemClickListener(new OnItemClickListener() {	

					@Override
					public void onItemClick(AdapterView<?> parent,
							View view, int position, long id) {
						tv_Shiptype.setText(from_lists.get(position));
						typeid=position+1;
						popupWindowArea.dismiss();
						from_lists.clear();
											
					}
				});
			 
			 listview_start.setAdapter(fromAdapter);
			// listview_goodsname.setAdapter(goodsnameAdapter);
				pop_dis6.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
						popupWindowArea.dismiss();
						from_lists.clear();

					}
				});

				LinearLayout parent6 = (LinearLayout) this.findViewById(R.id.father_send_sell_msg);//父窗口view  
				popupWindowArea = new PopupWindow(contentView, parent6.getWidth()*4/5, ViewGroup.LayoutParams.WRAP_CONTENT);
				popupWindowArea.setFocusable(true);
				popupWindowArea.setOutsideTouchable(true);
				popupWindowArea.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
				popupWindowArea.setBackgroundDrawable(new PaintDrawable());
				WindowManager.LayoutParams lp6 = getWindow().getAttributes();
				lp6.alpha = 0.5f;
				getWindow().setAttributes(lp6);
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				popupWindowArea.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss() {
						WindowManager.LayoutParams lp = getWindow().getAttributes();
						lp.alpha = 1f;
						getWindow().setAttributes(lp);
					}
				});
				
				popupWindowArea.showAtLocation(parent6, Gravity.TOP, 0, parent6.getHeight()*1/8);
			
			
			
			break;			
		default:
			break;
		}
	}
	
	
	// 发布售船信息
		private void SendShipSellTask() {

			// TODO Auto-generated method stub

			// 访问网络
			String Title=tv_Title.getText().toString();
			String Shiptype=tv_Shiptype.getText().toString();
			String Shipname=tv_Shipname.getText().toString();
			String Load=tv_Load.getText().toString();
			String Price=edit_price.getText().toString().equals("")?"面议":edit_price.getText().toString()+"元";
			String Linkman=tv_Linkman.getText().toString();
			String Tel=tv_Tel.getText().toString();
			String Remark=tv_Remark.getText().toString();
			String Age=tv_Shipage.getText().toString();
			if(Title.equals("")||Shiptype.equals("")||Shipname.equals("")||Load.equals("")||
					Tel.equals("")||Age.equals("")){
				Toast.makeText(getApplicationContext(), "请完善必填信息", Toast.LENGTH_SHORT).show();
			}
			else{
			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Title=" + Title);
			aa.add("ShiptypeID=" +typeid);
			aa.add("Shipname=" + Shipname);
			aa.add("Age=" + Age);
			aa.add("Load=" + Load);
			aa.add("Price=" + Price);
			aa.add("Linkman=" + Linkman);
			aa.add("Tel=" + Tel);
			aa.add("Remark=" + Remark);
			aa.add("Userid=" + SystemStatic.userId);
			aa.add("tradetype=" + 4);
			aa.add("id=" + id);
			
			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.baseUrl+"postTrade";
			if (!toUrl.equals("")) {
				kjh.post(toUrl, params, false, new HttpCallBack() {
					@Override
					public void onSuccess(Map<String, String> headers, byte[] t) {
						super.onSuccess(headers, t);
						// 获取cookie
						KJLoger.debug("===" + headers.get("Set-Cookie"));
						// Log.i(TAG+":kymjs---http", new String(t));
						String result = new String(t).trim();
						try {
							
							JSONObject  res = new JSONObject(result);
							String resultcode = res.getString("resultcode");
							
							if (!"".equals(resultcode) && resultcode != null) {
								if ("-1".equals(resultcode)) {
									Toast.makeText(getApplicationContext(),
											"提交失败", Toast.LENGTH_SHORT).show();
								} else if (Integer.valueOf(resultcode)
										.intValue() >=0) {
									Toast.makeText(getApplicationContext(),
											"提交成功", Toast.LENGTH_SHORT).show();
									SendShipSellActivity.this.finish();
								}
							}
							else{
								Toast.makeText(getApplicationContext(),
										"有错误，请检查", Toast.LENGTH_SHORT).show();
							}
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					@Override
					public void onFailure(int errorNo, String strMsg) {
						// 关闭进度条
						// Log.d(TAG, strMsg);
						// Util.getTip(cxt, errorNo+"");
						// mSVProgressHUD.showErrorWithStatus("验证过程出现问题");
						super.onFailure(errorNo, strMsg);
					}
				});
			}
		}
		}

	
	
	

}