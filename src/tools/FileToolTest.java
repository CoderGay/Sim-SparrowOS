package tools;

import equipment.Disk;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import org.junit.jupiter.api.Test;

import javax.print.Doc;
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
        List<FileCatalog>list = new ArrayList<>();
        for (int i=0;i<20;i++){
            FileCatalog fileCatalog = new FileCatalog();
            fileCatalog.setCatalogName(Integer.toString(i));
            list.add(fileCatalog);
        }
        SparrowDirectory directory = new SparrowDirectory();
        directory.setData(list);
        FileCatalog fileCatalog = new FileCatalog("Test",0,00001000,100,list.size()*8);
        directory.setFileCatalog(fileCatalog);
        directory.setSize(directory.getFileCatalog().getFileLength());
        List<Document>documents = FileTool.decomposeDirectory(directory);
        if (documents!=null)
            for (Document d:documents
            ) {
                System.out.println(d.getSize());
                System.out.println(d.getFileCatalog().getCatalogName());

            }
    }
}