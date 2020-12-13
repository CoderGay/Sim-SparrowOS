package enums;

/**
 * Create By  @林俊杰
 * 2020/12/13 21:04
 *
 * @version 1.0
 */
public enum DiskLabelEnum {

    DIR_DIVIDE_LABEL(",","目录项内容中的文件或目录的分隔符"),

    ;

    private String label;
    private String message;

    DiskLabelEnum(String label,String message){
        this.label = label;
        this.message = message;
    }

    public String getLabel(){
        return label;
    }

    public String getMessage(){
        return message;
    }
}
