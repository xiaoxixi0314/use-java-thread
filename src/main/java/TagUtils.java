import exception.ParamException;
import exception.Query;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class TagUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagUtils.class);

    public static boolean hasTag(Long userTag, long bit) {
        if (userTag == null) {
            return false;
        } else {
            return (userTag & bit) > 0L;
        }
    }

    public static Long addTag(Long userTag, long bit) {
        if (userTag == null) {
            userTag = 0L;
        }
        return userTag | bit;
    }


    public static long transSalePriceFromMallSalePrice(Long mallSalePrice, String saleUnit, Long mallSkuParam2) {
        if (mallSkuParam2 == null) {
            return mallSalePrice;
        } else {
            BigDecimal tempMallSalePrice = new BigDecimal(mallSalePrice == null ? 0L : mallSalePrice);
            if (Objects.equals(saleUnit, "kg")) {
                //kg转换成g
                return tempMallSalePrice
                        .divide(new BigDecimal(mallSkuParam2).divide(new BigDecimal("1000")).setScale(3,BigDecimal.ROUND_HALF_UP))
                        .setScale(3, BigDecimal.ROUND_HALF_UP).longValue();

            } else if (Objects.equals(saleUnit, "斤")) {
                //斤转换成g
                return tempMallSalePrice
                        .divide(new BigDecimal(mallSkuParam2).divide(new BigDecimal("500")).setScale(3,BigDecimal.ROUND_HALF_UP))
                        .setScale(3, BigDecimal.ROUND_HALF_UP).longValue();
            } else {
                return tempMallSalePrice
                        .divide(new BigDecimal(mallSkuParam2))
                        .setScale(3 , BigDecimal.ROUND_HALF_UP).longValue();
            }
        }
    }


    public static BigDecimal transSaleCountFromMallSaleCount(BigDecimal mallSaleCount, String saleUnit, Long mallSkuParam2) {
        if (mallSkuParam2 == null) {
            return mallSaleCount;
        } else {
            BigDecimal tempMallSaleCount = mallSaleCount == null ? BigDecimal.ZERO : mallSaleCount;

            if (Objects.equals(saleUnit, "kg")) {
                //kg转换成g
                return tempMallSaleCount
                        .multiply(new BigDecimal(mallSkuParam2).divide(new BigDecimal("1000")).setScale(3, BigDecimal.ROUND_HALF_UP))
                        .setScale(3, BigDecimal.ROUND_HALF_UP);

            } else if (Objects.equals(saleUnit, "斤")) {
                //斤转换成g
                return tempMallSaleCount
                        .multiply(new BigDecimal(mallSkuParam2).divide(new BigDecimal("500")).setScale(3, BigDecimal.ROUND_HALF_UP))
                        .setScale(3, BigDecimal.ROUND_HALF_UP);
            } else {
                return tempMallSaleCount.multiply(new BigDecimal(mallSkuParam2));
            }
        }
    }

    public static Date calcExceptDeliveryTime4NextDay(Date orderTime) {
        return DateUtils.truncate(DateUtils.addDays(orderTime, 1), Calendar.DAY_OF_MONTH);
    }

    private static String convertImage2Base64(String imagePath) {
        try {
            HttpURLConnection conn = null;
            ByteArrayOutputStream outPut = null;
            InputStream inStream = null;
            byte[] data = new byte[1024];
            try {
                // 创建URL
                URL url = new URL("https://pic.daily.heyean.com" + imagePath);
                // 创建链接
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(10 * 1000);

                if(conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "error";
                }
                inStream = conn.getInputStream();
                outPut = new ByteArrayOutputStream();
                int len = -1;
                while ((len = inStream.read(data)) != -1) {
                    outPut.write(data, 0, len);
                }
            } catch (IOException e) {
                LOGGER.error("获取图片base64失败", e);
            } finally {
                if (outPut != null) {
                    outPut.close();
                }
                if (inStream != null) {
                    inStream.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
            // 对字节数组Base64编码
            return "data:image/jpeg;base64," + new BASE64Encoder().encode(outPut.toByteArray()).replaceAll("\\r\\n|\\r|\\n", "");
        } catch (Exception e) {
            LOGGER.error("获取图片base64失败", e);
            return "error";
        }

    }
    public static void main(String[] ags) {

       BigDecimal ss =  new BigDecimal(1)
                .divide(new BigDecimal(3), 1, RoundingMode.HALF_UP);
        System.out.println(ss.toString());


        System.out.println(DateFormatUtils.format(new Date(), "MM-dd HH:mm"));
        System.out.println(DateFormatUtils.format(new Date(), "MM-dd 24:00"));

    }

    public static  File base64ToFile(String imageContent) {
        String destPath = "/tmp";
        String fileName = "_" + System.currentTimeMillis() + ".jpg";

        File file = null;
        //创建文件目录
        String filePath = destPath + "/" + fileName;
        File dir = new File(destPath);
        if (!dir.exists() && !dir.isDirectory()) {
            dir.mkdirs();
        }
        BufferedOutputStream bos = null;
        java.io.FileOutputStream fos = null;
        try {
            byte[] bytes = Base64.getDecoder().decode(imageContent);
            file = new File(filePath);
            fos = new java.io.FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            LOGGER.error("BaiduOcrManagerImpl.base64ToFile error:{}", e);
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
        return file;
    }
}
