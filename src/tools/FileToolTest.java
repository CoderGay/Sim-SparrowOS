package tools;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import equipment.DiskBlock;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import org.junit.jupiter.api.Test;

import javax.print.Doc;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/12/13 10:34
 *
 * @version 1.0
 */
class FileToolTest {

    @Test
    void isExist() {
        //boolean exist = FileNameTool.isExist("/home/Sim-SparrowOS");
        //Assert.assertNotEquals(false,exist);
        //boolean result = FileNameTool.hasAbsolutePath("/home/Sim-SparrowOS");
        //Disk disk = Disk.getDisk() ;
        //Object object = new Object();
        //FileTool.readObjectStreamFile(disk,"myTest.disk",2);
        //System.out.println(disk.getDiskName());

    }

    @Test
    void decomposeDir(){
        //分解目录测试
        List<Document>list = new ArrayList<>();
        for (int i=0;i<20;i++){
            FileCatalog fileCatalog = new FileCatalog();
            fileCatalog.setCatalogName(Integer.toString(i));
            Document document = new SparrowDirectory();
            document.setFileCatalog(fileCatalog);
            document.setSize(SizeEnum.BLOCKS_DIR_SIZE.getCode());
            list.add(document);
        }
        SparrowDirectory directory = new SparrowDirectory();
        directory.setData(list);
        FileCatalog fileCatalog = new FileCatalog("Test",0,00001000,100,list.size()*8);
        directory.setFileCatalog(fileCatalog);
        directory.setSize(directory.getFileCatalog().getFileLength());
        List<SparrowDirectory>sparrowDirectories = FileTool.decomposeDirectory(directory);
        if (sparrowDirectories!=null)
            for (Document d:sparrowDirectories
            ) {
                System.out.println("length: "+d.getFileCatalog().getFileLength());
                System.out.println(d.getSize());
                System.out.println(d.getFileCatalog().getCatalogName());
            }
    }

    @Test
    void exitFile(){
        Disk disk = Disk.getDisk();
        disk = Disk.getDisk();
        //System.out.println(disk.getDiskName());
        /**
        List<DiskBlock>diskBlocks = disk.getDiskBlockList();
        for (DiskBlock d:diskBlocks
             ) {
            if (d!=null){
                System.out.println();
                System.out.println(d.getData().toString());
            }
        }*/


    }

    @Test
    void findFile(){
        FileCatalog fileCatalog = new FileCatalog("/a",0,FileTypeEnum.COMMON_DIR.getCode(),100,40);
        FileCatalog fileCatalog1 = new FileCatalog("/a/b",0,FileTypeEnum.COMMON_DIR.getCode(),101,30);
        FileCatalog fileCatalog2 = new FileCatalog("/a/b/c.txt",2,FileTypeEnum.COMMON_FILE.getCode(),103,50);
        SparrowDirectory sparrowDirectory = new SparrowDirectory();
        SparrowDirectory sparrowDirectory1 = new SparrowDirectory();
        SparrowFile sparrowFile =new SparrowFile();
        List<Document>documentList = new ArrayList<>();
        List<Document>documentList1 = new ArrayList<>();
        sparrowDirectory.setFileCatalog(fileCatalog);
        sparrowDirectory.setSize(fileCatalog.getFileLength());

        sparrowDirectory1.setFileCatalog(fileCatalog1);
        sparrowDirectory1.setSize(fileCatalog1.getFileLength());

        sparrowFile.setFileCatalog(fileCatalog2);
        sparrowFile.setSize(fileCatalog2.getFileLength());
        sparrowFile.setData("Hello World ! \n");

        documentList1.add(sparrowFile);
        //documentList.add(sparrowFile);
        documentList.add(sparrowDirectory1);

        sparrowDirectory1.setData(documentList1);
        sparrowDirectory.setData(documentList);

        String path = "/a/b/c";
        String[] filePath = path.split("/");

        for (String s:filePath
             ) {
            System.out.println(s);
        }
        FileCatalog root = new FileCatalog("",0,FileTypeEnum.COMMON_DIR.getCode(),110,1);
        SparrowDirectory rootDir = new SparrowDirectory();
        List<Document>List = new ArrayList<>();
        List.add(sparrowDirectory);
        rootDir.setData(List);
        rootDir.setFileCatalog(root);
        rootDir.setSize(root.getFileLength());


        documentList = rootDir.getData();

        int i=1;
        int j=0;

        System.out.println(filePath.length);
        while (j<documentList.size()){
            System.out.println(documentList.size() + ": "+FileTool.getEndFileName(documentList.get(j).getFileCatalog().getCatalogName()));
            System.out.println(filePath[i]);
            if (FileTool.getEndFileName(documentList.get(j).getFileCatalog().getCatalogName()).equals(filePath[i])){
                System.out.println(documentList.get(j).getFileCatalog().toString());
                if (i==filePath.length-1){
                    System.out.println("success found !");
                    break;
                }else{
                    j=0;
                    i++;
                    documentList = ((SparrowDirectory)documentList.get(j)).getData();
                    System.out.println("i = "+ i);
                }
            }else {
                j++;
            }
        }
    }
}