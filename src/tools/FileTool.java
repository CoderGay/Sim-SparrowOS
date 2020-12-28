package tools;

import com.sun.glass.ui.Size;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/12/13 10:28
 * Modified by WenZhikun
 *
 * @version 1.0
 */
public class FileTool {

    /**
     * "./"：代表目前所在的路径
     * "../"：代表上一层路径
     * "/"开头，代表根目录
     * */

    /**
    public static FileCatalog getExistFile(String fileName){
        System.out.println("getExitFile filename = "+fileName);
        String []dirs = fileName.split("/");
        Disk disk = Disk.getDisk();

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
        String[] allPath = absolutePath.split("/");
        //获取根节点中的目录列表
        return hasFile(allPath,Disk.getDisk().getRoot());
    }

    //判断相对路径下是否存在文件
    public static FileCatalog hasRelativePath(String filePath){
        SparrowDirectory currentDir = CurrentDirCatalog.getCurrentDir();
        String[] relativePath = filePath.split("/");
        return hasFile(relativePath,currentDir);
    }

    public static FileCatalog hasFile(String[] path,SparrowDirectory directory){
        FileCatalog resultFileCatalog = null;
        List<Document> directoryData = directory.getData();
        int index = 1;
        if (path.length==1)
            return Disk.getDisk().getRoot().getFileCatalog();
        while(true){
            for (Document d:directoryData
            ) {
                System.out.println(path.length);
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
    }*/

