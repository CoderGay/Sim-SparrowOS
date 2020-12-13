package tools;

import enums.DiskLabelEnum;
import filemanager.CurrentDirCatalog;
import filemanager.Disk;
import filemanager.FileCatalog;

/**
 * Create By  @林俊杰
 * 2020/12/13 10:28
 *
 * @version 1.0
 */
public class FileNameTool {

    //TODO disk文件中最好使用对象流!

    public static boolean isExist(String fileName){
        String []dirs = fileName.split("/");
        Disk disk = Disk.getDisk();

        /**
        * "./"：代表目前所在的路径
        * "../"：代表上一层路径
        * "/"开头，代表根目录
        * */
        if (dirs[0].equals(".")||dirs[0].equals("..")){
            //TODO 1.以相对路径为文件名检索是否存在
            System.out.println("相对路径");
            if(dirs[0].equals(".")){
                //TODO 同文件夹下的文件名检索
                FileCatalog currentDir = CurrentDirCatalog.getCurrentDir();
                String curDirName = currentDir.getCatalogName();

            }else {
                //TODO 上一文件夹下的文件名检索
            }

            return true;
        }else if(dirs[0].equals("")) {  //空字符串代表它在根目录下
            //TODO 2.以绝对路径为文件名检索是否存在
            boolean yes = hasAbsolutePath(fileName);

            System.out.println("绝对路径");
           return true;
        }

        return false;
    }

    //TODO: 此处需要递归
    private static boolean hasAbsolutePath(String absolutePath){

        Disk disk = Disk.getDisk();
        String []dirs = absolutePath.split("/");
        String rootContent = disk.getDiskBlockContent(disk.getRoot());
        if (rootContent.equals("")){
            System.out.println("[Warning]: 根目录为空!");
            return false;
        }
        String[] rootDirs = rootContent.split(DiskLabelEnum.DIR_DIVIDE_LABEL.getLabel());
        for (String dir:rootDirs
             ) {
            if (dirs[1].equals(dir)){
                if(dirs[1].contains(".")){
                    //是文件
                    return true;
                }else{
                    //是目录,递归检索

                }
                break;
            }
        }

        return false;
    }

}
