package filemanager.fileserver;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.FileServer;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import tools.FileTool;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:32
 */
public class MakeDir implements FileServer {
    //创建目录 mkdir \xx\yy


    @Override
    public void operation(String instruction) throws Exception {

    }

    public SparrowDirectory mkFile(String dirName) {

        String dirPath = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName()
                +"/"+dirName;
        //判断是否有空闲盘块
        Disk disk = Disk.getDisk();
        int startIndex = disk.getFirstAvailableBlock();
        if (startIndex== SizeEnum.FILLED_DISK_LABEL.getCode()){
            System.out.println("磁盘盘块已经满，创建失败");
            return null;
        }
        //System.out.println("startIndex : "+ startIndex);
        FileCatalog fileCatalog = new FileCatalog(dirPath, FileTypeEnum.DIR_LABEL.getCode(),FileTypeEnum.COMMON_DIR.getCode(),startIndex,SizeEnum.BLANK_FILE_SIZE.getCode());
        //分配盘块给该新建目录
        //System.out.println("startIndex : "+ fileCatalog.getStartIndex());
        disk.fileAllocate(fileCatalog);
        //System.out.println("startIndex : "+ fileCatalog.getStartIndex());
        SparrowDirectory sparrowDirectory = new SparrowDirectory();
        List<Document>documents = new ArrayList<>();
        sparrowDirectory.setData(documents);
        sparrowDirectory.setFileCatalog(fileCatalog);
        sparrowDirectory.setSize(fileCatalog.getFileLength());

        disk.writeDirectory2Disk(sparrowDirectory);//写磁盘

        FileTool.addDir2Directory(sparrowDirectory);
        Disk.output2DiskDocument(disk);
        return sparrowDirectory;

    }
}
