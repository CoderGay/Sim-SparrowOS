package memory;

import com.sun.glass.ui.Size;
import enums.ModeEnum;
import enums.SizeEnum;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author WenZhikun
 * @data 2020-11-02 19:28
 */
public class MainMemory {

    //系统区
    private List<Integer> systemBlock;
    //用户区
    private List<Integer> userBlock;
    //内存分配表
    private List<AllocateTableNode> allocateTable;
    //总可用空间
    private int totalSize;
    //系统区可用空间
    private int sysSize;
    //用户区可用空间
    private int userSize;

    //用户区开始的块号
    private int userBlockIndex;


    public MainMemory() {
        systemBlock=new ArrayList<>(SizeEnum.SYS_MEMORY_SIZE.getCode());
        userBlock=new ArrayList<>(SizeEnum.USER_MEMORY_SIZE.getCode());
        //allocateTable = new LinkedList<>(new ArrayList<Integer>(SizeEnum.SYS_MEMORY_SIZE.getCode()+ SizeEnum.USER_MEMORY_SIZE.getCode()));
        allocateTable = new LinkedList<>();

        //初始时 分配好一整块空间
        for (int i = 0; i < SizeEnum.SYS_MEMORY_SIZE.getCode(); i++) {
            systemBlock.set(i,0);
        }
        for (int i = 0; i < SizeEnum.USER_MEMORY_SIZE.getCode(); i++) {
            userBlock.set(i,0);

        }

//        AllocateTableNode allocateTableNode_0 = new AllocateTableNode(0,SizeEnum.ALLOCATE_TABLE_MEMORY_SIZE.getCode());
//        allocateTableNode_0.setAllocated(true);


        allocateTable.add(new AllocateTableNode(0,SizeEnum.USER_MEMORY_SIZE.getCode()+SizeEnum.SYS_MEMORY_SIZE.getCode()));

        allocate(SizeEnum.ALLOCATE_TABLE_MEMORY_SIZE.getCode(), ModeEnum.ALLOCATE_SYS_BLOCK.getCode());
        allocate(SizeEnum.RESIDENT_MONITOR_SIZE.getCode(), ModeEnum.ALLOCATE_SYS_BLOCK.getCode());

        //总可用空间 = 总内存大小 - 内存分配表占用空间大小
        totalSize = SizeEnum.SYS_MEMORY_SIZE.getCode() + SizeEnum.USER_MEMORY_SIZE.getCode() - SizeEnum.ALLOCATE_TABLE_MEMORY_SIZE.getCode();
        //系统可用空间 = 系统区空间大小 - 内存分配表空间大小
        sysSize = SizeEnum.SYS_MEMORY_SIZE.getCode() - SizeEnum.ALLOCATE_TABLE_MEMORY_SIZE.getCode();
        userSize = SizeEnum.USER_MEMORY_SIZE.getCode();
    }

    //申请内存空间，采用首次适配算法
    public boolean allocate(int size,int mode){
        return firstFit(size,mode);
    }

    public boolean firstFit(int size,int mode){
        boolean flag = false;
        if (mode == ModeEnum.ALLOCATE_USER_BLOCK.getCode()){
            for (int i = userBlockIndex; i < allocateTable.size(); i++) {
                AllocateTableNode node = allocateTable.get(i);
                if (node.isAllocated()){
                    if(node.getNodeLength()>=size){
                        flag = true;
                        userSize -= size;
                        totalSize -= size;


                        int oldNodeSize = node.getNodeLength();
                        node.setNodeLength(size);
                        node.setAllocated(true);
                        //int oldNodeEndIndex = node.getEndIndex();
                        node.setEndIndex(node.getStartIndex()+size);

                        for (int j = node.getStartIndex(); j <= node.getEndIndex(); j++) {
                            userBlock.set(j-SizeEnum.SYS_MEMORY_SIZE.getCode(),1);
                        }

                        if(oldNodeSize > size){
                            AllocateTableNode newNode = new AllocateTableNode(node.getEndIndex()+1,oldNodeSize-size);

                            allocateTable.add(i+1,newNode);
                        }
                        break;
                    }
                }
            }
        }else if(mode == ModeEnum.ALLOCATE_SYS_BLOCK.getCode()){

            for (int i = 0; i < userBlockIndex; i++) {
                AllocateTableNode node = allocateTable.get(i);
                if (node.isAllocated()){
                    if(node.getNodeLength()>=size){
                        flag = true;

                        sysSize -= size;
                        totalSize-=size;
                        int oldNodeSize = node.getNodeLength();
                        node.setNodeLength(size);
                        node.setAllocated(true);
                        //int oldNodeEndIndex = node.getEndIndex();
                        node.setEndIndex(node.getStartIndex()+size);

                        for (int j = 0; j <= node.getEndIndex(); j++) {
                            systemBlock.set(j,1);
                        }
                        if(oldNodeSize > size){
                            AllocateTableNode newNode = new AllocateTableNode(node.getEndIndex()+1,oldNodeSize-size);

                            allocateTable.add(i+1,newNode);

                            //维护 用户区 块起始块号
                            userBlockIndex++;
                        }
                        break;
                    }
                }
            }
        }else{
            //TODO 抛出功能号异常
        }
        return flag;
    }

    /**
     * Getter & Setter
     * */
    public List<Integer> getSystemBlock() {
        return systemBlock;
    }

    public void setSystemBlock(List<Integer> systemBlock) {
        this.systemBlock = systemBlock;
    }

    public List<Integer> getUserBlock() {
        return userBlock;
    }

    public void setUserBlock(List<Integer> userBlock) {
        this.userBlock = userBlock;
    }

    public List<AllocateTableNode> getAllocateTable() {
        return allocateTable;
    }

    public void setAllocateTable(List<AllocateTableNode> allocateTable) {
        this.allocateTable = allocateTable;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public int getSysSize() {
        return sysSize;
    }

    public void setSysSize(int sysSize) {
        this.sysSize = sysSize;
    }

    public int getUserSize() {
        return userSize;
    }

    public void setUserSize(int userSize) {
        this.userSize = userSize;
    }

    public int getUserBlockIndex() {
        return userBlockIndex;
    }

    public void setUserBlockIndex(int userBlockIndex) {
        this.userBlockIndex = userBlockIndex;
    }
}
