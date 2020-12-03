package filemanager;

import enums.SizeEnum;

/**
 * @author WenZhikun
 * @data 2020-11-21 16:22
 */
public class ReadFile {
    //读文件操作
    //TODO read file
    public static FileCatalog read(String fileName,int readLength){
        //
        FileCatalog fileCatalog = null;
        String []dirs = fileName.split("\\\\");
        if (readLength< SizeEnum.BLOCKS_SIZE.getCode()){
            //TODO 读取的文件长度小于一个盘块大小
        }else{
            //TODO 读取的文件长度超过一个盘块大小。
        }


        return fileCatalog;
    }


}
