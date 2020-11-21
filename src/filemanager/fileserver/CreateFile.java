package filemanager.fileserver;

import filemanager.Disk;
import filemanager.FileServer;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:29
 */
public class CreateFile implements FileServer {
    //创建文件 create \xx\yy\zz.e
    //如果包含“\"就是使用绝对路径进行创建，否则就是相对路径
    @Override
    public void operation(String instruction) {
        //TODO 创建文件

    }
}
