package filemanager.fileserver;

import filemanager.FileServer;
import filemanager.file.SparrowDirectory;
import tools.FileTool;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:33
 */
public class RemoveDir implements FileServer {
    //删除空文件目录 rmdir /xx/yy
    //目录非空的时候报错

    @Override
    public void operation(String instruction) {
        //命令行操作
    }

    public static void deleteDir(SparrowDirectory sparrowDirectory){
        FileTool.deleteDirFromDir(sparrowDirectory);
    }

}
