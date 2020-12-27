package filemanager.fileserver;

import equipment.Disk;
import filemanager.CurrentDirCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Create By  @林俊杰
 * 2020/12/17 18:26
 *
 * @version 1.0
 */
class CreateFileTest {

    @Test
    void operation() throws Exception {
        String filename = "myFile.txt";
        CreateFile createFile = new CreateFile();
        for(int i=0;i<3;i++)
        createFile.createFile(filename);
        MakeDir makeDir = new MakeDir();
        for (int i=0;i<3;i++)
            makeDir.mkFile("新建文件夹");
        SparrowDirectory testDir = makeDir.mkFile("测试文件夹");
        CurrentDirCatalog.setCurrentDir(testDir);
        for (int i = 0; i < 10; i++) {
            createFile.createFile("测试文件夹下的新建文件！");
        }
        for (Document d:testDir.getData()
             ) {
            System.out.println(d.getFileCatalog().toString());
        }
        System.out.println("---------------------------------------------------------------");

        Disk disk = Disk.getDisk();
        SparrowDirectory sparrowDirectory = Disk.getDisk().getRoot();
//        int n=0;
//        for (int i=0;i<disk.getFileAllocateTable().length;i++){
//            if (disk.getFileAllocateTable()[i]==-1)
//                n++;
//        }
//        System.out.println("n : "+n);
        System.out.println(sparrowDirectory.getData().size());
        for (Document f:sparrowDirectory.getData()
             ) {
            System.out.println(f.getFileCatalog().toString());
            //System.out.println(f.getFileCatalog().getStartIndex()+"\n\n");
            //System.out.println(Disk.getDisk().getRoot().getSize());
        }
        System.out.print(" ");
        for(int i=0;i<Disk.getDisk().getFileAllocateTable().length;i++){
            System.out.print(i+"   ");
        }
        System.out.println();
        for (int i:Disk.getDisk().getFileAllocateTable()
             ) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }

    @Test
    void createDir(){
        MakeDir makeDir = new MakeDir();
        makeDir.mkFile("testFile");

        List<Document>documents = Disk.getDisk().getRoot().getData();
        for (int i=0;i<documents.size();i++){
            System.out.println(documents.get(i).getFileCatalog().toString());
        }

    }

    @Test
    void getAvailable(){
        Disk disk = Disk.getDisk();
        System.out.println(disk.getFirstAvailableBlock());
    }

    @Test
    void deleteTest() throws Exception {
        CreateFile createFile = new CreateFile();
        Disk disk = Disk.getDisk();
        SparrowFile sparrowFile = createFile.createFile("新建文件.txt");
        DeleteFile deleteFile = new DeleteFile();
        deleteFile.deleteFile(sparrowFile);
        //Disk disk = Disk.getDisk();
        SparrowDirectory sparrowDirectory = Disk.getDisk().getRoot();
//        int n=0;
//        for (int i=0;i<disk.getFileAllocateTable().length;i++){
//            if (disk.getFileAllocateTable()[i]==-1)
//                n++;
//        }
//        System.out.println("n : "+n);
        System.out.println(sparrowDirectory.getData().size());
        for (Document f:sparrowDirectory.getData()
        ) {
            System.out.println(f.getFileCatalog().toString());
            //System.out.println(f.getFileCatalog().getStartIndex()+"\n\n");
            //System.out.println(Disk.getDisk().getRoot().getSize());
        }
        System.out.print(" ");
        for(int i=0;i<Disk.getDisk().getFileAllocateTable().length;i++){
            System.out.print(i+"   ");
        }
        System.out.println();
        for (int i:Disk.getDisk().getFileAllocateTable()
        ) {
            System.out.print(i + "  ");
        }
        System.out.println();
    }
}