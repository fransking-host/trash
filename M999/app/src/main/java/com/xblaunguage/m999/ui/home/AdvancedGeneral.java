package com.xblaunguage.m999.ui.home;




import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.util.regex.*;



public class AdvancedGeneral {

    /**
    * ��Ҫ��ʾ���������蹤����
    * FileUtil,Base64Util,HttpUtil,GsonUtils���
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * ����
    */
	public static String[] scores;
	public static String[] keywords;
	public String path;
	public AdvancedGeneral(String path) {
		this.path=path;
	}
    public  String advancedGeneral() {
        // ����url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v2/advanced_general";
        try {
            // �����ļ�·��
            String filePath = path;
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;
//            https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=HoUN9yRTfkMtP9HoDYAEVZne&client_secret=2K5If0BqShx0untS9wTfGwHExc8stzAW&
            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
            String accessToken = "24.ce5cae6c0ed62b7ddd1d5423ce7474d5.2592000.1593146785.282335-20086475";

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    static int getSize(String result) {
//    	System.out.println(result);
    	String regex="(num.*?)(\\d)";
    	Pattern p=Pattern.compile(regex);
    	Matcher m=p.matcher(result);
    	return m.find()?Integer.parseInt(m.group(2)):0;
    	
    }
    static String[] getScores(int size,String result) {
    	String[] str=new String[size];
    	String regex="(score.*?)([0-9].*?[0-9]+)";
    	Pattern p=Pattern.compile(regex);
    	Matcher m=p.matcher(result);
    	for (int i=0;i<size;++i) {
    		if (m.find()) str[i]=m.group(2);
    	}
    	return str;
    }
    static String[] getKeyword(int size,String result) {
    	String[] str=new String[size];
    	String regex="(keyword.*?)([\\u4e00-\\u9fa5]+)";
    	Pattern p=Pattern.compile(regex);
    	Matcher m=p.matcher(result);
    	for (int i=0;i<size;++i) {
    		if (m.find()) str[i]=m.group(2);
    	}
    		return str;
   
    }
    public static void main(String[] args) throws IOException {
    	long start=System.currentTimeMillis();
    	HTTPSTrustManager.allowAllSSL();
        String result=new AdvancedGeneral("C:\\Users\\aaa\\Desktop\\521.jpg").advancedGeneral();
        long end=System.currentTimeMillis();
        int size=getSize(result);
        System.out.println(size);
        scores=getScores(size,result);
        keywords=getKeyword(size,result);
        for (int i=0;i<size;++i) {
        	System.out.print(Float.parseFloat(scores[i])*100+"�Ŀ�������");
        	System.out.println(keywords[i]);
        }
        System.out.println(end-start);
    }
}