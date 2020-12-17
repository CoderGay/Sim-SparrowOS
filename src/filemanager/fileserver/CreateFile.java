package filemanager.fileserver;

import enums.FileTypeEnum;
import enums.SizeEnum;
import equipment.Disk;
import filemanager.FileCatalog;
import filemanager.FileServer;

/**
 * @author WenZhikun
 * @data 2020-11-21 11:29
 */
public class CreateFile implements FileServer {
    //创建文件 create \xx\yy\zz.e
    //如果包含"/"就是使用绝对路径进行创建，否则就是相对路径
    @Override
    public void operation(String instruction) {
        //TODO 创建文件
        instruction=instruction.toLowerCase();
        if (!instruction.startsWith("create ")||!instruction.contains(".")){
            System.out.println("Can not found the \""+instruction.split(" ")[0]+"command. Do you want to input 'create' ?");
            return ;
        }

        String filePath  = instruction.trim().substring(6,instruction.length());
        String fileTypeStr = filePath.split("\\.")[1];


        int fileType = FileTypeEnum.ELSE_FILE.getCode();
        if (fileTypeStr.equals("txt")){
            fileType = FileTypeEnum.TXT_FILE.getCode();
        }else if(fileTypeStr.equals("exe")){
            fileType = FileTypeEnum.EXE_FILE.getCode();
        }

        Disk disk = Disk.getDisk();
        int startIndex  = disk.getFirstAvailableBlock();
        if (startIndex==SizeEnum.FILLED_DISK_LABEL.getCode()){
            System.out.println("磁盘已满创建失败");
            return;
        }

        FileCatalog fileCatalog = new FileCatalog(filePath,fileType,0100,startIndex, SizeEnum.BLANK_FILE_SIZE.getCode());

        //TODO 将目录项放置在对应父目录的盘块上. 比如说 filePath = "/home/software/a.exe"就要把该目录项fileCatalog放在"/software"目录所在的盘块下.




    }
}
