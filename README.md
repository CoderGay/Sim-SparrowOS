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

2020-12-15 更新：

    将文件改为了对象序列化流;
    写文件OUTPUT没问题,读文件INPUT发生错误,未修复

2020-12-16 更新：

    实现了对文件的对象序列化流读写;
    修复了disk文件读写bug    

2020-12-17 更新：

    创建了逻辑文件SparrowFile类;
    更新了模拟磁盘文件写操作
    更新了createFile类
    更新了FileTool类,添加了方法(将文件分解成适合磁盘块装载的大小)



2020-12-20更新：

    完善了FileTool中的“分解目录”、“合并文件”、“合并目录”、“判断文件或者目录是否存在”功能
    完善OpenFile类中“打开文件功能”
    
    修改了"SparrowDirctory"类的data类型为Document抽象类
    增加了界面的树类视图,有待完善
    
2020-12-23更新：

    增加了界面的流动面板显示文件
    完善了界面的树类视图,只需给出完整的逻辑根目录即可显示
    
2020-12-25更新：

    增加了界面的流动面板显示文件被选中动画
    增加了显示文件界面右键菜单栏
    增加了文件右键菜单栏
    增加了界面的导航栏按钮
    增加了文件或文件夹属性的查看   