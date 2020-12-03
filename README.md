# Sim-SparrowOS
OS Simulation Program

2020-10-26 更新:

    更新了进程控制模块的一些类;



2020-11-2 更新：

    添加了内存管理的相关类
    完成内存初始化的工作
    
2020-11-21 更新：

    在filemanage包创建文件管理有关类
    在Disk类中实现磁盘初始化、盘块申请、盘块回收、修改指定盘块内容
    完善文件目录类
    修改部分bug,读取文件有待完善.

2020-12-03 更新：

    在filemanage包创建了打开文件类OpenFile,并以单例模式设计该类;
    更新了枚举类ModeEnum;
    设计并整理了打开文件openFile操作的逻辑设计,ReadFile以及WriteFile都必须检索‘已打开文件表’中该文件是否存在；    
   