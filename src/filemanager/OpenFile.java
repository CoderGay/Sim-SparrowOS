package filemanager;

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

    //TODO 打开文件并更新'已打开文件表'(即openedFileList);
    public boolean openAFile(String fileName,int operationType){
        //if (openedFileList.contains())
        //TODO 检索所有文件表是否有该文件名的文件存在

            //TODO 1.以绝对路径为文件名检索是否存在


            //TODO 2.以相对路径为文件名检索是否存在

        /**
         * 方案一：构建局部文件树(即当前目录为根节点)，检索文件树形目录结构
         * 方案二: 构建局部文件链表(即当前目录为头指针)，顺序检索比较文件名。
         * 以上两个方案所说的‘局部’都是指当前目录，当前目录的父目录以及兄弟目录都不能访问，直接抛搜索不到
         * */
        //TODO 找到文件名后,比较操作类型(读、写或只读)与文件的操作权限(是否为只读,能写)，确保不能以写方式打开只读文件；

        //TODO 文件名和操作类型都符合规范后，最后填写已打开文件表，若文件已经打开则不需要填写已打开文件表。

        return false;
    }

}
