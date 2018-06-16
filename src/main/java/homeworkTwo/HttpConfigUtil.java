package homeworkTwo;

import com.google.common.collect.Lists;
import homeworkOne.SaveProcessResult;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class HttpConfigUtil {
    public static void main(String[] args) throws IOException {

        String s1 = "D:/Users/Administrator/IdeaProjects/test1/out/production/test1/输入列表.txt";
        String s2 = "D:/Users/Administrator/IdeaProjects/test1/out/production/test1/黑名单列表.txt";
        List<String> enterList = readFileByLines(s1);
        List<String> blackList = readFileByLines(s2);
        enterList.removeAll(blackList);
        String url = "https://api.douban.com/v2/book/";
        StringBuilder stringBuilder = new StringBuilder();
        for(String s :enterList){
            try {

                String json = HttpConfigUtil.getHttpResponse(url+s);
                stringBuilder.append(json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        SaveProcessResult.save(stringBuilder.toString());

    }

    public static String getHttpResponse(String allConfigUrl) {
        BufferedReader in = null;
        StringBuffer result = null;
        try {

            URI uri = new URI(allConfigUrl);
            URL url = uri.toURL();
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Charset", "utf-8");

            connection.connect();

            result = new StringBuffer();
            //读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return null;

    }

    public static List<String> readFileByLines(String fileName) {
        List<String> stringList = Lists.newArrayList();
        File file = new File(fileName);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tempString;
            while((tempString = reader.readLine())!=null){
                stringList.add(tempString.trim());
            }
            reader.close();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (IOException e1){

                }
            }
        }
        return stringList;
    }

}
