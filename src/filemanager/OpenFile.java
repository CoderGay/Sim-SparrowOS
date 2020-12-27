package filemanager;

import enums.FileTypeEnum;
import filemanager.file.SparrowFile;
import tools.FileTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Create By  @林俊杰
 * 2020/12/3 21:18
 *
 * @version 1.0
 */
public class OpenFile {

    private List<FileCatalog> openedFileList;

    private static OpenFile openFile;

    private OpenFile(){
        openedFileList = new ArrayList<>();
    }

    public static OpenFile getInstance(){
        if (openFile == null){
            openFile = new OpenFile();
        }
        return openFile;
    }

    public boolean openAFile(SparrowFile sparrowFile,int operationType){
        /**
        String []dirs = fileName.split("\\\\");
        FileCatalog fileCatalog = FileTool.getExistFile(fileName);
         **/
        FileCatalog fileCatalog = sparrowFile.getFileCatalog();
        int[] operate = new int[4];
        for (int i=3;i>=0;i--){
            operate[i] = operationType%10;
            operationType=operationType/10;
        }
        if (fileCatalog!=null){
            //文件存在则打开,则先判断文件权限，然后添加到打开文件表中
            if (fileCatalog.getFileAttribute()[FileTypeEnum.READONLY_LOCAL.getCode()]==1) {
                //如果文件是只读权限，则需要判断打开方式
                if (operate[3] == 1) {
                    openedFileList.add(fileCatalog);
                }
            }else{
                //如果文件具有读写权限，就直接打开
                openedFileList.add(fileCatalog);
            }
        }else{
            return false;
        }
        return false;
    }

    //关闭已经打开的文件
    public boolean closeFile(SparrowFile sparrowFile){
        for (int i = 0; i < openedFileList.size(); i++) {
            if (openedFileList.get(i)== sparrowFile.getFileCatalog()){
                openedFileList.remove(i);
                return true;
            }
        }
        System.out.println("[not found]文件未打开 ");
        return false;
    }

}
