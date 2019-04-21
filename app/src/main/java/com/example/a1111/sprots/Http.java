package com.example.a1111.sprots;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;

public class Http<T> extends AsyncTask<String,Void,T> {

    private OnResponseListener<T> listener;
    private JSONObject result = new JSONObject();
    public void setListener(OnResponseListener<T> listener) {
        this.listener = listener;
    }

    @Override
    protected T doInBackground(String... params) {

        if(params[0].equals("GetMatch")) {
            return (T) HttpUtil.GetMatch(params[1]);
        }
        if(params[0].equals("GetPlayerInfo"))
            return (T)HttpUtil.GetPlayerInfo(params[1]);
        if(params[0].equals("GetTeamInfo"))
            return (T)HttpUtil.GetTeamInfo();
        if(params[0].equals("Register"))
        {
            try {
                // System.out.println(HttpUtil.Register(params[1],params[2],params[3],params[4]));
                result.put("result","" + HttpUtil.Register(params[1],params[2],params[3],params[4]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("Login"))
        {
            try {
                result.put("result",""+HttpUtil.Login(params[1],params[2]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetEmailCode")) {
            try {
                result.put("result", "" + HttpUtil.GetEmailCode(params[1]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("RequestGet")){
            try {
                result.put("result", "" + HttpUtil.RequestGet(params[1]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("RequestPost")){
            try {
                result.put("result",""+HttpUtil.RequestPost(params[1],params[2]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("RequestDelete")){
            try {
                result.put("result",""+HttpUtil.RequestDelete(params[1],params[2]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetTeamSummary"))
            return (T)HttpUtil.GetTeamSummary(params[1]);
        if(params[0].equals("GetPlayerSummary"))
            return (T)HttpUtil.GetPlayerSummary(params[1]);
        if(params[0].equals("GetPlayerCareer"))
            return (T)HttpUtil.GetPlayerCareer(params[1]);
        if(params[0].equals("GetSchedule"))
            return (T)HttpUtil.GetSchedule(params[1]) ;
        return null;

    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        if (listener!=null){
            try {
                listener.onResponse(t);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    //接口 类似一个监听事件
    public interface OnResponseListener<T>{
        void onResponse(T t) throws JSONException, IOException;
    }
}