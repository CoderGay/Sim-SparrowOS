package filemanager;

/**
 * @author WenZhikun
 * @data 2020-11-21 15:10
 */
public class CurrentDirCatalog {
    //当前正在操作的文件目录
    public static FileCatalog currentDir = null;

    public static FileCatalog getCurrentDir(){
        return currentDir;
    }
}