    //根据路径查找文件
    public static FileCatalog getFile(String filePath){
        System.out.println("Find File is "+filePath);

        String[] paths = filePath.split("/");
        /**
         * 测试数据
         * */
        if (paths.length>0)
        for (String node:paths
             ) {
            System.out.println("path_: "+node);
        }
        /**
         * 以上为测试数据
         * */

        if (paths.length==1){
            return Disk.getDisk().getRoot().getFileCatalog();
        }

        Disk disk = Disk.getDisk();
        SparrowDirectory root = disk.getRoot();
        List<Document>documentList = root.getData();

        int index=1;
        int j=0;
        while (j<documentList.size()){
            System.out.println(documentList.size() + ": "+FileTool.getEndFileName(documentList.get(j).getFileCatalog().getCatalogName()));
            System.out.println(paths[index]);
            if (FileTool.getEndFileName(documentList.get(j).getFileCatalog().getCatalogName()).equals(paths[index])){
                System.out.println(documentList.get(j).getFileCatalog().toString());
                if (index==paths.length-1){
                    System.out.println("success found !");
                    return documentList.get(j).getFileCatalog();
                }else{
                    j=0;
                    index++;
                    documentList = ((SparrowDirectory)documentList.get(j)).getData();
                    //System.out.println("i = "+ index);
                }
            }else {
                j++;
            }
        }
        return null;
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
        //System.out.println("divide : "+ divided);
        //System.out.println("allData length is "+ allData.size());
        int length = allData.size()-1;
        //System.out.println(length);
        if (allData.size()%8!=0){
            divided++;
        }
        int i=0;
        while (i<divided){

            SparrowDirectory apartDirectory = new SparrowDirectory();
            apartDirectory.setFileCatalog(directory.getFileCatalog());
            List<Document> subDataList = new ArrayList<>();
            for (int j=strStartIndex;j<strStartIndex+8;j++){

                subDataList.add(allData.get(j));
                if (j==length)
                    break;
            }
            apartDirectory.setData(subDataList);
            apartDirectory.setSize(apartDirectory.getData().size()*SizeEnum.BLOCKS_DIR_SIZE.getCode());
            resultDirectory.add(apartDirectory);
            strStartIndex+=SizeEnum.BLOCKS_DIR_SIZE.getCode();
            i++;
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
        if (fileCatalog==null){
            System.out.println("结点为空！");
            return null;
        }
        List<DiskBlock> diskBlocks = Disk.getDisk().getGivenDiskBlocks(fileCatalog);
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

    //添加文件到父目录中
    public static void addFile2Directory(SparrowFile sparrowFile){
        //添加到父目录的结点中
        SparrowDirectory fatherDirectory = CurrentDirCatalog.getCurrentDir();
        List<Document>documents = fatherDirectory.getData();
        //System.out.println("addFile2Dir length0 : "  + fatherDirectory.getData().size());
        documents.add(sparrowFile);
        fatherDirectory.setData(documents);
        fatherDirectory.setSize(8*documents.size());
        FileCatalog fileCatalog = fatherDirectory.getFileCatalog();
        fileCatalog.setFileLength(fatherDirectory.getSize());
        fatherDirectory.setFileCatalog(fileCatalog);

        FileTool.updateDocument(fatherDirectory);

        //System.out.println("文件添加成功");
        //更新当前文件夹
        CurrentDirCatalog.setCurrentDir(fatherDirectory);
    }

    //添加文件夹到父目录中
    public static void addDir2Directory(SparrowDirectory sparrowDirectory){
        //添加到父目录的结点中
        SparrowDirectory fatherDirectory = CurrentDirCatalog.getCurrentDir();
        List<Document>documents = fatherDirectory.getData();
        documents.add(sparrowDirectory);
        fatherDirectory.setData(documents);
        fatherDirectory.setSize(8*documents.size());
        FileCatalog fileCatalog = fatherDirectory.getFileCatalog();
        fileCatalog.setFileLength(fatherDirectory.getSize());
        fatherDirectory.setFileCatalog(fileCatalog);
        //更新文件夹盘块
        FileTool.updateDocument(fatherDirectory);
        //System.out.println("文件夹添加成功");
        //更新当前文件夹
        CurrentDirCatalog.setCurrentDir(fatherDirectory);
    }

    public static void pasteDir2Directory(SparrowDirectory sparrowDirectory){

    }

    //从文件夹中删除文件
    public static void deleteFilefromDir(Document document){
        SparrowDirectory sparrowDirectory = CurrentDirCatalog.getCurrentDir();
        FileCatalog fileCatalog = document.getFileCatalog();
        List<Document>documentList=sparrowDirectory.getData();
        //System.out.println("size of document is "+documentList.size());
        for (int i=0;i<documentList.size();i++){
            if (documentList.get(i).getFileCatalog().getCatalogName().equals(fileCatalog.getCatalogName())){
                //System.out.println(documentList.get(i).getFileCatalog().toString());
                documentList.remove(i);
                //System.out.println("success find !");
                break;
            }
        }

        sparrowDirectory.setData(documentList);
        //更新当前文件夹
        updateDocument(sparrowDirectory);
        CurrentDirCatalog.setCurrentDir(sparrowDirectory);
        //回收所有占用的盘块
        Disk.getDisk().fileRecycling(fileCatalog);
        //System.out.println("size of sparrowDirectory is "+sparrowDirectory.getData().size());
        Disk.output2DiskDocument(Disk.getDisk());
        System.out.println("删除文件成功 ！");
    }

    //从文件夹中删除文件夹
    public static void deleteDirFromDir(SparrowDirectory directory){
        //1、从父目录中删除该文件目录项，并更新父目录(父目录就是当前目录)
        //2、回收文件所占有的所有盘块
        SparrowDirectory sparrowDirectory = CurrentDirCatalog.getCurrentDir();
        FileCatalog fileCatalog = directory.getFileCatalog();
        List<Document>documentList=sparrowDirectory.getData();
        for (int i=0;i<documentList.size();i++){
            if (documentList.get(i).getFileCatalog().getCatalogName().equals(fileCatalog.getCatalogName())){
                documentList.remove(i);
                break;
            }
        }
        sparrowDirectory.setData(documentList);
        //更新当前文件夹
        updateDocument(sparrowDirectory);
        CurrentDirCatalog.setCurrentDir(sparrowDirectory);
        //回收文件夹下的所有盘块
        recycleDirectory(directory);
        Disk.output2DiskDocument(Disk.getDisk());
        System.out.println("删除文件夹成功 ！");
    }


    //删除文件夹下的所有节点,递归
    public static void recycleDirectory(SparrowDirectory sparrowDirectory){
        List<Document>documents = sparrowDirectory.getData();

        for (int i = 0; i < documents.size(); i++) {
            if (documents.get(i).getFileCatalog().getExtensionName()==FileTypeEnum.DIR_LABEL.getCode()){
                //如果依然是目录，则继续递归
                //System.out.println("recycleDirectory loop dir!");
                recycleDirectory((SparrowDirectory)documents.get(i));
            }else{

                Disk.getDisk().fileRecycling(documents.get(i).getFileCatalog());

            }
        }
        //释放自己
        //Disk.getDisk().fileRecycling(sparrowDirectory.getFileCatalog());
    }


    //文件夹进行增删之后进行更新
    public static void updateDocument(SparrowDirectory sparrowDirectory){
        Disk.getDisk().fileRecycling(sparrowDirectory.getFileCatalog());
        Disk.getDisk().writeDirectory2Disk(sparrowDirectory);
        return;
    }

    //更新文件
    public static void updateFile(SparrowFile sparrowFile){
        Disk.getDisk().fileRecycling(sparrowFile.getFileCatalog());
        Disk.getDisk().writeFile2Disk(sparrowFile);
        return;
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
            //if (startIndex!=SizeEnum.END_BLOCKS_LABEL.getCode())
            stringBuilder.append(",");
        }
        stringBuilder.append(startIndex+1);
        return stringBuilder.toString();
    }

    public static void renameFile(Document document){
        //判断是文件还是目录
        if (document.getFileCatalog().getExtensionName()==FileTypeEnum.DIR_LABEL.getCode()){
            updateDocument((SparrowDirectory) document);
            Disk.output2DiskDocument(Disk.getDisk());
        }else{
            updateFile((SparrowFile)document);
            Disk.output2DiskDocument(Disk.getDisk());
        }
    }

    public static String getNewName(String oldName,boolean isNewDoc){
        StringBuilder newName = new StringBuilder();
        String[] split = oldName.split("\\.");
        if (split.length>0)
        newName.append(split[0]);
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String substring = dateStr.substring(dateStr.length() - 5, dateStr.length());
        if (isNewDoc){
            newName.append("(");
            newName.append(substring);
            newName.append(")");
        }else {
            newName.append("-副本"+substring);
        }
        if (split.length>1)
        newName.append("."+split[1]);
        return newName.toString();
    }
}
