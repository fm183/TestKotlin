package com.example.testkotlin.core;

import com.example.testkotlin.bean.ResponseBean;
import com.example.testkotlin.inf.SelectedListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterManager {

    private static AdapterManager instance;
    private List<SelectedListener> selectedListenerList;

    private AdapterManager(){
        selectedListenerList = new ArrayList<>();
    }

    public synchronized static AdapterManager getInstance(){
        if (instance == null) {
            synchronized (AdapterManager.class){
                instance = new AdapterManager();
            }
        }
        return instance;
    }

    public void addListener(SelectedListener selectedListener){
        if (selectedListener == null) {
            return;
        }
        if (selectedListenerList.contains(selectedListener)) {
            return;
        }
        selectedListenerList.add(selectedListener);
    }

    public void removeListener(SelectedListener selectedListener){
        if (selectedListener == null) {
            return;
        }
        if (!selectedListenerList.contains(selectedListener)) {
            return;
        }
        selectedListenerList.remove(selectedListener);
    }

    public void onChangeData(ResponseBean.DataBean.ChildrenBean childrenBean, ResponseBean.DataBean dataBean, int level){
        if (selectedListenerList.isEmpty()) {
            return;
        }
        for (SelectedListener selectedListener : selectedListenerList){
            selectedListener.onSelectedChange(childrenBean,dataBean,level);
        }
    }

    public void clean(){
        selectedListenerList.clear();
    }

}
