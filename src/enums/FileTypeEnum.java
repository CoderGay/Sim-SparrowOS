package enums;

/**
 * @author WenZhikun
 * @data 2020-11-21 14:23
 */
public enum FileTypeEnum {
    DIR_LABEL(0,"目录标志"),

    EXE_FILE(1,"exe可执行文件"),

    TXT_FILE(2,"txt文本文件"),

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
