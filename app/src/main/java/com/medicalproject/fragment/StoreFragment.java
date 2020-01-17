package com.medicalproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.core.PoiItem;
import com.amap.api.maps.model.Marker;
import com.example.lenovo.myapplication.R;

import java.util.List;
import java.util.ArrayList;

/**
 * 望杏林模块周边商铺Fragment
 * @author 汪文博
 */
public class StoreFragment extends Fragment implements PoiSearch.OnPoiSearchListener{

    private MapView mMapView = null;
    private AMap aMap;
    private Button btn;
    private EditText et;
    private String keyWord;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private PoiSearch.SearchBound searchBound;
    private PoiResult poiResult;
    private LatLonPoint searchLatlonPoint;
    private List<PoiItem> mDatas = new ArrayList<PoiItem>();   //poi数据
    private View view;

    public StoreFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_store, container, false);

        //获取控件
        btn = (Button) view.findViewById(R.id.btn_store);
        et = (EditText) view.findViewById(R.id.et_store);

        //设置按钮事件监听器
        btn.setOnClickListener(tabClickListener);

        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        //显示地图
        aMap = mMapView.getMap();
        //此处经纬度为医院经纬度，格式为(纬度，经度)
        LatLng latLng = new LatLng(30.60, 114.30);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        return view;
    }

    private OnClickListener tabClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            keyWord = et.getText().toString();
            if("".equals(keyWord) || keyWord == null){
                Toast.makeText(getActivity(), "请输入搜索关键字", Toast.LENGTH_SHORT).show();
                return ;
            }else{
                doSearchQuery();
            }
        }
    };

    /**
     * 搜索操作
     */
    private void doSearchQuery(){
        query = new PoiSearch.Query(keyWord, "", "");
        query.setPageSize(10);
        query.setPageNum(0);
        searchLatlonPoint = new LatLonPoint(30.60, 114.30);
        if(searchLatlonPoint != null){
            poiSearch = new PoiSearch(view.getContext(), query);
            poiSearch.setOnPoiSearchListener(this);    //设置回调数据的监听器
            poiSearch.setBound(new PoiSearch.SearchBound(searchLatlonPoint, 2000, true));
            poiSearch.searchPOIAsyn();
        }
    }

    /**
     * POI信息查询回调方法
     * @param poiResult 查询结果
     * @param resultCode 状态码
     */
    @Override
    public void onPoiSearched(PoiResult poiResult, int resultCode){
        if(resultCode == 1000){
            if(poiResult != null && poiResult.getQuery() != null){
                if(poiResult.getQuery().equals(query)){
                    mDatas = poiResult.getPois();
                    if(mDatas != null && mDatas.size() > 0){
                        //检索结果设置在地图上
                        drawPoint();
                    }else{
                        Toast.makeText(getActivity(), "无搜索结果", Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(getActivity(), "无搜索结果", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i){}

    /**
     * 绘制检索结果点
     */
    private void drawPoint(){
        for(int i=0; i<mDatas.size(); i++){
            LatLonPoint latLngPoint = mDatas.get(i).getLatLonPoint();
            LatLng latLng = new LatLng(latLngPoint.getLatitude(), latLngPoint.getLongitude());
            final Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title(keyWord).snippet("医院周边酒店"));
        }
    }
}
