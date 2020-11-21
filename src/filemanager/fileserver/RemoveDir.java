package filemanager.fileserver;

import filemanager.FileServer;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:33
 */
public class RemoveDir implements FileServer {
    //删除空文件目录 rmdir /xx/yy
    //目录非空的时候报错

    @Override
    public void operation(String instruction) {
        //TODO 删除目录
    }
}
