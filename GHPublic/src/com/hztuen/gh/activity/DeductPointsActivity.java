package com.hztuen.gh.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;
import org.kymjs.kjframe.utils.KJLoger;

import com.gh.modol.BreakRulesModel;
import com.gh.modol.DeductPointsModel;
import com.gh.modol.ShipCircleListModel;
import com.hxkg.ghpublic.R;
import com.hztuen.gh.activity.Adapter.DeductPointsAdapter;
import com.hztuen.gh.activity.Adapter.ShipGoodsCircleListAdapter;
import com.hztuen.gh.contact.contants;
import com.hztuen.lvyou.utils.SystemStatic;
import com.hztuen.lvyou.utils.Util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DeductPointsActivity extends Activity implements OnClickListener{

	
	private RelativeLayout relative_title_final;
	private List<DeductPointsModel> modellist = new ArrayList<DeductPointsModel>();
	private DeductPointsAdapter deductAdapter;
	private ListView list_deduct_points;
	private Intent intent_type;
	private TextView textView1,id_tpye_dengji,id_type_fenshu;
	private Intent intent;
	private RelativeLayout relative_empty;//当没有扣分记录时的relativlayout
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deduct_points);
		init();
	}
	private void init()
	{
		
		deductAdapter = new DeductPointsAdapter(getApplicationContext(), modellist);
		list_deduct_points=(ListView)findViewById(R.id.list_deduct_points);
		textView1=(TextView)findViewById(R.id.textView1);
		id_type_fenshu=(TextView)findViewById(R.id.id_type_fenshu);
		id_tpye_dengji=(TextView)findViewById(R.id.id_tpye_dengji);
		relative_empty=(RelativeLayout)findViewById(R.id.relative_empty);
		intent_type=getIntent();
		String tpye=intent_type.getStringExtra("PointsType");
		
		if("码头扣分".equals(tpye))
		{
			GetDuckPointsTask();
			textView1.setText("码头诚信扣分");
			id_type_fenshu.setText("码头诚信分数");
			id_tpye_dengji.setText("码头诚信等级");
		}else if("船舶扣分".equals(tpye))
		{
		
			GetCredCardTask();
			textView1.setText("船舶诚信扣分");
			
			id_type_fenshu.setText("船舶诚信分数");
			id_tpye_dengji.setText("船舶诚信等级");
		}
		
		else if("公司扣分".equals(tpye))
		{
			intent=getIntent();
			String name = intent.getStringExtra("companyName");
		
			GetCompPointsTask(name);
			textView1.setText("公司诚信扣分");
			
			id_type_fenshu.setText("公司诚信分数");
			id_tpye_dengji.setText("公司诚信等级");
		}
		
		relative_title_final=(RelativeLayout)findViewById(R.id.relative_title_final);		
		relative_title_final.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				
			}
		});
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	// 船舶获取扣分列表
	private void GetCredCardTask() {

		// TODO Auto-generated method stub

		// 访问网络

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Shipname=" + SystemStatic.searchShipName);
		aa.add("Page=" + "0");

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.creditlist;
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

						JSONObject resultJO = new JSONObject(result);
						// String resultMsg = resultJO.getString("resultMsg");
						// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
						JSONObject res = new JSONObject(result);
						JSONArray data = res.getJSONArray("data");
						Log.i("GH_TEXT", data.length() + "我是数据的size");
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "无扣分记录",
									Toast.LENGTH_SHORT).show();
							
							
							relative_empty.setVisibility(View.VISIBLE);
							list_deduct_points.setVisibility(View.GONE);
						} else {
							for (int i = 0; i < data.length(); i++) {
								JSONObject temp = data.getJSONObject(i);
								DeductPointsModel model = new DeductPointsModel();
								// {"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
								model.setpenalty(temp.getString("penalty"));
								model.setreason(temp.getString("reason"));
								model.settime(temp.getString("time"));

								
								modellist.add(model);

							}

							list_deduct_points.setAdapter(deductAdapter);
						}
						// newsAdapter.pre(newslist);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
	
	
	
	//码头获取扣分列表
	
	
	
	private void GetDuckPointsTask() {

		// TODO Auto-generated method stub

		// 访问网络

		KJHttp kjh = new KJHttp();
		List<String> aa = new ArrayList<String>();
		aa.add("Wharf=" + SystemStatic.Wharfname);
		

		HttpParams params = null;
		try {
			params = Util.prepareKJparams(aa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 访问地址
		String toUrl = contants.wharfcreditlist;
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

						JSONObject resultJO = new JSONObject(result);
						// String resultMsg = resultJO.getString("resultMsg");
						// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
						JSONObject res = new JSONObject(result);
						JSONArray data = res.getJSONArray("data");
						Log.i("GH_TEXT", data.length() + "我是数据的size");
						if (data.length() == 0) {
							Toast.makeText(getApplicationContext(), "无扣分记录",
									Toast.LENGTH_SHORT).show();
							
							relative_empty.setVisibility(View.VISIBLE);
							list_deduct_points.setVisibility(View.GONE);
						} else {
							for (int i = 0; i < data.length(); i++) {
								JSONObject temp = data.getJSONObject(i);
								DeductPointsModel model = new DeductPointsModel();
								// {"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
								model.setpenalty(temp.getString("penalty"));
								model.setreason(temp.getString("reason"));
								model.settime(temp.getString("time"));

								
								modellist.add(model);

							}

							list_deduct_points.setAdapter(deductAdapter);
						}
						// newsAdapter.pre(newslist);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

				@Override
				public void onFailure(int errorNo, String strMsg) {
					super.onFailure(errorNo, strMsg);
				}
			});
		}
	}
	
	
	
	//公司获取扣分列表
	
	
	
		private void GetCompPointsTask(String name) {

			// TODO Auto-generated method stub

			// 访问网络

			KJHttp kjh = new KJHttp();
			List<String> aa = new ArrayList<String>();
			aa.add("Company=" + name);
			

			HttpParams params = null;
			try {
				params = Util.prepareKJparams(aa);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 访问地址
			String toUrl = contants.companycredit;
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
							modellist.clear();
							JSONObject resultJO = new JSONObject(result);
							// String resultMsg = resultJO.getString("resultMsg");
							// Log.i(TAG+":kymjs---resultMsg", resultJO.toString());
							JSONObject res = new JSONObject(result);
							JSONArray obj = res.getJSONArray("obj");
							Log.i("GH_TEXT", obj.length() + "我是数据的size");
							if (obj.length() == 0) {
								Toast.makeText(getApplicationContext(), "无扣分记录",
										Toast.LENGTH_SHORT).show();
								
								relative_empty.setVisibility(View.VISIBLE);
								list_deduct_points.setVisibility(View.GONE);
							} else {
								for (int i = 0; i < obj.length(); i++) {
									JSONObject temp = obj.getJSONObject(i);
									DeductPointsModel model = new DeductPointsModel();
									// {"data":[{"id":2,"penalty":-5,"reason":"分数","time":null}],"total":1,"page":1,"rows":1,"pages":1}
									model.setpenalty(temp.getString("penalty"));
									model.setreason(temp.getString("reason"));
									model.settime(temp.getString("ctime"));

									
									modellist.add(model);

								}

								list_deduct_points.setAdapter(deductAdapter);
							}
							// newsAdapter.pre(newslist);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}

					@Override
					public void onFailure(int errorNo, String strMsg) {
						super.onFailure(errorNo, strMsg);
					}
				});
			}
		}
	
	
	

}