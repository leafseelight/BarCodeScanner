package barcode;

import java.util.HashMap;
import java.util.Map;


/**
 *扫码枪模拟的键盘按钮事件监听（0-9键和回车键）
 * 关键算法：条形码扫描器在很短的时间内输入了至少 barcodeMinLength 个字符以上信息，并且以“回车”作为结束字符，并且一次扫描要在 maxScanTime 毫秒内完成
 * 字符数及扫描时间可根据具体情况设置
 * @author ysc
 */
public class BarcodeKeyboardListener{
    //条形码数据缓充区
    private StringBuilder barcode;
    //扫描开始时间
    private long start;
    private Map<Integer,Integer> keyToLetter=new HashMap<Integer,Integer>();
    //一次扫描的最长时间
    private static int maxScanTime=300;
    //条形码的最短长度
    private static int barcodeMinLength=6;

    /**
     * 初始键盘代码和字母的对于关系
     */
    public BarcodeKeyboardListener(){
        keyToLetter.put(48,0);
        keyToLetter.put(49,1);
        keyToLetter.put(50,2);
        keyToLetter.put(51,3);
        keyToLetter.put(52,4);
        keyToLetter.put(53,5);
        keyToLetter.put(54,6);
        keyToLetter.put(55,7);
        keyToLetter.put(56,8);
        keyToLetter.put(57,9);
    }
    /**
     * 此方法响应扫描枪事件
     * @param keyCode 
     */
    public void onKey(int keyCode) {
        //获取输入的是那个数字
        Integer letter=keyToLetter.get(keyCode);
        if(barcode==null){
            //开始进入扫描状态
            barcode=new StringBuilder();
            //记录开始扫描时间
            start=System.currentTimeMillis();
        }
        //需要判断时间
        long cost=System.currentTimeMillis()-start;
        if(cost > maxScanTime){
             //开始进入扫描状态
            barcode=new StringBuilder();
            //记录开始扫描时间
            start=System.currentTimeMillis();
        }
        //数字键0-9
        if (keyCode >= 48 && keyCode <= 57) {
            barcode.append(letter);
        }
        //回车键
        if (keyCode == 13) {
            //条形码扫描器在很短的时间内输入了至少 barcodeMinLength 个字符以上信息，并且以“回车”作为结束字符

            //进入这个方法表示是“回车”
            //那么判断回车之前输入的字符数，至少 barcodeMinLength 个字符
            //并且一次扫描要在 maxScanTime 毫秒内完成
            if(barcode.length() >= barcodeMinLength && cost < maxScanTime){
                cost=System.currentTimeMillis()-start;
                System.out.println("耗时："+cost);
                System.out.println(barcode.toString());
                //将数据加入缓存阻塞队列
                BarcodeBuffer.product(barcode.toString());
            }

            //清空原来的缓冲区
            barcode=new StringBuilder();
        }
    }
}
