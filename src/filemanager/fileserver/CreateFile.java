package filemanager.fileserver;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.FileServer;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import tools.FileTool;

import javax.print.Doc;
import java.io.File;
import java.util.Currency;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:29
 */
public class CreateFile implements FileServer {
    //创建文件 create \xx\yy\zz.e
    //如果包含"/"就是使用绝对路径进行创建，否则就是相对路径

    @Override
    public void operation(String instruction) throws Exception {

    }

    public SparrowFile createFile(String filename) throws Exception {
        String filepath = null;
        if (!filename.contains(".")){
            System.out.println("缺少文件后缀，默认生成txt文件");
            filepath = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName()
                    +"/"+filename+".txt";
        }else{
            filepath = CurrentDirCatalog.getCurrentDir().getFileCatalog().getCatalogName()
                    +"/"+filename;
        }
        System.out.println("filePath = "+filepath);

        //System.out.println("create File : ");
        Disk disk = Disk.getDisk();
        int startindex = disk.getFirstAvailableBlock();
        if (startindex==SizeEnum.FILLED_DISK_LABEL.getCode()){
            System.out.println("磁盘已满，创建失败");
            return null;
        }
        //System.out.println("startIndex : "+startindex);
        FileCatalog fileCatalog = null;
        if (filepath.split("\\.")[1].equals("txt")){
            fileCatalog = new FileCatalog(filepath,FileTypeEnum.TXT_FILE.getCode(),0100,startindex,SizeEnum.BLANK_FILE_SIZE.getCode());
        }else if (filepath.split("\\.")[1].equals("exe")){
            fileCatalog = new FileCatalog(filepath,FileTypeEnum.EXE_FILE.getCode(), 0100,startindex,SizeEnum.BLANK_FILE_SIZE.getCode());
        }else{
           fileCatalog = new FileCatalog(filepath,FileTypeEnum.ELSE_FILE.getCode(), 0100,startindex,SizeEnum.BLANK_FILE_SIZE.getCode());
        }
        if (fileCatalog==null){
            System.out.println("[Error]文件项创建失败！");
            return null;
        }
        Disk.getDisk().fileAllocate(fileCatalog);//分配一个盘块空间

        SparrowFile sparrowFile = new SparrowFile();
        sparrowFile.setData(" ");
        fileCatalog.setFileLength(sparrowFile.getData().length());
        sparrowFile.setSize(fileCatalog.getFileLength());
        sparrowFile.setFileCatalog(fileCatalog);


        disk.writeFile2Disk(sparrowFile);

        FileTool.addFile2Directory(sparrowFile);

        Disk.output2DiskDocument(disk);

        return sparrowFile;
    }

}
