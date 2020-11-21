package filemanager.fileserver;

import filemanager.Disk;
import filemanager.FileCatalog;
import filemanager.FileServer;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:32
 */
public class CopyFile implements FileServer {
    //复制文件 copy \xx\yy \xx\zz
    @Override
    public void operation(String instruction) {

        if(!instruction.toLowerCase().equals("copy")){
            //TODO 抛异常;
            return ;
        }

        //TODO 复制文件
        FileCatalog srcFile ;

    }

}
