package equipment;

import enums.FileTypeEnum;
import enums.SizeEnum;
import filemanager.FileCatalog;
import filemanager.file.Document;
import filemanager.file.SparrowDirectory;
import filemanager.file.SparrowFile;
import tools.FileTool;

import javax.print.Doc;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-21 8:57
 */
public class Disk implements Serializable,Device{
    //磁盘，共有256盘快，每盘快大小为64B
    //采用链式分配方式，文件分配表每个项占1B，共256B，占用前4个盘快(0,1,2,3)
    //根目录 占用64B, 共占用一个盘块(4);

    //文件分配表（链式分配中盘块索引）,长度256
    //-1表示为文件最后一块，0代表可分配，其它代表已占用
    private int[] fileAllocateTable= new int[SizeEnum.DISK_SIZE.getCode()];

    //每个盘快使用情况,未使用时候是64字节（值为64）
    private int[] blocksStatus = new int[SizeEnum.DISK_SIZE.getCode()];

    //可用盘块个数
    private int availableBlocks;

    //根目录 为目录文件和系统文件 在盘块4中
    private SparrowDirectory root;

    private static String diskName;

    private List<DiskBlock> diskBlockList=new ArrayList<>();

    //设计成单例模式
    private static Disk disk = null;

    @Override
    public int signal() {
        return 0;
    }

    private Disk(){
        //初始化文件分配表和盘块
        FileCatalog fileCatalog = new FileCatalog("", FileTypeEnum.DIR_LABEL.getCode(),1010,4,SizeEnum.BLOCKS_SIZE.getCode());
        //root.getData().add(fileCatalog);
        root = new SparrowDirectory();
        root.setFileCatalog(fileCatalog);
        List<Document> nullList = new ArrayList<>();
        root.setData(nullList);
        root.setSize(SizeEnum.BLANK_FILE_SIZE.getCode() );

        //自定义磁盘文件
        try{
            diskName = System.getProperty("user.dir")+"/resource"+"/Sparrow.disk";
            if(!FileTool.isDiskFileExist(diskName)){
                throw new FileNotFoundException();
            }
        }catch (java.io.FileNotFoundException e){

            /**
             * 对文件分配表进行初始化
             * */
            int fileAllocateTableArray[][] = new int[4][SizeEnum.BLOCKS_SIZE.getCode()];
            for (int i = 0; i< SizeEnum.DISK_SIZE.getCode(); i++){
                if (i<5){
                    //文件分配表占用
                    fileAllocateTable[i] = SizeEnum.END_BLOCKS_LABEL.getCode();
                    //开头五个盘块(文件分配表占用4个+根目录占用1个)不给使用
                    blocksStatus[i] = 0;
                }else{
                    fileAllocateTable[i] = SizeEnum.AVAILABLE_BLOCKS.getCode();
                    blocksStatus[i] = SizeEnum.BLOCKS_SIZE.getCode();
                }
                fileAllocateTableArray[i/SizeEnum.BLOCKS_SIZE.getCode()][i%SizeEnum.BLOCKS_SIZE.getCode()] = fileAllocateTable[i];
            }



                File diskFile = new File(diskName.split("/")[1]);
                diskFile.mkdirs();

                //diskBlockList = new ArrayList<>();
                //写满256行
                for (int i = 0; i < SizeEnum.DISK_SIZE.getCode(); i++) {
                    /**
                     * 磁盘的第0~3块装载文件分配表,一共四块;
                     * */
                    if (i<4)    diskBlockList.add(new DiskBlock<int []>(fileAllocateTableArray[i]));

                    /**
                     * 根目录
                     * */
                    else if(i==4)    diskBlockList.add(new DiskBlock<SparrowDirectory>(root));
                    else diskBlockList.add(new DiskBlock());
                }

                //初始化可用盘块
                availableBlocks = SizeEnum.DISK_SIZE.getCode() - 5;
                System.out.println(e.toString());
                //throw e;
        }
        catch (IOException e){
            System.out.println(e.toString());
        }


    }

