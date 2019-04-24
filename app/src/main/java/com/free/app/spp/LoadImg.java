package com.free.app.spp;

import android.os.AsyncTask;

import java.io.IOException;

public class LoadImg<Bitmap> extends AsyncTask<String,Integer, Bitmap> {
    /*private ImageView img;
    public LoadImg(ImageView im){
        this.img = im;
    }*/
    private OnResponseListener<Bitmap> listener;
    public void setListener(OnResponseListener<Bitmap> listener) {
        this.listener = listener;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }
    @Override
    protected Bitmap doInBackground(String... params){
        if(params[0].equals("GetPlayerImage")){
            return (Bitmap)HttpUtil.GetPlayerImage(params[1]);
        }
        if(params[0].equals("GetTeamImage")){
            return (Bitmap)HttpUtil.GetTeamImage(params[1]);
        }
        return null;
    }
    @Override
    protected void onPostExecute(Bitmap bitmap){
        super.onPostExecute(bitmap);
        if(listener != null){
            try {
                listener.onResponse(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnResponseListener<Bitmap>{
        void onResponse(Bitmap t) throws  IOException;
    }

}
