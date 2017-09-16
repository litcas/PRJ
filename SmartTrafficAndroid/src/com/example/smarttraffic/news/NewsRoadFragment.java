package com.example.smarttraffic.news;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;

import com.example.smarttraffic.R;
import com.example.smarttraffic.network.HttpThread;
import com.example.smarttraffic.network.UpdateView;
import com.example.smarttraffic.util.StartIntent;

public class NewsRoadFragment extends Fragment implements UpdateView
{

	@SuppressWarnings("unchecked")
	public void update(Object data)
	{
		if (data instanceof List<?>)
		{
			List<News> dataList = (List<News>) data;
			if (contentListView.getAdapter() == null)
			{
				newsAdapter = new NewsAdapter(getActivity(), dataList);
				contentListView.setAdapter(newsAdapter);
			} else
			{
				newsAdapter.update(dataList);
			}
		}
	}

	GridView selectGridView;
	ListView contentListView;
	public CheckAdapter checkAdapter;
	public NewsAdapter newsAdapter;

	NewsRequest newsRequest;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_news_road,
				container, false);

		selectGridView = (GridView) rootView
				.findViewById(R.id.news_road_select_item_gridview);
		contentListView = (ListView) rootView
				.findViewById(R.id.news_road_select_content_listview);
		contentListView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(android.widget.AdapterView<?> parent,
					View view, int position, long id)
			{
				int newsID = ((News) (newsAdapter.getItem(position))).getId();
				Bundle bundle = new Bundle();
				bundle.putInt(NewsDetailActivity.DETAIL_NEWS_ID, newsID);

				StartIntent.startIntentWithParam(getActivity(),
						NewsDetailActivity.class, bundle);
			};
		});

		initGridView();
		newsRequest = new NewsRequest();
		newsRequest.setType(1);
		newsRequest.setSubType(checkAdapter.getSelectForInt());

		new HttpThread(new NewsSearch(), newsRequest, this, getActivity(),
				R.string.error_news_info).start();

		return rootView;
	}

	public void initGridView()
	{
		checkAdapter = new CheckAdapter(getActivity(), initCheckItem());

		selectGridView.setAdapter(checkAdapter);

		selectGridView.setOnItemClickListener(new ItemClick());
	}

	private List<NewsType> initCheckItem()
	{
		List<NewsType> list = new ArrayList<NewsType>();

		String[] nameStrings = getResources().getStringArray(
				R.array.news_select_type);

		for (int i = 0; i < 6; i++)
		{
			NewsType tempNewsType = new NewsType();
			tempNewsType.setTypeID(i);
			tempNewsType.setTypeName(nameStrings[i]);
			tempNewsType.setCheck(true);
			list.add(tempNewsType);
		}

		return list;
	}

	class ItemClick implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			checkAdapter.setChange(position);
			newsRequest.setSubType(checkAdapter.getSelectForInt());

			if(newsAdapter!=null)
				newsAdapter.clear();
			
			new HttpThread(new NewsSearch(), newsRequest,
					NewsRoadFragment.this, NewsRoadFragment.this.getActivity(),
					R.string.error_news_info).start();
		}
	}
}