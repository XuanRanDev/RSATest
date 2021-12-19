import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created By XuanRan on 2021/12/19
 */
public class FileUtil {
    public synchronized static void WriteText(String path, String data) {

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        try {
            FileWriter fileWriter = new FileWriter(path);
            fileWriter.write(data);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
