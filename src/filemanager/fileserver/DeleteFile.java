package filemanager.fileserver;

import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.FileServer;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import tools.FileTool;

import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:30
 */
public class DeleteFile implements FileServer {
    //删除文件 delete \xx\yy.e

    @Override
    public void operation(String instruction) {
        /**
        instruction = instruction.toLowerCase();
        if (!instruction.startsWith("delete ")||!instruction.contains(".")){
            System.out.println("Can not found the instruction :"+instruction);
            return ;
        }

        String filepath = instruction.trim().substring(6,instruction.length());

        FileCatalog fileCatalog = FileTool.getExistFile(filepath);*/
    }

    public void deleteFile(Document document){

        FileTool.deleteFilefromDir(document);
    }
}
