package com.free.app.spp;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

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
            return (T) HttpUtil.RequestGet(params[1]);
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
        if(params[0].equals("Changepassword"))
        {
            try {
                result.put("result",""+HttpUtil.Changepassword(params[1],params[2],params[3]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetMySchedule"))
        {
            return (T)MyGameInterface.GetMySchedule(params[1]);
        }
        if(params[0].equals("POSTMySchedule"))
        {
            try {
                result.put("result",""+MyGameInterface.POSTMySchedule(params[1],params[2],params[3],params[4],params[5]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetMyMatch"))
        {
            return (T) MyGameInterface.GetMyMatch(params[1]);
        }
        if(params[0].equals("POSTMyMatch"))
        {
            try {
                result.put("result",""+MyGameInterface.POSTMyMatch(params[1],params[2],params[3],params[4],params[5],params[6]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetPlayer"))
        {
            return (T) MyGameInterface.GetPlayer(params[1]);
        }
        if(params[0].equals("POSTPlayer"))
        {
            try {
                result.put("result",""+MyGameInterface.POSTPlayer(params[1],params[2],params[3],params[4]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetAllSchedule"))
        {
            return (T) MyGameInterface.GetAllSchedule();
        }
        if(params[0].equals("POSTAllSchedule"))
        {
            try {
                result.put("result",""+MyGameInterface.POSTAllSchedule(params[1],params[2]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }
        if(params[0].equals("GetSubforgame"))
        {
            return (T) MyGameInterface.GetSubforgame(params[1]);
        }
        if(params[0].equals("POSTSubforgame"))
        {
            try {
                result.put("result",""+MyGameInterface.POSTSubforgame(params[1],params[2]));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return (T) (new JSONArray().put(result));
        }

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