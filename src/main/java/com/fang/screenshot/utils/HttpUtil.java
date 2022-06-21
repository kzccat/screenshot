package com.fang.screenshot.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpUtil {

//    private static Logger logger = Logger.getLogger(HttpUtil.class);

    public final static String CT_X_WWW_FORM_URLENCODED = "application/x-www-form-urlencoded";
    public final static String CT_JSON = "application/json";
    public final static String CT_FORM_DATA = "multipart/form-data";
    public final static String CHARSET_NAME = "UTF-8";

    /**
     *
     * @param httpUrl
     * @param params
     * @param charsetName
     * @return Map<String,Object>
     */
    public static Map<String, Object> doGet(String httpUrl,
                                            Map<String, ? extends Object> params, String charsetName) {
        return doGet(httpUrl, params, charsetName, null);
    }

    /**
     *
     * @param httpUrl
     * @param params
     * @param charsetName
     * @param contentType
     * @return Map<String,Object>
     */
    public static Map<String, Object> doGet(String httpUrl,
                                            Map<String, ? extends Object> params, String charsetName,
                                            String contentType) {
        charsetName = charsetName == null ? CHARSET_NAME : charsetName;
        Map<String, Object> map = (Map<String, Object>) params;
        Map<String, Object> result = new HashMap<>();
        StringBuilder param = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        InputStream is = null;
        String data = null;
        if (map != null) {
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                try {
                    String v = URLEncoder.encode(value, charsetName);
                    param.append("&").append(key).append("=").append(v);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            if (param.length() > 0) {
                httpUrl += "?" + param.substring(1);
            }
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(300000);
            connection.setReadTimeout(600000);
            connection.connect();
            if (StringUtils.isNotBlank(contentType)) {
                connection.setRequestProperty("Content-Type", contentType);
            }
            int responseCode = connection.getResponseCode();
            StringBuilder sb = new StringBuilder();
            if (responseCode == 200) {
                is = connection.getInputStream();
                isr = new InputStreamReader(is, Charset.forName(charsetName));
                br = new BufferedReader(isr);
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    sb.append(readLine);
                    sb.append("\r\n");
                }
                data = sb.toString();
                result.put("data", data);
                result.put("state", responseCode);
                result.put("msg", connection.getResponseMessage());
            } else {
                result.put("state", responseCode);
                result.put("msg", connection.getResponseMessage());
            }
        } catch (IOException e) {
            result.put("state", 500);
            result.put("msg", e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    result.put("state", 500);
                    result.put("msg", e.getMessage());
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    result.put("state", 500);
                    result.put("msg", e.getMessage());
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    result.put("state", 500);
                    result.put("msg", e.getMessage());
                    e.printStackTrace();
                }
            }
            if(connection != null) {
                connection.disconnect();
            }
        }
        return result;
    }

    public static String doGetImg(String httpUrl) {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        InputStream is = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(60000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            StringBuilder sb = new StringBuilder();
            if (responseCode == 200) {
                is = connection.getInputStream();
                // 得到图片的二进制数据，以二进制封装得到数据，具有通用性
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                // 创建一个Buffer字符串
                byte[] buffer = new byte[1024];
                // 每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                // 使用一个输入流从buffer里把数据读取出来
                while ((len = is.read(buffer)) != -1) {
                    // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outStream.write(buffer, 0, len);
                }
                // 关闭输入流
                is.close();
                byte[] data = outStream.toByteArray();
                // 对字节数组Base64编码
                Base64 encoder = new Base64();
                String base64 = encoder.encodeAsString(data);
                return base64;// 返回Base64编码过的字节数组字符串
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return "";
    }

    public static Map<String, Object> doPost(String httpUrl, Map<String, Object> map,
                                             String charsetName, String contentType) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String data = null;
        StringBuilder param = new StringBuilder();
        if (map != null) {
            Set<Map.Entry<String, Object>> entrySet = map.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                try {
                    param.append(key).append("=")
                            .append(URLEncoder.encode(value, charsetName))
                            .append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
//        logger.info("savetodictaaaaaaaaaaaaaaaa: " + httpUrl + param.toString());
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", contentType);
            //connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            os = connection.getOutputStream();
            if (param != null) {
                os.write(param.substring(0, param.length() - 1).getBytes());
                os.flush();
            }
            // connection.connect();
            StringBuilder sb = new StringBuilder();
            String temp = null;
            if (200 == connection.getResponseCode()) {
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, charsetName));
                while ((temp = br.readLine()) != null) {
                    sb.append(temp);
                    sb.append("\r\n");
                }
                data = sb.toString();
                result.put("data", data);
            }
            result.put("state", connection.getResponseCode());
            result.put("msg", connection.getResponseMessage());
        } catch (MalformedURLException e) {
            result.put("state", 500);
            result.put("msg", e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            result.put("state", 500);
            result.put("msg", e.getMessage());
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    result.put("state", 500);
                    result.put("msg", e.getMessage());
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    result.put("state", 500);
                    result.put("msg", e.getMessage());
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    result.put("state", 500);
                    result.put("msg", e.getMessage());
                    e.printStackTrace();
                }
            }
            connection.disconnect();
        }
        return result;
    }

    /**
     *
     * @param url
     * @param json
     * @return String
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String httpRaw(String url,String json) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity postingString = null;
        postingString = new StringEntity(json, "UTF-8");
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(15000).setConnectionRequestTimeout(15000).setSocketTimeout(600000).build();
        post.setConfig(requestConfig);
        HttpResponse response;
        response = httpClient.execute(post);
        String content = EntityUtils.toString(response.getEntity());
        return content;
    }

    /**
     *
     * @param url
     * @param json
     * @return String
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static String httpRaw(String url,String json, String requestId) throws ClientProtocolException, IOException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        StringEntity postingString = null;
        postingString = new StringEntity(json, "UTF-8");
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        post.setHeader("DataType", "huxing " + requestId);
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(20000).setConnectionRequestTimeout(3000).setSocketTimeout(15000).build();
        post.setConfig(requestConfig);
        HttpResponse response;
        response = httpClient.execute(post);
        String content = EntityUtils.toString(response.getEntity());
        return content;
    }
}
