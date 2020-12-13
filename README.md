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
  
2020-12-13 更新：

    修改了Disk类的初始化以及读写文件函数,但仍不完善,建议进一步将字符流文件读写转换为对象流文件读写
    重新构思了文件检索步骤:
            1. 打开根目录下的盘块，读取硬盘中的块内容；
            2. 逐一比较根目录下的“目录名或文件名”；
            3. 三条分支：
               - 没找到，报告未找到
               - 找到：
                 - 是文件，执行打开文件操作，结束；
                 - 是目录，执行第4步
            4. 打开该目录的所在盘块，读取硬盘中的块内容；
            5. 重复第2步。
    需要对象流文件才能在从硬盘读取的内容中获取对应的FileCatalog对象(该目录下的文件或子目录),否则仅能判断该目录是否合法(即返回 true or false)而已;
    添加了一个工具类FileNameTool,并未完成
          
      