package barcode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *扫描服务监听，把此监听器配置到web.xml中
 * 
    <listener>
        <listener-class>barcode.ScanBarcodeListener</listener-class>
    </listener>
 * @author ysc
 */
public class ScanBarcodeListener  implements ServletContextListener{
    private BarcodeProducter barcodeProducter;
    private BarcodeConsumer barcodeConsumer;
    
    /**
     * tomcat启动
     * @param sce 
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        barcodeProducter=new BarcodeProducter();
        barcodeProducter.startProduct();
        barcodeConsumer=new BarcodeConsumer();
        barcodeConsumer.startConsume();
    }
    /**
     * tomcat关闭
     * @param sce 
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        barcodeProducter.stopProduct();
        barcodeConsumer.stopConsume();
    }
    /**
     * 可以在此文件中运行测试
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        BarcodeProducter producter=new BarcodeProducter();
        BarcodeConsumer consumer=new BarcodeConsumer();
        
        producter.startProduct();
        consumer.startConsume();
        
        BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("输入 '<exit>' 退出程序");
        String line=reader.readLine();
        while(line!=null){
            if("exit".equals(line)){
                producter.stopProduct();
                consumer.stopConsume();
                System.exit(0);
            }
            line=reader.readLine();
        }
    }
}
