import org.apache.commons.lang3.time.DateUtils;

import java.math.BigDecimal;
import java.util.*;

public class TagUtils {

    public static boolean hasTag(Long userTag, long bit) {
        if (userTag == null) {
            return false;
        } else {
            return (userTag & bit) > 0L;
        }
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
    public static void main(String[] ags) {

        Long money = new BigDecimal("1.000").multiply(new BigDecimal("2000")).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
        System.out.println(money);
    }
}
