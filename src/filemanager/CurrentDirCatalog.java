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

    public static SparrowDirectory getCurrentDir(){
        if (currentDir==null){
            currentDir = Disk.getDisk().getRoot();
        }
        return currentDir;
    }
    public static void setCurrentDir(SparrowDirectory f){
        currentDir=f;
    }
}
