package filemanager;

import equipment.Disk;
import filemanager.file.SparrowDirectory;

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

        //TODO 没写的哈,根据目录树自动寻找到父节点
        if (fatherDir==null){
            fatherDir = currentDir;
        }
        return fatherDir;
    }
    public static void setCurrentDir(SparrowDirectory f){
        currentDir=f;
    }

    public static void setFatherDir(SparrowDirectory f){
        fatherDir=f;
    }
}
