package filemanager;

import enums.FileTypeEnum;
import enums.SizeEnum;
import filemanager.file.SparrowFile;
import tools.FileTool;

import java.io.File;

/**
 * @author WenZhikun
 * @data 2020-11-21 16:22
 */
public class ReadFile {
    //读文件操作
    public static String read(SparrowFile sparrowFile){
        //1、打开文件
        //2、显示文件内容

        FileCatalog fileCatalog = sparrowFile.getFileCatalog();
        OpenFile openFile = OpenFile.getInstance();
        //用只读的方式打开文件
        if (openFile.openAFile(sparrowFile, 0101)){
            //表示打开成功
            return sparrowFile.getData();
        }
        return null;
    }
}
