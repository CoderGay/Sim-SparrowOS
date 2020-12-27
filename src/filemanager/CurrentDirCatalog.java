package filemanager;

import equipment.Disk;
import filemanager.file.SparrowDirectory;
import tools.FileTool;

import java.io.File;

/**
 * @author WenZhikun
 * @data 2020-11-21 15:10
 */
public class CurrentDirCatalog{
    //当前正在操作的文件目录
    private static SparrowDirectory currentDir = null;

    private static SparrowDirectory fatherDir = null;

    public static SparrowDirectory getCurrentDir(){
        if (currentDir==null){
            currentDir = Disk.getDisk().getRoot();
            fatherDir = currentDir;
        }
        return currentDir;
    }

    public static SparrowDirectory getFatherDir(){

        if (fatherDir==null){
            fatherDir = currentDir;
        }
        return fatherDir;
    }

    //自动设置父节点
    public static void setCurrentDir(SparrowDirectory f){
        System.out.println(f.getFileCatalog().getCatalogName());
        currentDir=f;
        FileCatalog fileCatalog = f.getFileCatalog();
        String[] strings = (fileCatalog.getCatalogName()).split("/");
        System.out.println("The File Path is : "+ fileCatalog.getCatalogName());
        /*System.out.println("strings.length: "+strings.length);
        if (strings.length>0)
        for (int i = 0; i < strings.length; i++) {
            System.out.println("string["+i+"] = "+strings[i]);
        }*/

        if (strings.length>2){
            String fatherPath = fileCatalog.getCatalogName().substring(0, fileCatalog.getCatalogName().length()- strings[strings.length-1].length()-1);
            System.out.println("fatherPath: "+fatherPath);
            FileCatalog fatherFileCatalog = FileTool.getFile(fatherPath);

            SparrowDirectory sparrowDirectory = FileTool.getSparrowDirectory(fatherFileCatalog);
            setFatherDir(sparrowDirectory);
        }else{
            setFatherDir(Disk.getDisk().getRoot());
        }
    }

    public static void setFatherDir(SparrowDirectory f){
        fatherDir=f;
    }
}
