package com.example.user.util;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.UnknownHostException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HttpClientUtil {

    private final static String userAgent = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/49.0.2623.110 Safari/537.36";
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static CookieStore cookieStore = new BasicCookieStore();
    private static CloseableHttpClient httpClient;
    private static HttpHost proxy;
    private static RequestConfig requestConfig;


    /**
     * 根据uri请求获取html 代码
     *
     * @param uri
     * @return
     * @throws Exception
     */
    public static String doGet(String uri) throws Exception {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet get = new HttpGet(uri);
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应的结果
        //int statusCode = response.getStatusLine().getStatusCode();
        //System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");

        //关闭httpclient
        response.close();
        httpClient.close();

        return string;
    }

    /**
     * 根据uri获取get请求内容
     *
     * @param uri
     * @param parmasName
     * @param paramsValue
     * @return
     * @throws Exception
     */
    public static String doGet(String uri, String parmasName, String paramsValue) throws Exception {
        //创建一个httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        URIBuilder uriBuilder = new URIBuilder(uri);
        uriBuilder.addParameter(parmasName, paramsValue);
        HttpGet get = new HttpGet(uriBuilder.build());

        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);

        //取响应的结果
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");

        //关闭httpclient
        response.close();
        httpClient.close();

        return string;
    }


    public static String getWebPage(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return getWebPage(request, "utf-8");
    }

    public static String getWebPage(HttpRequestBase request) throws IOException {
        return getWebPage(request, "utf-8");
    }

    public static String postRequest(String postUrl, Map<String, String> params) throws IOException {
        HttpPost post = new HttpPost(postUrl);
        setHttpPostParams(post, params);
        return getWebPage(post, "utf-8");
    }

    /**
     * @param encoding 字符编码
     * @return 网页内容
     */
    public static String getWebPage(HttpRequestBase request
            , String encoding) throws IOException {
        CloseableHttpResponse response = null;
        response = getResponse(request);
        logger.info("status---" + response.getStatusLine().getStatusCode());
        String content = EntityUtils.toString(response.getEntity(), encoding);
        request.releaseConnection();
        return content;
    }

    public static CloseableHttpResponse getResponse(HttpRequestBase request) throws IOException {
        if (request.getConfig() == null) {
            request.setConfig(requestConfig);
        }
        request.setHeader("User-Agent", Constants.userAgentArray[new Random().nextInt(Constants.userAgentArray.length)]);
        HttpClientContext httpClientContext = HttpClientContext.create();
        httpClientContext.setCookieStore(cookieStore);
        CloseableHttpResponse response = httpClient.execute(request, httpClientContext);
//		int statusCode = response.getStatusLine().getStatusCode();
//		if(statusCode != 200){
//			throw new IOException("status code is:" + statusCode);
//		}
        return response;
    }

    public static CloseableHttpResponse getResponse(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        return getResponse(request);
    }

    /**
     * 序列化对象
     *
     * @param object
     * @throws Exception
     */
    public static void serializeObject(Object object, String filePath) {
        OutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath, false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            logger.info("序列化成功");
            oos.flush();
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化对象
     *
     * @param path
     * @throws Exception
     */
    public static Object deserializeObject(String path) throws Exception {
//		InputStream fis = HttpClientUtil.class.getResourceAsStream(name);
        File file = new File(path);
        InputStream fis = new FileInputStream(file);
        ObjectInputStream ois = null;
        Object object = null;
        ois = new ObjectInputStream(fis);
        object = ois.readObject();
        fis.close();
        ois.close();
        return object;
    }

    /**
     * 下载图片
     *
     * @param fileURL       文件地址
     * @param path          保存路径
     * @param saveFileName  文件名，包括后缀名
     * @param isReplaceFile 若存在文件时，是否还需要下载文件
     */
    public static void downloadFile(String fileURL
            , String path
            , String saveFileName
            , Boolean isReplaceFile) {
        try {
            CloseableHttpResponse response = getResponse(fileURL);
            logger.info("status:" + response.getStatusLine().getStatusCode());
            File file = new File(path);
            //如果文件夹不存在则创建
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
            } else {
                logger.info("//目录存在");
            }
            file = new File(path + saveFileName);
            if (!file.exists() || isReplaceFile) {
                //如果文件不存在，则下载
                try {
                    OutputStream os = new FileOutputStream(file);
                    InputStream is = response.getEntity().getContent();
                    byte[] buff = new byte[(int) response.getEntity().getContentLength()];
                    while (true) {
                        int readed = is.read(buff);
                        if (readed == -1) {
                            break;
                        }
                        byte[] temp = new byte[readed];
                        System.arraycopy(buff, 0, temp, 0, readed);
                        os.write(temp);
                        logger.info("文件下载中....");
                    }
                    is.close();
                    os.close();
                    logger.info(fileURL + "--文件成功下载至" + path + saveFileName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                logger.info(path);
                logger.info("该文件存在！");
            }
            response.close();
        } catch (IllegalArgumentException e) {
            logger.info("连接超时...");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public static CookieStore getCookieStore() {
        return cookieStore;
    }

    public static void setCookieStore(CookieStore cookieStore) {
        HttpClientUtil.cookieStore = cookieStore;
    }

    /**
     * 有bug 慎用
     * unicode转化String
     *
     * @return
     */
    public static String decodeUnicode(String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            start = dataStr.indexOf("\\u", start - (6 - 1));
            if (start == -1) {
                break;
            }
            start = start + 2;
            end = start + 4;
            String tempStr = dataStr.substring(start, end);
            String charStr = "";
            charStr = dataStr.substring(start, end);
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            dataStr = dataStr.replace("\\u" + tempStr, letter + "");
            start = end;
        }
        logger.debug(dataStr);
        return dataStr;
    }

    /**
     * 设置request请求参数
     *
     * @param request
     * @param params
     */
    public static void setHttpPostParams(HttpPost request, Map<String, String> params) {
        List<NameValuePair> formParams = new ArrayList<NameValuePair>();
        for (String key : params.keySet()) {
            formParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        UrlEncodedFormEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(formParams, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        request.setEntity(entity);
    }


    public static void main(String[] args) {
        String result = "'";
        try {
            doGet("http://www.yaopinnet.com/tools/wenhao.asp?keyword=Z23021461");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.err.println(result);
    }

}
