package com.cuje.myapplication.fragment.news.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cuje.myapplication.R;
import com.cuje.myapplication.activity.NewsDetailActivity;
import com.cuje.myapplication.adapter.RecyclerViewAdapter;
import com.cuje.myapplication.beans.NewsBean;
import com.cuje.myapplication.configure.Conf;
import com.cuje.myapplication.menu.ExpandableItem;
import com.cuje.myapplication.menu.ExpandableSelector;
import com.cuje.myapplication.menu.OnExpandableItemClickListener;
import com.cuje.myapplication.utils.PixUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.easyrecyclerview.swipe.SwipeRefreshLayout;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by CUJE on 2017/4/10.
 */

public class NewsFragment extends Fragment {
    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;          //新闻List

    @BindView(R.id.fab)
    ExpandableSelector fab;     //菜单

    Unbinder unbinder;

    private String currentNewId;        //当前新闻的ID
    private RecyclerViewAdapter adapter;    //list 的 适配器
    private List<NewsBean> newsList;        //新闻的list

    private OkHttpClient client;        //okhhtp 的客户端
    private int page = 1;       //当前界面
    private String url;     //api的网址
    private final static String showapi_appid = "32703";
    private final static String showapi_sign = "9c6c99e7d81145f28f9a2567b0757389";

    private JSONArray jsonArray = null;     //接收返回数据的ARRAY
    private NewsBean newsBean = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setAdapter(adapter = new RecyclerViewAdapter(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        currentNewId = Conf.ID_GUONEI;      //最开始进入该页面默认加载国内新闻

        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixUtil.convertDpToPixel(16, getContext()));
        itemDecoration.setPaddingEdgeSide(false);
        itemDecoration.setPaddingStart(false);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);

        initializeSizesExpandableSelector();        //初始化菜单

        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {

            @Override
            public void onMoreShow() {
                getData(currentNewId);
            }

            @Override
            public void onMoreClick() {

            }
        });

        //下拉刷新
//        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//
//            @Override
//            public void onRefresh() {
//                recyclerView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.clear();
//                        page = 0;
//                        getData(currentNewId);
//                    }
//                }, 1000);
//
//            }
//        });

        //设置点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent();
                intent.putExtra("title", adapter.getAllData().get(position).getTitle());
                intent.putExtra("html", adapter.getAllData().get(position).getHtml());
                intent.putExtra("content", adapter.getAllData().get(position).getContent());
                intent.setClass(getActivity(), NewsDetailActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    //初始化菜单选项
    private void initializeSizesExpandableSelector() {
        List<ExpandableItem> expandableItems = new ArrayList<>();

        expandableItems.add(new ExpandableItem("国内", Conf.TYPE_GUONEI));
        expandableItems.add(new ExpandableItem("国际", Conf.TYPE_GUOJI));
        expandableItems.add(new ExpandableItem("军事", Conf.TYPE_JUNSHI));
        expandableItems.add(new ExpandableItem("体育", Conf.TYPE_TIYU));
        expandableItems.add(new ExpandableItem("娱乐", Conf.TYPE_YULE));
        expandableItems.add(new ExpandableItem("科技", Conf.TYPE_KEJI));

        fab.showExpandableItems(expandableItems);
        fab.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override
            public void onExpandableItemClickListener(final int index, View view) {
                final ExpandableItem item = fab.getExpandableItem(index);
                swipeFirstItem(index, item);
                if(index!=0){
                    new Handler().postDelayed(new Runnable() {//延时300ms执行，否则会卡顿
                        @Override
                        public void run() {
                            adapter.clear();
                            switch (item.getType()){
                                case 1:
                                    currentNewId = Conf.ID_GUONEI;
                                    break;
                                case 2:
                                    currentNewId = Conf.ID_GUOJI;
                                    break;
                                case 3:
                                    currentNewId = Conf.ID_JUNSHI;
                                    break;
                                case 4:
                                    currentNewId = Conf.ID_TIYU;
                                    break;
                                case 5:
                                    currentNewId = Conf.ID_YULE;
                                    break;
                                case 6:
                                    currentNewId = Conf.ID_KEJI;
                                    break;
                            }
                            getData(currentNewId);

                        }
                    },300);
                }
                fab.collapse();
            }

            private void swipeFirstItem(int position, ExpandableItem clickedItem) {
                ExpandableItem firstItem = fab.getExpandableItem(0);
                fab.updateExpandableItem(0, clickedItem);
                fab.updateExpandableItem(position, firstItem);
            }
        });
    }

    @OnClick(R.id.fab)
    public void onClick() {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getData(currentNewId);
    }

    @OnClick(R.id.recyclerView)
    public void onViewClicked() {
    }

    private void getData(String type) {

        url = "http://route.showapi.com/109-35?" +
                "showapi_appid=" + showapi_appid + "&" +
                "showapi_sign=" + showapi_sign + "&" +
                "page=" + String.valueOf(page) + "&" +
                "channelid=5572a108b3cdc86cf39001cd&" +
                "needAllList=0&" +
                "needHtml=1&" +
                "needContent=1&"+
                "channelId="+type;

        Log.i("url", url);
        client = new OkHttpClient();
        final Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    newsList = new ArrayList<NewsBean>();
                    JSONObject object = new JSONObject(response.body().string().toString());
                    jsonArray = object.getJSONObject("showapi_res_body").getJSONObject("pagebean").getJSONArray("contentlist");
                    Log.i("TAG", jsonArray.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        object = jsonArray.getJSONObject(i);
                        newsBean = new NewsBean();
                        newsBean.setChannelId(object.getString("channelId"));
                        newsBean.setChannelName(object.getString("channelName"));
                        newsBean.setDesc(object.getString("desc"));
                        newsBean.setHtml(object.getString("html"));
                        newsBean.setLink(object.getString("link"));
                        newsBean.setPubDate(object.getString("pubDate"));
                        newsBean.setSource(object.getString("source"));
                        newsBean.setTitle(object.getString("title"));
                        newsBean.setHavePic(object.getString("havePic"));
                        newsBean.setContent(object.getString("content"));
                        if (object.getString("havePic").equals("false")) {
                            newsBean.setImageurls("");
                        } else {
                            newsBean.setImageurls(object.getJSONArray("imageurls").getJSONObject(0).getString("url"));
                        }
                        newsList.add(newsBean);
                    }
                } catch (Exception e) {
                    Log.i("Exception in NF", "----------------------" + e.toString());
                }
                new RunUi().execute(newsList);
            }
        });
        page = page + 1;
    }

    //创建一个多线程来实现网络的请求
    class RunUi extends AsyncTask<List<NewsBean>, Void, List<NewsBean>> {

        @Override
        protected List<NewsBean> doInBackground(List<NewsBean>... params) {
            return NewsFragment.this.newsList;
        }

        @Override
        protected void onPostExecute(List<NewsBean> list) {
            adapter.addAll(list);
        }
    }
}
