package com.medicalproject.fragment;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.WalkRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.example.lenovo.myapplication.R;

import java.util.List;
import java.util.ArrayList;

/**
 * 望杏林模块医院导航Fragment
 * @author 汪文博
 */
public class NavigationFragment extends Fragment implements RouteSearch.OnRouteSearchListener{

    private MapView mMapView = null;
    private AMap aMap;
    private LocationManager locationManager;
    private RouteSearch routeSearch;
    private View view;

    public NavigationFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_navigation, container, false);

        //获取地图控件引用
        mMapView = (MapView) view.findViewById(R.id.map_navi);
        mMapView.onCreate(savedInstanceState);
        //显示地图
        aMap = mMapView.getMap();
        //此处经纬度为医院经纬度，格式为(纬度，经度)
        LatLng latLng = new LatLng(30.60, 114.30);
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));

        //GPS定位
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        startRequestLocation();

        return view;
    }

    /**
     * GPS定位
     */
    private void startRequestLocation(){
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(!gps){
            Toast.makeText(getActivity(), "请先设置界面开启GPS定位服务", Toast.LENGTH_LONG).show();
            return ;
        }
        //通过GPS获取位置
        Location location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
        if(location != null){
            showNavigation(location);
        }

        //监听位置变化
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 10, locationListener);
    }

    //位置监听器
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            showNavigation(location);
        }

        //当位置提供者的状态发生改变
        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        //位置提供者可用时自动调用
        @Override
        public void onProviderEnabled(String s) {}

        //位置不可用时自动调用
        @Override
        public void onProviderDisabled(String s) {}
    };

    /**
     * 显示导航信息
     * @param location GPS经纬度
     */
    private void showNavigation(Location location){
        if(location != null){
            routeSearch = new RouteSearch(view.getContext());
            routeSearch.setRouteSearchListener(this);
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                    new LatLonPoint(location.getLatitude(), location.getLongitude()),
                    new LatLonPoint(30.60, 114.30)
            );
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, 0, null, null, "");
            routeSearch.calculateDriveRouteAsyn(query);
        }
    }

    //不同路线查询的回调方法
    @Override
    public void onDriveRouteSearched(DriveRouteResult drr, int rCode){
        if(rCode == 1000){
            List<DrivePath> pathList = drr.getPaths();
            List<LatLng> driverPath = new ArrayList<>();

            for (DrivePath dp: pathList){
                List<DriveStep> stepList = dp.getSteps();
                for(DriveStep ds: stepList){
                    List<LatLonPoint> points = ds.getPolyline();
                    for(LatLonPoint llp: points){
                        driverPath.add(new LatLng(llp.getLatitude(), llp.getLongitude()));
                    }
                }
            }

            aMap.clear();
            aMap.addPolyline(new PolylineOptions()
                        .addAll(driverPath)
                        .width(40)
                        //是否开启纹理贴图
                        .setUseTexture(false)
                        //绘制成大地线
                        .geodesic(true)
                        //设置画线的颜色
                        .color(Color.argb(200, 0, 0, 0)));
        }
    }

    @Override
    public void onBusRouteSearched(BusRouteResult brr, int i){}

    @Override
    public void onWalkRouteSearched(WalkRouteResult wrr, int i){}

    @Override
    public void onRideRouteSearched(RideRouteResult rrr, int i){}

}
