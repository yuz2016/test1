import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Grep implements Command{

    private static Charset charset = Charset.forName("utf-8");
    private static CharsetDecoder decoder = charset.newDecoder();

    private static Pattern linePattern = Pattern.compile(".*\r?\n");
    private static Pattern pattern;

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage : java Grep pattern file...");
            return;
        }
        compile(args[0]);
        for (int i = 1; i < args.length; i++) {
            File f = new File(args[i]);
            try {
                grep(f);
            } catch (IOException e) {
                System.err.println(f + ":" + e);
            }
        }
    }

    private static void grep(File f) throws IOException {
        //取得 FileChannel
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        // Get the file's size and then map it into memory
        int size = (int) fc.size();
        MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size);
        //把 字节buffer decode 成 charBuffer
        CharBuffer cb = decoder.decode(bb);
        grep(f, cb);
        fc.close();
    }

    private static void grep(File f, CharBuffer cb) throws IOException {
        Matcher lm = linePattern.matcher(cb); //line matcher
        Matcher pm = null; //pattern matcher
        int lines = 0;
        StringBuilder stringBuilder = new StringBuilder();
        while (lm.find()) {
            lines++;
            CharSequence curLineCs = lm.group();//the current line
            if (pm == null) {
                pm = pattern.matcher(curLineCs);
            } else {
                pm.reset(curLineCs);
            }
            if (pm.find()) {
                System.out.println(f + "→" + lines + ":" + curLineCs);
            }
            if (lm.end() == cb.limit()) { //文件最后
                break;
            }
        }
        SaveProcessResult.save(f,stringBuilder.toString());
    }

    private static void compile(String pat) {
        try {
            pattern = Pattern.compile(pat);
        } catch (PatternSyntaxException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public void process(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage : java Grep pattern file...");
            return;
        }
        compile(args[1]);
        File dir = new File(args[2]);
        List<File> list = new ArrayList<File>();
        FileFilter filterByTxt = new FilterBySuffix(".txt");
        GetAllFiles.getFileList(dir, list, filterByTxt);
        for (int i = 0; i < list.size(); i++) {
            try {
                grep(list.get(i));
            } catch (IOException e) {
                System.err.println(list.get(i) + ":" + e);
            }
        }
    }
}