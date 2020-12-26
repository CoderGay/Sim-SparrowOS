package tools;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.DiskBlock;
import filemanager.CurrentDirCatalog;
import equipment.Disk;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import org.junit.Assert;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/12/13 10:28
 * Modified by WenZhikun
 *
 * @version 1.0
 */
public class FileTool {

    public static FileCatalog isExist(String fileName){
        String []dirs = fileName.split("\\\\");
        Disk disk = Disk.getDisk();

        /**
        * "./"：代表目前所在的路径
        * "../"：代表上一层路径
        * "/"开头，代表根目录
        * */
        if (dirs[0].equals(".")||dirs[0].equals("..")){
            if(dirs[0].equals(".")){
                System.out.println("判断相对路径下文件是否存在");
                FileCatalog fileCatalog = hasRelativePath(fileName);
                if (fileCatalog==null){
                    System.out.println("[Not Found]相对路径下文件不存在");
                }
                return fileCatalog;
            }else {
                //TODO 上一文件夹下的文件名检索
                System.out.println("检索上一级文件夹");
            }
            return null;
        }else if(dirs[0].equals("")) {  //空字符串代表它在根目录下
            FileCatalog fileCatalog = hasAbsolutePath(fileName);
            System.out.println("绝对路径");
            if (fileCatalog==null){
                System.out.println("[Not Found]绝对路径下该文件不存在");
            }
           return fileCatalog;
        }else{
            System.out.println("[ERROR]文件查找语句输入有误");
            return null;
        }
    }

    //绝对路径判断文件是否存在
    private static FileCatalog hasAbsolutePath(String absolutePath){
        //对文件目录进行划分
        String[] allPath = absolutePath.split("\\\\");
        //获取根节点中的目录列表
        return hasFile(allPath,Disk.getDisk().getRoot());
    }

    //判断相对路径下是否存在文件
    public static FileCatalog hasRelativePath(String filePath){
        SparrowDirectory currentDir = CurrentDirCatalog.getCurrentDir();
        String[] relativePath = filePath.split("\\\\");
        return hasFile(relativePath,currentDir);
    }

    public static FileCatalog hasFile(String[] path,SparrowDirectory directory){
        FileCatalog resultFileCatalog = null;
        List<Document> directoryData = directory.getData();
        int index = 1;
        while(true){
            for (Document d:directoryData
            ) {
                if (path[index].equals(d.getFileCatalog().getCatalogName())){
                    if (d.getFileCatalog().getExtensionName()==0){
                        //如果当前目录结点代表的是目录，且目录名字匹配，则继续向下查找
                        //获取子目录
                        SparrowDirectory sparrowDirectory = getSparrowDirectory(d.getFileCatalog());
                        if (sparrowDirectory==null){
                            System.out.println("[ERROR]目录项为空");
                            return null;
                        }else
                            directoryData = sparrowDirectory.getData();
                        index++;
                        if (index== path.length-1){
                            //表示找到子目录
                            return d.getFileCatalog();
                        }
                        break;
                    }else{
                        if (index!= path.length-1){
                            System.out.println("[指令有误]文件名应当为最后一项");
                            return null;
                        }else{
                            //表示成功找到文件
                            return d.getFileCatalog();
                        }
                    }
                }
            }
        }
    }


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

    public static List<SparrowFile> decomposeFile(SparrowFile file){
        //分解内容超过64B的文件
        List<SparrowFile> resultFiles = new ArrayList<>();
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
        if (file.getSize()% SizeEnum.BLOCKS_SIZE.getCode()!=0){
            SparrowFile apartFile = new SparrowFile();
            apartFile.setFileCatalog(file.getFileCatalog());
            apartFile.setSize(SizeEnum.BLOCKS_SIZE.getCode());
            apartFile.setData(allData.substring(strStartIndex,allData.length()));
            resultFiles.add(apartFile);
        }

        return resultFiles;
    }


