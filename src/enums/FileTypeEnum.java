package enums;

/**
 * @author WenZhikun
 * @data 2020-11-21 14:23
 */
public enum FileTypeEnum {
    DIR_LABEL(0,"目录标志"),

    EXE_FILE(1,"exe可执行文件"),

    TXT_FILE(2,"txt文本文件"),

    ELSE_FILE(404,"其他文件"),

    DIR_LOCAL(4,"目录属性位"),

    FILE_LOCL(5,"文件属性位"),

    SYSTEM_LOCAL(6,"系统文件位"),

    READONLY_LOCAL(7,"只读文件位"),

    COMMON_DIR(00001000,"普通目录"),

    COMMON_FILE(00000100,"普通文件"),

    SYS_FILE(00000110,"系统文件"),

    SYS_DIR(00001010,"系统目录"),

    READ_ONLY_FILE(00000101,"普通只读文件"),

    READ_ONLY_DIR(00001001,"只读目录"),

    READ_ONLY_SYS_FILE(00000111,"只读系统文件"),

    READ_ONLY_SYS_DIR(00001011,"只读系统目录"),



    ;

    private int code;
    private String message;

    FileTypeEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
