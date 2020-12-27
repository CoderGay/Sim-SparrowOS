package filemanager;

import enums.FileTypeEnum;
import equipment.Disk;
import filemanager.file.SparrowFile;

import java.io.File;

/**
 * @author WenZhikun
 * @data 2020-11-21 16:22
 */
public class WriteFile {
    //写文件操作,覆盖写
    public void writeFile(SparrowFile sparrowFile){
        FileCatalog fileCatalog = sparrowFile.getFileCatalog();
        OpenFile openFile = OpenFile.getInstance();
        //用写的方式进行打开
        if (openFile.openAFile(sparrowFile, 0100)){
            //表示打开成功
            Disk.getDisk().fileRecycling(sparrowFile.getFileCatalog());
            Disk.getDisk().writeFile2Disk(sparrowFile);
            Disk.output2DiskDocument(Disk.getDisk());
        }
    }



}
