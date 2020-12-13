package filemanager;

/**
 * @author WenZhikun
 * @data 2020-11-21 15:10
 */
public class CurrentDirCatalog{
    //当前正在操作的文件目录
    private static FileCatalog currentDir = null;

    public static FileCatalog getCurrentDir(){
        if (currentDir==null){
            currentDir = Disk.getDisk().getRoot();
        }
        return currentDir;
    }
    public static void setCurrentDir(FileCatalog f){
        currentDir=f;
    }
}
