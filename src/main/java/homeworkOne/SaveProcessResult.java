package homeworkOne;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveProcessResult {
    public static void save(File file, String s) throws IOException {
        String path = "";
        if (file.getName().endsWith(".txt")) {
            path = file.getPath().substring(0, file.getPath().length() - 4) + ".rs";
        }
        File file1 = new File(path);
        if(file1.getParentFile() == null){
            System.out.println("输入绝对路径");
            return;
        }
        if (!file1.exists()) {
            file1.getParentFile().mkdirs();
        }

        file1.createNewFile();

        // write
        FileWriter fw = new FileWriter(file1, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(s);
        bw.flush();
        bw.close();
        fw.close();

    }
    public static void save(String s) throws IOException {
        String path = "D:/Users/Administrator/IdeaProjects/test1/out/production/test1/结果.txt";

        File file1 = new File(path);
        if(file1.getParentFile() == null){
            System.out.println("输入绝对路径");
            return;
        }
        if (!file1.exists()) {
            file1.getParentFile().mkdirs();
        }

        file1.createNewFile();

        // write
        FileWriter fw = new FileWriter(file1, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(s);
        bw.flush();
        bw.close();
        fw.close();

    }
}