    //分解字目录中list<FileCatalog>长度超过8的子目录
    public static List<SparrowDirectory> decomposeDirectory(SparrowDirectory directory){
        List<SparrowDirectory>resultDirectory = new ArrayList<>();
        if (directory.getData().size()<=SizeEnum.BLOCKS_DIR_SIZE.getCode()){
            System.out.println("[分解失败]无需分解，该子目录中仅有"+directory.getData().size()+"个目录项");
            return null;
        }
        int divided = (directory.getData().size())/SizeEnum.BLOCKS_DIR_SIZE.getCode();
        int strStartIndex = 0;
        //子目录中含有的目录项结点的个数
        List<Document>allData = directory.getData();
        int length = allData.size()-1;
        //System.out.println(length);
        for (int i = 0; i < divided+1; i++) {
            SparrowDirectory apartDirectory = new SparrowDirectory();
            apartDirectory.setFileCatalog(directory.getFileCatalog());
            List<Document> subDataList = new ArrayList<>();
            for (int j=strStartIndex;j<strStartIndex+8;j++){
                //System.out.println(j);
                subDataList.add(allData.get(j));
                if (j==length)
                    break;
            }
            apartDirectory.setData(subDataList);
            apartDirectory.setSize(apartDirectory.getData().size()*SizeEnum.BLOCKS_DIR_SIZE.getCode());
            resultDirectory.add(apartDirectory);
            strStartIndex+=SizeEnum.BLOCKS_DIR_SIZE.getCode();
        }
        return resultDirectory;
    }

    //通过目录结点获取文件&&合并文件
    public static SparrowFile getSparrowFile(FileCatalog fileCatalog){
        List<DiskBlock> diskBlocks = Disk.getDisk().getGivenDiskBlocks(fileCatalog);
        if (diskBlocks.size()==1){
            System.out.println("[合并失败]文件仅占用一个盘块，无需合并");
            return (SparrowFile)diskBlocks.get(0).getData();
        }
        SparrowFile resultFile = new SparrowFile();
        resultFile.setData("");
        resultFile.setFileCatalog(fileCatalog);
        resultFile.setSize(fileCatalog.getFileLength());
        for (DiskBlock d:diskBlocks
             ) {
            SparrowFile sparrowFile = (SparrowFile)(d.getData());
            resultFile.setData(resultFile.getData()+sparrowFile.getData());
        }
        return resultFile;
    }


    //通过目录结点获取目录&&合并目录
    public static SparrowDirectory getSparrowDirectory(FileCatalog fileCatalog){
        List<DiskBlock>diskBlocks=Disk.getDisk().getGivenDiskBlocks(fileCatalog);
        if (diskBlocks.size()==1){
            System.out.println("[合并失败]该目录仅占用一个盘块");
            return  (SparrowDirectory)diskBlocks.get(0).getData();
        }
        SparrowDirectory resultDirectory = new SparrowDirectory();
        resultDirectory.setSize(fileCatalog.getFileLength());
        resultDirectory.setFileCatalog(fileCatalog);
        List<Document> directoryData = new ArrayList<>();
        for (DiskBlock d:diskBlocks
             ) {
            SparrowDirectory sparrowDirectory = (SparrowDirectory) d.getData();
            /*for (int i=0;i<sparrowDirectory.getData().size();i++){
                //fileCatalogList.add(sparrowDirectory.getData().get(i));
                directoryData.add(sparrowDirectory.getData().get(i));
            }*/
            directoryData.addAll(sparrowDirectory.getData());
        }
        resultDirectory.setData(directoryData);
        return resultDirectory;
    }

    public static String getEndFileName(String filePath){
        String result;
        String[] split = filePath.split("/");
        if (split.length==0){
            result = "根目录";
        }else if(split.length==1){
            result = split[0];
        }else{
            result = split[split.length-1];
        }
        return result;
    }

    public static String getExtensionName(Document document){
        String result = "未知文件";
        if (document.getFileCatalog().getExtensionName()== FileTypeEnum.DIR_LABEL.getCode()){
            result = "目录";
        }else if (document.getFileCatalog().getExtensionName()== FileTypeEnum.EXE_FILE.getCode()){
            result = "可执行文件";
        }else if(document.getFileCatalog().getExtensionName()== FileTypeEnum.TXT_FILE.getCode()){
            result = "文本文件";
        }
        return result;
    }

    public static String getOccupiedDiskBlockNum(Document document){
        Disk disk = Disk.getDisk();
        int[] fileAllocateTable = disk.getFileAllocateTable();
        StringBuilder stringBuilder = new StringBuilder();
        int startIndex = document.getFileCatalog().getStartIndex();
        while(startIndex!=SizeEnum.END_BLOCKS_LABEL.getCode()){
            stringBuilder.append(startIndex+1);
            startIndex = fileAllocateTable[startIndex];
            if (startIndex!=SizeEnum.END_BLOCKS_LABEL.getCode())
                stringBuilder.append(",");
        }
        return stringBuilder.toString();
    }
}
