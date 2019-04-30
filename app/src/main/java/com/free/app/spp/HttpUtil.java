package com.free.app.spp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

class  HttpUtil {
    private static String BaseUrl = "http://114.116.156.240/api/";

    //获得比赛数据
    //Path.         : api/GetMatch
    //Method.    : GET
    //Params.    : 无
    static JSONArray GetMatch(String id){
        try{
            URL url = new URL(BaseUrl + "GetMatch?match_id="+id);
            HttpURLConnection conn =
                    (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            JSONArray jsonArray = new JSONArray(buffer.toString());
            System.out.println(jsonArray.toString());
            return jsonArray;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //球队信息
    //
    //Path.         : api/GetTeanmInfo
    //Method.    : GET
    //Params.    : 无
    //Return       : status=200  获取成功
    //         返回球队数据
    //      status=400 获取失败
    static JSONArray GetTeamInfo(){
        try{
            URL url = new URL(BaseUrl + "GetTeamInfo");
            HttpURLConnection conn =
                    (HttpURLConnection)url.openConnection();
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            //System.out.println(jsonArray.toString());
            //parseJsonWithJsonObject(buffer.toString());
            return new JSONArray(buffer.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //某场比赛球队总结
    //Path.         : api/GetTeamSummary
    //Method.    : GET
    //Params.    : match_id
    static JSONArray GetTeamSummary(String match_id){
        try{
            URL url = new URL(BaseUrl+"GetTeamSummary?match_id="+
                    match_id);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            //parseJsonWithJsonObject(buffer.toString());
            System.out.println(buffer.toString());
            return new JSONArray(buffer.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //某场比赛球员总结
    //Path.         : api/GetPlayerSummary
    //Method.    : GET
    //Params.    : match_id
    static JSONArray GetPlayerSummary(String match_id){
        try{
            //System.out.println("test");
            URL url = new URL(BaseUrl+"GetPlayerSummary?match_id="+
                    match_id);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传参

            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            // parseJsonWithJsonObject(buffer.toString());
            return new JSONArray(buffer.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //球员信息
    //Path.         : api/GetPlayerInfo
    //Method.    : GET
    //Params.    : teamname（球队的英文名，获取哪个球队的所有球员）
    static JSONArray GetPlayerInfo(String teamname){
        try{
            //System.out.println("test");
            URL url = new URL(BaseUrl+"GetPlayerInfo?teamname="+
                    teamname);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传参

            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            //System.out.println(buffer.toString());
            //parseJsonWithJsonObject(buffer.toString());
            return new JSONArray(buffer.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //球员职业生涯信息
    //
    //Path.         : api/GetPlayerCareer
    //Method.    : GET
    //Params.    : player_index（球员的序号）
    //Return       :
    //         返回球员职业生涯数据
    static JSONArray GetPlayerCareer(String player_index){
        try{
            //System.out.println("test");
            URL url = new URL(BaseUrl+"GetPlayerCareer?player_index="+
                    player_index);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传参

            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            // System.out.println(buffer.toString());

            return new JSONArray(buffer.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //获取邮箱验证码
    //
    //Path.         : api/register
    //Method.    : GET
    //Params.    : email
    //Return       : status=200  发送成功
    static int GetEmailCode(String email) {
        try {

            URL url = new URL(BaseUrl+"register?email="+email);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传递参数
            System.out.println(BaseUrl+"register?email="+email);

            //设置响应头参数
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");


            //获取结果
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();

            int code=conn.getResponseCode();
            //获取结果
            if(code==200)
                return 1;
            else
                return 0;
        }catch(MalformedURLException e){

            e.printStackTrace();
        }catch(IOException e){

            e.printStackTrace();
        }
        return 0;
    }

    //登录
    //
    //Path.         : api/login
    //Method.    : GET
    //Params.    : nick_name , password
    //Return       : status=400. 登录失败
    //          status=200  登录成功
    static int Login(String nick_name,String password) {
        try {

            URL url = new URL(BaseUrl+"login?nick_name="+URLEncoder.encode(nick_name,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8"));
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传递参数

            //设置响应头参数
            conn.setReadTimeout(10000);
            conn.setRequestMethod("GET");


            //获取结果
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            int code = conn.getResponseCode();
            if(code == 200){
                return 1;
            }else {
                return 0;
            }
        }catch(MalformedURLException e){

            e.printStackTrace();
        }catch(IOException e){

            e.printStackTrace();
        }
        return 0;
    }

    //注册
    //
    //Path.         : api/register
    //Method.    : POST
    //Params.    : email , nick_name , password , veri_code
    //Return       : status=201  创建成功
    //          status=400. 验证码错误
    static int Register(String username,String password,String email,String veri_code)
    {
        try {

            URL url = new URL(BaseUrl+"register");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            //设置响应头参数
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");

            //传递参数
            String data = "nick_name="+URLEncoder.encode(username,"UTF-8")+"&password="+URLEncoder.encode(password,"UTF-8")+"&email="+URLEncoder.encode(email,"UTF-8")+"&veri_code="+URLEncoder.encode(veri_code,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);

            int code=conn.getResponseCode();
            System.out.println("register_code " + code);
            //获取结果
            if(code==201)
                return 1;
            else
                return 0;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    static int RequestPost(String username,String teamname)
    {
        try {

            URL url = new URL(BaseUrl+"Subscribe");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            //设置响应头参数
            conn.setReadTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");

            //传递参数
            String data = "username="+URLEncoder.encode(username,"UTF-8")+"&teamname="+URLEncoder.encode(teamname,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);

            int code=conn.getResponseCode();
            //获取结果
            if(code==201) {
                System.out.println("good job");
                return 1;
            }
            else
                return 0;
        }catch(MalformedURLException e){

            e.printStackTrace();
        }catch(IOException e){

            e.printStackTrace();
        }
        return 0;
    }

    static JSONArray RequestGet(String username){
        try{
            //System.out.println("test1");
            URL url = new URL(BaseUrl+"Subscribe?username="+
                    URLEncoder.encode(username,"UTF-8"));
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传参

            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            //System.out.println("test");
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            JSONArray jsa = new JSONArray(buffer.toString());
            System.out.println(jsa.length());
            System.out.println(jsa.toString());
            return jsa;
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    static int RequestDelete(String username,String teamname){
        try{
            //System.out.println("test1");
            URL url = new URL(BaseUrl+"Subscribe?username="+
                    URLEncoder.encode(username,"UTF-8")+"&teamname="+URLEncoder.encode(teamname,"UTF-8"));
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传参

            conn.setReadTimeout(5000);
            conn.setRequestMethod("DELETE");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str;
            System.out.println("test");
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            int code=conn.getResponseCode();
            //获取结果
            if(code==204) {
                System.out.println("goodjob");
                return 1;
            }
            else {
                System.out.println("false");
                return 0;
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }

    static Bitmap GetPlayerImage(String name){
        Bitmap bm=null;
        try{
            URL url = new URL(BaseUrl+"GetPlayerImage/"+name);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bm = BitmapFactory.decodeStream(inputStream);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bm;
    }

    static Bitmap GetTeamImage(String name){
        Bitmap bm=null;
        try{
            URL url = new URL(BaseUrl+"GetPlayerImage/"+URLEncoder.encode(name,"UTF-8"));
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            bm = BitmapFactory.decodeStream(inputStream);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
        return bm;
    }

    static JSONArray GetSchedule(String teamname){
        try{
            URL url = new URL(BaseUrl+"GetSchedule?teamname="+
                    URLEncoder.encode(teamname,"UTF-8"));
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            StringBuilder buffer = new StringBuilder();
            String str = null;
            while((str = reader.readLine())!=null){
                buffer.append(str);
            }
            //parseJsonWithJsonObject(buffer.toString());
            System.out.println(buffer.toString());
            return new JSONArray(buffer.toString());
        }catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }catch(JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    static int Changepassword(String username,String oldpassword,String newpassword) {
        try {

            URL url = new URL(BaseUrl+"Changepassword?username="+username+"&oldpassword="+oldpassword
                    +"&new="+newpassword);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //传递参数
            //System.out.println(BaseUrl+"register?email="+email);

            //设置响应头参数
            conn.setReadTimeout(10000);
            conn.setRequestMethod("PUT");
            //获取结果
            InputStream stream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(stream)
            );
            int code = conn.getResponseCode();
            if(code == 200){
                return 1;
            }else {
                return 0;
            }
        }catch(MalformedURLException e){

            e.printStackTrace();
        }catch(IOException e){

            e.printStackTrace();
        }
        return 0;
    }

}
