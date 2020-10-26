package process;

/**
 * Create By  @林俊杰
 * 2020/10/26 21:42
 *
 * @version 1.0
 */
public interface Register {
    public void write();

    public <T> T read();
}
