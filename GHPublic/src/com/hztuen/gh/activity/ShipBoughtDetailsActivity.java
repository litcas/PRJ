package com.hztuen.gh.activity;

import com.hxkg.ghpublic.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShipBoughtDetailsActivity extends Activity
{	
	private RelativeLayout relative_title_final;
	private TextView tv_title, tv_shiptype, tv_shipname, tv_tons, tv_price,
			tv_linkman, tv_tel, tv_remark,tv_shipage;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ship_bought_deetails);
		init();
	}
	
	private void init() {

		relative_title_final = (RelativeLayout) findViewById(R.id.relative_title_final);
		relative_title_final.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();

			}
		});
		tv_title = (TextView) findViewById(R.id.text1_context);
		tv_shiptype = (TextView) findViewById(R.id.text2_context);
		tv_shipname = (TextView) findViewById(R.id.text3_context);
		tv_tons = (TextView) findViewById(R.id.text4_context);
		tv_price = (TextView) findViewById(R.id.text5_context);
		tv_linkman = (TextView) findViewById(R.id.text6_context);
		tv_tel = (TextView) findViewById(R.id.text8_context);
		tv_remark = (TextView) findViewById(R.id.text9_context);
		tv_shipage = (TextView) findViewById(R.id.text10_context);


		Intent in = getIntent();
		String title = in.getStringExtra("title");
		String shiptype = in.getStringExtra("shiptype");
		String shipname = in.getStringExtra("shipname");
		String tons = in.getStringExtra("load");
		String price = in.getStringExtra("price");
		String linkman = in.getStringExtra("linkman");
		String tel = in.getStringExtra("tel");
		String remark = in.getStringExtra("remark");
		String shipage = in.getStringExtra("shipage");

		tv_title.setText(title);
		tv_shiptype.setText(shiptype);
		tv_shipname.setText(shipname);
		tv_tons.setText(tons);
		tv_price.setText(price);
		tv_linkman.setText(linkman);
		tv_tel.setText(tel);
		tv_remark.setText(remark);
		tv_shipage.setText(shipage);

	}
	

}