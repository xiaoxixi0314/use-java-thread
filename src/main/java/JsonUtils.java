import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);
    private static final String CATEGORY_JSON_FILE_PATH = "/tmp/yimai/category_json.json";

    private static final String REGION_JOSN_FILE_PATH = "/tmp/yimai/region.json";

    private static CategoryData categories = null;
    private static Map<String, TradeCategoryIdVO> categoryCacheMap = new HashMap<>();


    private static RegionData regionData = null;
    private static Map<String, MatchedRegionVO> MATCHED_REGION_CACHE_MAP = new HashMap<>();

    public  static void main(String[] args) {
        initRegion();
        matchRegionCode("天津市", "天津市", "1111");
//        String target = "企业-生活/咨询服务-网上生活服务平台";
//        TradeCategoryIdVO vo = matchCategory(target);
//        if (!Objects.isNull(vo)) {
//            System.out.println(JSON.toJSONString(vo));
//        } else {
//            System.out.println("没有找到：" + target);
//        }
    }

    private static void matchRegionCode(String province, String city, String area) {
        if ("北京市".equals(province)
                || "天津市".equals(province)
                || "上海市".equals(province)
                || "天津市".equals(province)) {
            province = province.substring(0, 2);
        }

        String cacheKey = province+city+area;
        if (MATCHED_REGION_CACHE_MAP.get(cacheKey) != null) {
            System.out.println(JSON.toJSONString(MATCHED_REGION_CACHE_MAP.get(cacheKey)));
            return;
        }
        MatchedRegionVO matchedRegionVO = null;
        for(RegionVO proRegionVO :regionData.getRegionData()) {
            if (proRegionVO.getName().equals(province)) {
                for (RegionVO cityRegionVO : proRegionVO.getChildren()) {
                    if (cityRegionVO.getName().equals(city)) {
                        for (RegionVO areaRegionVO : cityRegionVO.getChildren()) {
                            if (areaRegionVO.getName().equals(area)) {
                                matchedRegionVO = new MatchedRegionVO();
                                matchedRegionVO.setProvince(proRegionVO.getName());
                                matchedRegionVO.setProvinceCode(proRegionVO.getCode());
                                matchedRegionVO.setCity(cityRegionVO.getName());
                                matchedRegionVO.setCityCode(cityRegionVO.getCode());
                                matchedRegionVO.setArea(cityRegionVO.getName());
                                matchedRegionVO.setAreaCode(cityRegionVO.getCode());
                                MATCHED_REGION_CACHE_MAP.put(cacheKey, matchedRegionVO);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (matchedRegionVO == null) {
            System.out.println("没有匹配到省市区");
        }else {
            System.out.println(JSON.toJSONString(matchedRegionVO));
        }
    }



    private static void initRegion() {
        if (regionData != null) {
            return;
        }
        InputStream is = null;
        try {
            is = new FileInputStream(REGION_JOSN_FILE_PATH);
            regionData = JSON.parseObject(IOUtils.toString(is,"utf8"), RegionData.class);
        } catch (Exception ex) {
            LOGGER.error("读取json文件失败，无法获取到类目信息", ex);
        } finally {
        }
    }





    private static class MatchedRegionVO {
        private String province;
        private String city;
        private String area;
        private String provinceCode;
        private String cityCode;
        private String areaCode;

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(String provinceCode) {
            this.provinceCode = provinceCode;
        }

        public String getCityCode() {
            return cityCode;
        }

        public void setCityCode(String cityCode) {
            this.cityCode = cityCode;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }
    }

    private static class TradeCategoryIdVO {
        private Integer oneTrade;
        private Integer twoTrade;
        private Integer threeTrade;

        public Integer getOneTrade() {
            return oneTrade;
        }

        public void setOneTrade(Integer oneTrade) {
            this.oneTrade = oneTrade;
        }

        public Integer getTwoTrade() {
            return twoTrade;
        }

        public void setTwoTrade(Integer twoTrade) {
            this.twoTrade = twoTrade;
        }

        public Integer getThreeTrade() {
            return threeTrade;
        }

        public void setThreeTrade(Integer threeTrade) {
            this.threeTrade = threeTrade;
        }
    }

    private static class CategoryData {

        List<CategoryVO> categorieData;

        public List<CategoryVO> getCategorieData() {
            return categorieData;
        }

        public void setCategorieData(List<CategoryVO> categorieData) {
            this.categorieData = categorieData;
        }
    }

    private static class CategoryVO {
        private String id;
        private String name;
        private String categoryId;
        List<CategoryVO> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<CategoryVO> getChildren() {
            return children;
        }

        public void setChildren(List<CategoryVO> children) {
            this.children = children;
        }
    }

    private static class RegionData {
        private List<RegionVO> regionData;

        public List<RegionVO> getRegionData() {
            return regionData;
        }

        public void setRegionData(List<RegionVO> regionData) {
            this.regionData = regionData;
        }
    }
    private static class RegionVO {
        private String code;

        private String name;

        private String parentCode;

        List<RegionVO> children;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParentCode() {
            return parentCode;
        }

        public void setParentCode(String parentCode) {
            this.parentCode = parentCode;
        }

        public List<RegionVO> getChildren() {
            return children;
        }

        public void setChildren(List<RegionVO> children) {
            this.children = children;
        }
    }
}
