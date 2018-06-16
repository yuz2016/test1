import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;


public class GetAllFiles {

	 /*获取一个想要的指定文件的集合，获取文件夹下(包含子目录的所有.java的文件对象，并存储到集合中)
      * 思路：
	  * 1，既然包含子目录，就需要递归。
	  * 2，在递归过程中需要过滤器
	  * 3，满足条件，都添加到集合中
	  */

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File dir = new File("E:\\txt文件");
        List<File> list = new ArrayList<File>();
        FileFilter filterByTxt = new FilterBySuffix(".txt");
        getFileList(dir, list, filterByTxt);
        for (File list1 : list) {
            System.out.println(list1);
        }
    }

    public static void getFileList(File dir, List<File> list,
                                    FileFilter filterbyjava) {

        if(dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    getFileList(file, list, filterbyjava);
                } else {
                    if (filterbyjava.accept(file)) {
                        list.add(file);
                    }
                }
            }
        }else{
            list.add(dir);
        }

    }

}

class FilterBySuffix implements FileFilter {

    public String suffix;

    public FilterBySuffix(String suffix) {
        super();
        this.suffix = suffix;
    }

    public boolean accept(File pathname) {
        // TODO Auto-generated method stub
        return pathname.getName().endsWith(suffix);
    }

}
