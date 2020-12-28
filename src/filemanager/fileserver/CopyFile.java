package filemanager.fileserver;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import filemanager.CurrentDirCatalog;
import filemanager.FileCatalog;
import filemanager.FileServer;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import tools.FileTool;

import javax.print.Doc;
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



//    public void pasteFile2CurDir(SparrowFile sparrowFile){
//        //更新当前目录
//        SparrowDirectory sparrowDirectory = CurrentDirCatalog.getCurrentDir();
//        List<Document>documentList = sparrowDirectory.getData();
//        for (int i = 0; i < documentList.size(); i++) {
//            if (Disk.getDisk().getFileAllocateTable()[documentList.get(i).getFileCatalog().getStartIndex()]==0){
//                if (documentList.get(i).getFileCatalog().getExtensionName()== FileTypeEnum.DIR_LABEL.getCode()){
//                    FileTool.addFile2Directory((SparrowFile)documentList.get(i));
//                }else{
//                    FileTool.addDir2Directory((SparrowDirectory)documentList.get(i));
//                }
//                return;
//            }
//        }
//    }

    public void updateCurrent(SparrowDirectory sparrowDirectory){
        //更新当前文件夹
        List<Document>documentList = sparrowDirectory.getData();
        for (int i = 0; i < documentList.size(); i++) {
            if (!documentList.get(i).getFileCatalog().getCatalogName().contains("/")&&documentList.get(i) instanceof SparrowFile){
                FileCatalog fileCatalog = documentList.get(i).getFileCatalog();
                fileCatalog.setCatalogName(sparrowDirectory.getFileCatalog().getCatalogName()+"/"+fileCatalog.getCatalogName());
                documentList.get(i).setFileCatalog(fileCatalog);
                SparrowFile sparrowFile = new SparrowFile();
                List<Document>documents = ((SparrowDirectory)(documentList.get(i))).getData();
                sparrowDirectory.setData(documents);
                sparrowDirectory.setFileCatalog(fileCatalog);
                sparrowDirectory.setSize(fileCatalog.getFileLength());

            }
        }
    }

    //文件复制
    public Document copy(Document document){
        if (document instanceof SparrowDirectory){
            //如果是个文件夹
            List<Document>date = ((SparrowDirectory) document).getData();
        }else if(document instanceof  SparrowFile){
            //如果是个文件
            String date = ((SparrowFile) document).getData();
        }
        //分配新的空间
        int startIndex = Disk.getDisk().getFirstAvailableBlock();
        if (startIndex == SizeEnum.FILLED_DISK_LABEL.getCode()){
            System.out.println("磁盘已满！");
            return null;
        }
        int attribute = 0;
        for (int i:document.getFileCatalog().getFileAttribute()
             ) {
            attribute+=i;
            attribute*=10;
        }

        FileCatalog fileCatalog = new FileCatalog(FileTool.getEndFileName(document.getFileCatalog().getCatalogName()),
                document.getFileCatalog().getExtensionName(),
                attribute,
                startIndex,
                document.getFileCatalog().getFileLength());
        System.out.println("copy Test filename : " + fileCatalog.getCatalogName());
        System.out.println("copy Test fileStartIndex : " + fileCatalog.getStartIndex());
        //返回一个重新生成的文件
        if (document instanceof SparrowFile){
            SparrowFile newFile = new SparrowFile();
            newFile.setData(((SparrowFile) document).getData());
            newFile.setFileCatalog(fileCatalog);
            newFile.setSize(fileCatalog.getFileLength());
            return newFile;
        }else{
            SparrowDirectory newFile = new SparrowDirectory();
            newFile.setFileCatalog(fileCatalog);
            newFile.setSize(fileCatalog.getFileLength());
            newFile.setData(((SparrowDirectory)document).getData());
            return newFile;
        }
    }

    //粘贴 更新整个文件夹
    public void pasteDir2Dir(SparrowDirectory sparrowDirectory){
        List<Document>documentList = sparrowDirectory.getData();
        for (int i=0;i<documentList.size();i++){
            if (!documentList.get(i).getFileCatalog().getCatalogName().contains("/")){
                //机芯重命名操作
                FileCatalog fileCatalog = new FileCatalog();
                fileCatalog.setCatalogName(sparrowDirectory.getFileCatalog().getCatalogName()+"/"+documentList.get(i).getFileCatalog().getCatalogName());
                fileCatalog.setFileLength(documentList.get(i).getFileCatalog().getFileLength());
                fileCatalog.setExtensionName(documentList.get(i).getFileCatalog().getExtensionName());
                int attribute = 0;
                for (int j=0;j<documentList.get(i).getFileCatalog().getFileAttribute().length;j++){
                    attribute+=documentList.get(i).getFileCatalog().getFileAttribute()[j];
                    attribute*=10;
                }
                fileCatalog.setFileAttribute(attribute);
                fileCatalog.setStartIndex(documentList.get(i).getFileCatalog().getStartIndex());
                documentList.get(i).setFileCatalog(fileCatalog);
                //将这个新加入的文件写入磁盘中
                if (documentList.get(i) instanceof SparrowDirectory){
                    //如果是个目录
                    Disk.getDisk().writeDirectory2Disk((SparrowDirectory)documentList.get(i));
                }else{
                    //如果是个文件
                    Disk.getDisk().writeFile2Disk((SparrowFile)documentList.get(i));
                }
                FileTool.updateDocument(sparrowDirectory);
                System.out.println("粘贴成功");
                return;
            }
        }
        System.out.println("粘贴失败");
    }

}