    //读取磁盘文件内容
    public static Disk getDiskDocument(){
        Disk disk =null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(diskName))) {
            disk = (Disk) ois.readObject();
           // Disk disk = Disk.getDisk();
            /*System.out.println("getDisk --------------------------------------------  : ");
            System.out.print(" ");
            for(int z=0;z<disk.getFileAllocateTable().length;z++){
                System.out.print(z+"   ");
            }
            System.out.println();
            for (int z:disk.getFileAllocateTable()
            ) {
                System.out.print(z + "  ");
            }*/
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return disk;
    }

    //写入磁盘文件内容
    public static void output2DiskDocument(Disk wholeDisk){
//        int label = 0;
//        for (int i=0;i< wholeDisk.getFileAllocateTable().length;i++){
//            if (wholeDisk.getFileAllocateTable()[i]==-1){
//                label++;
//            }
//        }
//        System.out.println("label : "+label);
        //System.out.println("output length1 : "  + wholeDisk.getRoot().getData().size());
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(diskName))) {
            oos.writeObject(wholeDisk);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //获取首个空闲块号
    public int getFirstAvailableBlock(){
        int number = SizeEnum.FILLED_DISK_LABEL.getCode();
        if(availableBlocks==0){
            number = SizeEnum.FILLED_DISK_LABEL.getCode();
        }else {
            int i=0;
            for (i =0;i<SizeEnum.DISK_SIZE.getCode();i++){
                //System.out.println(i+" : "+fileAllocateTable[i]);
                if (fileAllocateTable[i]==SizeEnum.AVAILABLE_BLOCKS.getCode())
                    break;
            }
            number = i;
        }
        //System.out.println("available block is "+number);
        return number;
    }

    //文件盘块申请,一次申请一块
    public int fileAllocate(FileCatalog fileCatalog){
        int blockIndex = 0;

        //没有可分配磁盘块则返回失败
        if (availableBlocks == 0)
            return 0;//返回零代表分配失败
        else if (fileCatalog.getExtensionName()==FileTypeEnum.DIR_LABEL.getCode()){
            //没有扩展名则表示不是文件是目录
            //TODO 目录也需要分配盘块
            return 0;//返回零代表分配失败
        }
        /**
         * 首次适配 First Fit
         * */
        int temp = fileCatalog.getStartIndex();

        while(fileAllocateTable[temp]!=SizeEnum.END_BLOCKS_LABEL.getCode()){

            if (fileAllocateTable[temp]==0){
                //表示是刚创建的空文件
                break;
            }
            temp = fileAllocateTable[temp];
        }
        //System.out.println(temp);
        int i=getFirstAvailableBlock();
        System.out.println(i);
        fileAllocateTable[temp] = i;
        //System.out.println("i : "+i);
        fileAllocateTable[i] = SizeEnum.END_BLOCKS_LABEL.getCode();
//        for (int j = 0; j < fileAllocateTable.length; j++) {
//            System.out.println(j +" " + fileAllocateTable[j]);
//        }
        availableBlocks--;
        //更新文件长度
        fileCatalog.setFileLength(fileCatalog.getFileLength()+1);
        return i;
    }

    public boolean writeFile2Disk(SparrowFile data){
        FileCatalog fileCatalog = data.getFileCatalog();
        int blockIndex = 0;

        if(fileCatalog.getFileLength()<SizeEnum.BLOCKS_SIZE.getCode()){
            writeDisk(fileCatalog.getStartIndex(),data);

            //System.out.println("w2d"+ fileCatalog.getStartIndex());
            fileAllocateTable[fileCatalog.getStartIndex()] = -1;
        }else{
            //System.out.println(data.getData());
            List<SparrowFile> blockFileList = FileTool.decomposeFile(data);
            blockIndex = fileCatalog.getStartIndex();
            fileAllocateTable[blockIndex] = SizeEnum.END_BLOCKS_LABEL.getCode();
            int newIndex = 0;
            for (int i = 1; i <blockFileList.size(); i++) {
                writeDisk(blockIndex, blockFileList.get(i));
                //如果比文件原来的大则申请盘块空间;
                newIndex = Disk.getDisk().getFirstAvailableBlock();
                //System.out.println("newIndex : "+ newIndex);
                fileAllocateTable[blockIndex] = newIndex;
                fileAllocateTable[newIndex] = SizeEnum.END_BLOCKS_LABEL.getCode();
                blockIndex = newIndex;
            }
            //fileAllocateTable[blockIndex] = SizeEnum.END_BLOCKS_LABEL.getCode();
        }

        return false;
    }

    //将目录写入到磁盘中
    public boolean writeDirectory2Disk(SparrowDirectory sparrowDirectory){
        Disk disk = Disk.getDisk();
        FileCatalog fileCatalog = sparrowDirectory.getFileCatalog();
        if (sparrowDirectory.getData().size()<=8){
            //目录项小于8，占用一个盘块
            writeDisk(fileCatalog.getStartIndex(),sparrowDirectory);
            //System.out.println("startIndex : "+fileCatalog.getStartIndex());
            fileAllocateTable[fileCatalog.getStartIndex()]=-1;
        }else{
            //目录项大于8,需要进行拆分
            List<SparrowDirectory>sparrowDirectories = FileTool.decomposeDirectory(sparrowDirectory);
            int blockIndex = fileCatalog.getStartIndex();
            fileAllocateTable[blockIndex] =SizeEnum.END_BLOCKS_LABEL.getCode();
            writeDisk(blockIndex,sparrowDirectories.get(0));
            int newIndex = 0;
            //System.out.println("Disk.writeDirectory2Disk blockIndex "+blockIndex);
            //System.out.println("sparrow Dir size is " + sparrowDirectories.size());
            for (int i=1;i<sparrowDirectories.size();i++){
                newIndex = disk.getFirstAvailableBlock();
                //System.out.println("Disk.getDisk().getFirstAvailableBlock() : "+ Disk.getDisk().getFirstAvailableBlock());
                //System.out.println("blockIndex :"+blockIndex);
                //System.out.println("Disk.writeDirectory2Disk newIndex "+newIndex);
                if (newIndex == SizeEnum.FILLED_DISK_LABEL.getCode()){
                    System.out.println("[error]磁盘已满");
                    return false;
                }
                //写入磁盘
                writeDisk(newIndex,sparrowDirectories.get(i));
                fileAllocateTable[blockIndex] = newIndex;
                fileAllocateTable[newIndex] = SizeEnum.END_BLOCKS_LABEL.getCode();
                blockIndex = newIndex;
            }
        }
        return true;
    }

    public int recyclingBlock(int startIndex){
        int count = 0;
        if (startIndex<3|startIndex==SizeEnum.END_BLOCKS_LABEL.getCode()){
            System.out.println("[RecyclingError]: Invalid Access!");
            return count;
        }
        int temp = startIndex;
        int label;
        while (fileAllocateTable[temp]!=SizeEnum.END_BLOCKS_LABEL.getCode()){
            label = fileAllocateTable[temp];
            fileAllocateTable[temp] = SizeEnum.AVAILABLE_BLOCKS.getCode();
            count++;
            temp = label;
        }
        fileAllocateTable[temp] = SizeEnum.AVAILABLE_BLOCKS.getCode();
        count++;
        availableBlocks+=count;
        return count;
    }

    //文件盘块回收，文件删除后回收全部盘块
    public FileCatalog fileRecycling(FileCatalog fileCatalog){

        if (fileAllocateTable[fileCatalog.getStartIndex()]==0){
            //TODO 抛出异常
            return null;
        }
        recyclingBlock(fileCatalog.getStartIndex());
        return fileCatalog;
    }

    //对指定盘块的内容进行修改
    public boolean writeDisk(int blockIndex,Document data) {
        //一个字符占一个字节，一个盘块有64字节，写入超过64字节则出错
        if (data.getSize()>SizeEnum.BLOCKS_SIZE.getCode()){
            //TODO 此处建议抛异常!
            System.out.println("file writing error !");
            return false;
        }
        if (blockIndex<0||blockIndex>=SizeEnum.DISK_SIZE.getCode()){
            System.out.println("Invalid Access");
            return false;
        }

        DiskBlock<Document> block = new DiskBlock<>();
        block.setData(data);
        block.setOccupiedSize(data.getSize());
        diskBlockList.set(blockIndex,block);
        return true;
    }

    //读取某个文件的所有盘块
    public List<DiskBlock> getGivenDiskBlocks(FileCatalog fileCatalog){
        //StringBuilder strBuilder = new StringBuilder();
        List<DiskBlock> resultDiskBlockList = new ArrayList<>();

        int nextBlock  = fileCatalog.getStartIndex();
        int index = 0;
        int fomerBlock = 0;
        while(nextBlock!=SizeEnum.END_BLOCKS_LABEL.getCode()&&nextBlock<SizeEnum.DISK_SIZE.getCode()){
            fomerBlock = nextBlock;
            resultDiskBlockList.add(diskBlockList.get(nextBlock));
            nextBlock  = fileAllocateTable[nextBlock];
            index++;
            fileCatalog.setReadPointBlock(nextBlock);
            fileCatalog.setReadPointIndex(index);
        }

        /**
         * 读取到文件末端
         * */
        if (nextBlock==SizeEnum.END_BLOCKS_LABEL.getCode()){
            resultDiskBlockList.add(diskBlockList.get(fomerBlock));
            return resultDiskBlockList;
        }

        /**
         * 错误处理
         * */
        if(fileAllocateTable[nextBlock]==SizeEnum.BAD_BLOCKS_LABEL.getCode()){
            System.out.println("磁盘第"+fileAllocateTable[nextBlock]+"块盘块损坏");
        }

        return resultDiskBlockList;

    }

    //读取盘块内容
    public Document getBlockContent(FileCatalog fileCatalog){
        Document resultContent =  null;
        List<DiskBlock> blocks = getGivenDiskBlocks(fileCatalog);
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).getData();
        }
        return  resultContent;
}


    //读取某个盘块内容
    public Document getBlockContent(int index){
        return (Document) diskBlockList.get(index).getData();
    }


    /**
     * get()
     */
    public static Disk getDisk(){
        if (disk==null){
            disk = new Disk();
            if (!FileTool.isDiskFileExist(diskName)){
                output2DiskDocument(disk);//写入Disk文件中
                System.out.println("磁盘初始化成功,成功创建Sparrow.disk");
            }else{
                disk = getDiskDocument();
            }
        }
        return disk;
    }

    public int[] getBlocksStatus() {
        return blocksStatus;
    }

    public int[] getFileAllocateTable() {
        return fileAllocateTable;
    }

    public int getAvailableBlocks() {
        return availableBlocks;
    }

    public String getDiskName() {
        return diskName;
    }

    public SparrowDirectory getRoot() {
        return root;
    }

    public List<DiskBlock> getDiskBlockList() {
        return diskBlockList;
    }
}
