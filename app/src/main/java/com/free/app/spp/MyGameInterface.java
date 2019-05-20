package com.free.app.spp;

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

public class MyGameInterface {
    public static String BaseUrl = "http://114.116.156.240/api/";
    static JSONArray GetMySchedule(String username){
        try{
            URL url = new URL(BaseUrl+"MySchedule?username="+
                    URLEncoder.encode(username,"UTF-8"));
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Cookie","user="+token);
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
    static int POSTMySchedule(String username,String time,String name,String introduction,String manager)
    {
        try {

            URL url = new URL(BaseUrl+"MySchedule");
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
            //conn.setRequestProperty("Cookie","user="+token);
            //传递参数
            String data = "username="+URLEncoder.encode(username,"UTF-8")+"&time="+URLEncoder.encode(time,"UTF-8")+"&name="+URLEncoder.encode(name,"UTF-8")+"&introduction="+URLEncoder.encode(introduction,"UTF-8")+"&manager="+URLEncoder.encode(manager,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();
            //System.out.println("123456");
            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);
            int code=conn.getResponseCode();
            System.out.println(code);
            //获取结果
            if(code==201) {
                System.out.println("goodjob");
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
    static JSONArray PUTMyMatch(String matchid,String home1,String home2,String home3,String home4,String home5,String home6,String home7,String home8,
                                String away1,String away2,String away3,String away4,String away5,String away6,String away7,String away8,String OT,String home_total,String away_total){
        try{
            URL url = new URL(BaseUrl+"MyMatch?matchid="+ URLEncoder.encode(matchid,"UTF-8")
                    +"&home1="+URLEncoder.encode(home1,"UTF-8")
                    +"&home2="+URLEncoder.encode(home2,"UTF-8")
                    +"&home3="+URLEncoder.encode(home3,"UTF-8")
                    +"&home4="+URLEncoder.encode(home4,"UTF-8")
                    +"&home5="+URLEncoder.encode(home5,"UTF-8")
                    +"&home6="+URLEncoder.encode(home6,"UTF-8")
                    +"&home7="+URLEncoder.encode(home7,"UTF-8")
                    +"&home8="+URLEncoder.encode(home8,"UTF-8")
                    +"&away1="+URLEncoder.encode(away1,"UTF-8")
                    +"&away2="+URLEncoder.encode(away2,"UTF-8")
                    +"&away3="+URLEncoder.encode(away3,"UTF-8")
                    +"&away4="+URLEncoder.encode(away4,"UTF-8")
                    +"&away5="+URLEncoder.encode(away5,"UTF-8")
                    +"&away6="+URLEncoder.encode(away6,"UTF-8")
                    +"&away7="+URLEncoder.encode(away7,"UTF-8")
                    +"&away8="+URLEncoder.encode(away8,"UTF-8")
                    +"&OT="+URLEncoder.encode(OT,"UTF-8")
                    +"&home_total="+URLEncoder.encode(home_total,"UTF-8")
                    +"&away_total="+URLEncoder.encode(away_total,"UTF-8"));
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
    static int POSTMyMatch(String gameid,String time,String date,String location,String home,String away)
    {
        try {

            URL url = new URL(BaseUrl+"MyMatch");
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
            String data = "gameid="+URLEncoder.encode(gameid,"UTF-8")+"&time="+URLEncoder.encode(time,"UTF-8")+"&date="+URLEncoder.encode(date,"UTF-8")+"&location="+URLEncoder.encode(location,"UTF-8")+"&home="+URLEncoder.encode(home,"UTF-8")+"&away="+URLEncoder.encode(away,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);
            int code=conn.getResponseCode();
            //获取结果
            if(code==201){
                System.out.println("goodjob");
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
    static JSONArray GetMyMatch(String gameid ,String matchid){
        try{
            URL url = new URL(BaseUrl+"MyMatch?gameid="+
                    URLEncoder.encode(gameid,"UTF-8")+"&matchid="+URLEncoder.encode(matchid,"UTF-8"));
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
    static JSONArray GetPlayer(String matchid){
        try{
            URL url = new URL(BaseUrl+"PlayerInfo?matchid="+
                    URLEncoder.encode(matchid,"UTF-8"));
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
    static int POSTPlayer(String matchid,String teamname,String playername,String position)
    {
        try {

            URL url = new URL(BaseUrl+"PlayerInfo");
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
            String data = "matchid="+URLEncoder.encode(matchid,"UTF-8")+"&teamname="+URLEncoder.encode(teamname,"UTF-8")+"&playername="+URLEncoder.encode(playername,"UTF-8")+"&position="+URLEncoder.encode(position,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);
            int code=conn.getResponseCode();
            //获取结果
            if(code==201) {
                System.out.println("goodjob");
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
    static JSONArray GetAllSchedule(){
        try{
            URL url = new URL(BaseUrl+"AllShedule");
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
    static int POSTAllSchedule(String scheduleid,String username)
    {
        try {

            URL url = new URL(BaseUrl+"AllShedule");
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
            String data = "scheduleid="+URLEncoder.encode(scheduleid,"UTF-8")+"&username="+URLEncoder.encode(username,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);
            int code=conn.getResponseCode();
            System.out.println(code);
            //获取结果
            if(code==201) {
                System.out.println("goodjob");
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
    static JSONArray GetSubforgame(String username){
        try{
            URL url = new URL(BaseUrl+"SubForGame?username="+
                    URLEncoder.encode(username,"UTF-8"));
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
    static int POSTSubforgame(String scheduleid,String username)
    {
        try {

            URL url = new URL(BaseUrl+"SubForGame");
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
            //conn.setRequestProperty("Cookie","user="+token);
            //传递参数
            String data = "scheduleid="+URLEncoder.encode(scheduleid,"UTF-8")+"&username="+URLEncoder.encode(username,"UTF-8");//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);
            int code=conn.getResponseCode();
            System.out.println(code);
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
    public static int PlayerScore(JSONArray playerlist)
    {
        try {
            //球员信息
//            JSONObject player=new JSONObject();
//
//            player.put("id",1);
//            player.put("得分","2");
//            player.put("篮板","1");
//            player.put("助攻","1");
//            player.put("三分","1");
//            player.put("罚球","1");
//            player.put("抢断","1");
//            player.put("助攻","1");
//            player.put("失误","1");
//            player.put("号码","1");
//
//            JSONArray test =new JSONArray();
//
//            test.put(player);

            URL url = new URL(BaseUrl+"PlayerInfo");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            //设置响应头参数
            conn.setReadTimeout(5000);
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");

            //传递参数
            String data =playerlist.toString();//拼装参数

            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);

            int code=conn.getResponseCode();
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
    public static int MatchScore(JSONObject matchscore)
    {
        try {
            URL url = new URL(BaseUrl+"MyMatch");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            //设置响应头参数
            conn.setReadTimeout(5000);
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");

            //传递参数
            String data =matchscore.toString();//拼装参数

            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            System.out.println(data);

            int code=conn.getResponseCode();
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
    public static int DeleteSchedule(String scheduleid)
    {
        try {
            URL url = new URL(BaseUrl+"MySchedule");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            //设置响应头参数
            conn.setReadTimeout(5000);
            conn.setRequestMethod("DELETE");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");

            //传递参数
            String data =new JSONObject().put("scheduleid",scheduleid).toString();//拼装参数

            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();


            int code=conn.getResponseCode();
            //获取结果
            if(code==204)
                return 1;
            else
                return 0;
        }catch(MalformedURLException e){

            e.printStackTrace();
        }catch(IOException e){

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int DeleteMatch(String matchid)
    {
        try {
            URL url = new URL(BaseUrl+"MyMatch");
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            //设置响应头参数
            conn.setReadTimeout(5000);
            conn.setRequestMethod("DELETE");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            //传递参数
            String data =new JSONObject().put("matchid",matchid).toString();//拼装参数
            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();
            int code=conn.getResponseCode();
            //获取结果
            if(code==204)
                return 1;
            else
                return 0;
        }catch(MalformedURLException e){

            e.printStackTrace();
        }catch(IOException e){

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int Validate(String para)
    {
        try {
            JSONObject dataob = new JSONObject(para);

            URL url = new URL(BaseUrl+"Validate");
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
            String data = dataob.toString();//拼装参数

            conn.setRequestProperty("Content-Length", String.valueOf(data.getBytes().length));
            OutputStream outputStream = conn.getOutputStream();

            outputStream.write(data.getBytes());//上传参数
            outputStream.flush();
            outputStream.close();


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
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
