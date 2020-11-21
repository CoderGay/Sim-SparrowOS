package filemanager;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import enums.SizeEnum;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-21 8:57
 */
public class Disk {
    //磁盘，共有256盘快，每盘快大小为64B
    //采用链式分配方式，文件分配表每个项占1B，共128B，占用前4个盘快(0,1,2,3)

    //文件分配表（链式分配中盘块索引）,长度256
    //-1表示为文件最后一块，0代表可分配，其它代表已占用
    private int[] fileAllocateTable= new int[SizeEnum.DISK_SIZE.getCode()];

    //每个盘快使用情况,未使用时候是64字节（值为64）
    private int[] blocksStatus = new int[SizeEnum.DISK_SIZE.getCode()];

    //可用盘块个数
    private int availableBlocks;

    //设计成单例模式
    private static Disk disk = null;

    private Disk(){
        //初始化文件分配表和盘块
        for (int i = 0; i< SizeEnum.DISK_SIZE.getCode(); i++){
            if (i<2){
                //文件分配表占用
                fileAllocateTable[i] = SizeEnum.END_BLOCKS_LABEL.getCode();
                //开头两个盘块不给使用
                blocksStatus[i] = 0;
            }else{
                fileAllocateTable[i] = SizeEnum.AVALIABLE_BLOCKS.getCode();
                blocksStatus[i] = SizeEnum.BLOCKS_SIZE.getCode();
            }
        }
        //使用disk.txt作为磁盘文件
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("disk.txt"));
            //写满256行
            for (int i = 0; i < 256; i++) {
                bufferedWriter.write("\n");
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }

        //初始化可用盘块
        availableBlocks = SizeEnum.DISK_SIZE.getCode() - 4;
    }

    //文件盘块申请,一次申请一块
    public boolean fileAllocate(FileCatalog fileCatalog){
        //没有可分配磁盘块则返回失败
        if (availableBlocks == 0)
            return false;
        else if (fileCatalog.getExtensionName()==0){
            //没有扩展名则表示不是文件是目录
            return false;
        }
        //找到末尾的盘块号，再插入可用盘块
        int temp = fileCatalog.getStartIndex();
        while(fileAllocateTable[temp]!=SizeEnum.END_BLOCKS_LABEL.getCode()){
            temp = fileAllocateTable[temp];
        }
        int i=0;
        for (i =0;i<SizeEnum.DISK_SIZE.getCode();i++){
            if (fileAllocateTable[i]==SizeEnum.AVALIABLE_BLOCKS.getCode())
                break;
        }
        fileAllocateTable[temp] = i;
        fileAllocateTable[i] = SizeEnum.END_BLOCKS_LABEL.getCode();
        availableBlocks--;
        //更新文件长度
        fileCatalog.setFileLength(fileCatalog.getFileLength()+1);
        return true;
    }

    //文件盘块回收，文件删除后回收全部盘块
    public FileCatalog fileRecycling(FileCatalog fileCatalog){

        if (fileAllocateTable[fileCatalog.getStartIndex()]==0){
            //TODO 抛出异常
            return null;
        }
        int count = 0,temp = fileCatalog.getStartIndex() , label;
        while (fileAllocateTable[temp]!=SizeEnum.END_BLOCKS_LABEL.getCode()){
            label = fileAllocateTable[temp];
            fileAllocateTable[temp] = SizeEnum.AVALIABLE_BLOCKS.getCode();
            count++;
            temp = label;
        }
        fileAllocateTable[temp] = SizeEnum.AVALIABLE_BLOCKS.getCode();
        count++;
        availableBlocks+=count;
        return fileCatalog;
    }

    //对指定盘块的内容进行修改
    public boolean writeDisk(int blockIndex,String message){
        //一个字符占一个字节，一个盘块有64字节，写入超过64字节则出错
        if (message.length()>64){
            //TODO 此处建议抛异常!
            System.out.println("file writing error !");
            return false;
        }
        String str = "";
        //先对文件内容读入缓冲区，并进行修改
        try{
            BufferedReader bufferedReader = new BufferedReader(new FileReader("disk.txt"));
            int i=0;
            while (bufferedReader.ready()){
                if (i==blockIndex){
                    str+=message+"\n";
                }else{
                    str+=bufferedReader.readLine()+"\n";
                }
                i++;
            }
            bufferedReader.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
        //将修改过后的文件disk内容写回去disk.txt
        try{
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("disk.txt"));
            bufferedWriter.write(str);
            bufferedWriter.flush();
            bufferedWriter.close();
        }catch (IOException e){
            System.out.println(e.toString());
        }
        return true;
    }

    /**
     * get()
     */
    public static Disk getDisk() {
        if (disk==null){
            disk = new Disk();
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
}
