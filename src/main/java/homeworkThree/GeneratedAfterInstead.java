package homeworkThree;

import homeworkOne.SaveProcessResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class GeneratedAfterInstead {

    public static void main(String[] args) {
        //读url
        //System.out.println(readFileByUrl("http://qfc.qunar.com/homework/sdxl_prop.txt"));
        //System.out.println(readFileByUrl("http://qfc.qunar.com/homework/sdxl_template.txt"));
        String s = readFileByUrl("http://qfc.qunar.com/homework/sdxl_prop.txt");
        StringBuilder s1 = new StringBuilder(readFileByUrl("http://qfc.qunar.com/homework/sdxl_template.txt"));
        String[] strings = s.split("\r\n");
        /*
        System.out.println(Arrays.toString(strings));
        System.out.println(strings.length);
        System.out.println(Arrays.toString(strings[1].trim().split("\t")));*/


        //存url内容
        Map<String, String> map = new HashMap<>();
        String[] natureStrings = new String[strings.length];
        String[] charOrderStrings = new String[strings.length];
        String[] charOrderDescStrings = new String[strings.length];

        for (int i = 0; i < strings.length; i++) {
            //存键值
            map.put(strings[i].trim().split("\t")[0], strings[i].trim().split("\t")[1]);
            //存自然顺续
            natureStrings[i] = strings[i].trim().split("\t")[1];
            //字符顺序
            charOrderStrings[i] = strings[i].trim().split("\t")[1];
            //字符逆序
            charOrderDescStrings[i] = strings[i].trim().split("\t")[1];
        }
        System.out.println("map: " + map.size());
        System.out.println("natureStrings: " + natureStrings.length);
        Arrays.sort(charOrderStrings, String::compareTo);
        Arrays.sort(charOrderDescStrings, Comparator.reverseOrder());
        System.out.println(Arrays.toString(charOrderStrings));

        //替换，四种排序
        int a;
        while ((a = s1.indexOf("$natureOrder(")) != -1) {
            String after = s1.substring(a + 13, s1.length() - 1);
            int i = Integer.parseInt(after.substring(0, after.indexOf(")")));
            s1 = new StringBuilder(s1.substring(0, a) +
                    natureStrings[i] + after.substring(after.indexOf(")") + 1, after.length() - 1));
        }

        while ((a = s1.indexOf("$charOrder(")) != -1) {
            String after = s1.substring(a + 11, s1.length() - 1);
            int i = Integer.parseInt(after.substring(0, after.indexOf(")")));
            s1 = new StringBuilder(s1.substring(0, a) +
                    charOrderStrings[i] + after.substring(after.indexOf(")") + 1, after.length() - 1));
        }

        while ((a = s1.indexOf("$charOrderDESC(")) != -1) {
            String after = s1.substring(a + 15, s1.length() - 1);
            int i = Integer.parseInt(after.substring(0, after.indexOf(")")));
            s1 = new StringBuilder(s1.substring(0, a) +
                    charOrderDescStrings[i] + after.substring(after.indexOf(")") + 1, after.length() - 1));
        }

        while ((a = s1.indexOf("$indexOrder(")) != -1) {
            String after = s1.substring(a + 12, s1.length() - 1);
            int i = Integer.parseInt(after.substring(0, after.indexOf(")")));
            s1 = new StringBuilder(s1.substring(0, a) +
                    charOrderDescStrings[i] + after.substring(after.indexOf(")") + 1, after.length() - 1));
        }

        //替换后的存到classpath下sdxl.txt
        ReadClasspathFile readClasspathFile = new ReadClasspathFile();
        File fileName = readClasspathFile.getFile("sdxl.txt");
        try {
            SaveProcessResult.saveByPath(fileName, s1.toString());
        } catch (Exception e) {
            System.out.println("存储文本内容失败 Exception：" + e);
        }
    }

    public static String readFileByUrl(String urlStr) {
        String res = null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            res = readInputStream(inputStream);
        } catch (Exception e) {
            System.out.println("通过url地址获取文本内容失败 Exception：" + e);
        }
        return res;
    }

    /**
     * 从输入流中获取字符串
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        //System.out.println(new String(bos.toByteArray(),"utf-8"));
        return new String(bos.toByteArray(), "utf-8");
    }
}
