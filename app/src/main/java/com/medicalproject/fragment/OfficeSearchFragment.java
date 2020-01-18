package com.medicalproject.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.myapplication.R;
import com.medicalproject.adapter.OfficeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 门诊查询Fragment
 * @author 汪文博
 */
public class OfficeSearchFragment extends Fragment {

    private ListView listView;
    private OfficeAdapter adapter;
    private List<String> items;

    public OfficeSearchFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_office_search, container, false);

        initData();

        listView = (ListView) view.findViewById(R.id.lv_hosp);
        listView.setAdapter(adapter = new OfficeAdapter(view.getContext(), items));

        //设置监听事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                Toast.makeText(getActivity(), items.get(position)+"", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    //初始化数据
    private void initData(){
        items = new ArrayList<>();
        items.add("心脏与血管外科");items.add("肿瘤科");items.add("男科");
        items.add("整形美容科");items.add("神经外科");items.add("内分泌与代谢科");
        items.add("皮肤性病科");items.add("呼吸内科");items.add("内科");
        items.add("眼科");items.add("普外科");items.add("烧伤科");
        items.add("口腔颌面科");items.add("肾内科");items.add("甲状腺乳腺外科");
        items.add("精神科");items.add("风湿免疫科");items.add("泌尿外科");
        items.add("耳鼻喉科");items.add("神经内科");items.add("消化内科");
        items.add("妇科");items.add("心理科");items.add("感染科");
        items.add("骨科");items.add("血液病科");items.add("心血管内科");
        items.add("胸外科");items.add("儿科");items.add("产科");
        items.add("新生儿科");items.add("急诊科");
    }

}
