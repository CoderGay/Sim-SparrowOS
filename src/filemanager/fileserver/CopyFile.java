package filemanager.fileserver;

import enums.FileTypeEnum;
import equipment.Disk;
import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.FileServer;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import tools.FileTool;

import java.io.File;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:32
 */
public class CopyFile implements FileServer {
    //复制文件 copy \xx\yy \xx\zz
    @Override
    public void operation(String instruction) throws Exception{

        if(!instruction.toLowerCase().equals("copy")){

            throw new Exception("文件复制命令不正确");
        }


        FileCatalog srcFile;


    }

    //粘贴 更新整个文件夹
    public void pasteDir2Dir(SparrowDirectory sparrowDirectory){
        FileTool.pasteDir2Directory(sparrowDirectory);
    }

    public void pasteFile2CurDir(SparrowFile sparrowFile){
        //更新当前目录
        SparrowDirectory sparrowDirectory = CurrentDirCatalog.getCurrentDir();
        List<Document>documentList = sparrowDirectory.getData();
        for (int i = 0; i < documentList.size(); i++) {
            if (Disk.getDisk().getFileAllocateTable()[documentList.get(i).getFileCatalog().getStartIndex()]==0){
                if (documentList.get(i).getFileCatalog().getExtensionName()== FileTypeEnum.DIR_LABEL.getCode()){
                    FileTool.addFile2Directory((SparrowFile)documentList.get(i));
                }else{
                    FileTool.addDir2Directory((SparrowDirectory)documentList.get(i));
                }
                return;
            }
        }
    }
}
