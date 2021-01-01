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
    @Override
    public void operation(String instruction) {}

    public void deleteFile(Document document){

        FileTool.deleteFilefromDir(document);
    }
}
