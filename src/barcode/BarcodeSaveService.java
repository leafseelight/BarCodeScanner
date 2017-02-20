package barcode;

/**
 *条形码保存服务
 * @author ysc
 */
public interface BarcodeSaveService {
    /**
     * 保存条形码
     * @param barcode 
     */
    public void save(String barcode);
    /**
     * 在这里释放资源，如数据库连接，关闭文件，关闭网络连接等
     */
    public void finish();
}
