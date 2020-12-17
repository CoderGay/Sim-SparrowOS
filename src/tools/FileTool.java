package tools;

import enums.SizeEnum;
import filemanager.CurrentDirCatalog;
import equipment.Disk;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/12/13 10:28
 *
 * @version 1.0
 */
public class FileTool {



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
            /*boolean yes = hasAbsolutePath(fileName);*/

            System.out.println("绝对路径");
           return true;
        }

        return false;
    }

    //TODO: 此处需要递归
    /*
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
    }*/

    public static void writeObjectStreamFile(Object object,String filename){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObjectStreamFile(String filename)throws FileNotFoundException{
        Object object= null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            object = ois.readObject();
        }catch (FileNotFoundException e) {
            throw  e;
        }finally{
            return object;
        }
    }

    public static boolean isDiskFileExist(String filename){
        File file = new File(filename);
        return file.exists();
    }

    public static List<Document> decomposeFile(SparrowFile file){
        List<Document> resultFiles = new ArrayList<>();
        if (file.getSize()< SizeEnum.BLOCKS_SIZE.getCode()){
            System.out.println("[分解失败]:无需分解,该文件大小仅为"+file.getSize()+"B!");
            return null;
        }
        int divided = file.getSize()/SizeEnum.BLOCKS_SIZE.getCode();
        int strStartIndex = 0;
        String allData  = file.getData();
        for (int i = 0; i < divided; i++) {
            SparrowFile apartFile = new SparrowFile();
            apartFile.setFileCatalog(file.getFileCatalog());
            apartFile.setSize(SizeEnum.BLOCKS_SIZE.getCode());
            apartFile.setData(allData.substring(strStartIndex,strStartIndex+SizeEnum.BLOCKS_SIZE.getCode()));
            resultFiles.add(apartFile);
            strStartIndex+=SizeEnum.BLOCKS_SIZE.getCode();
        }
        if (file.getSize()%SizeEnum.BLOCKS_SIZE.getCode()!=0){
            SparrowFile apartFile = new SparrowFile();
            apartFile.setFileCatalog(file.getFileCatalog());
            apartFile.setSize(SizeEnum.BLOCKS_SIZE.getCode());
            apartFile.setData(allData.substring(strStartIndex,allData.length()));
            resultFiles.add(apartFile);
        }

        return resultFiles;
    }


}
