import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class GsonUtils {

        public static String toJsonString(Object obj){
            return GsonFactory.getGson().toJson(obj);
        }

        public static <T> T toBean(String json, Class<T> clazz) {
            if (null == json) {
                return null;
            }
            return GsonFactory.getGson().fromJson(json, clazz);
        }

        public static <T> T toBean(String json, Type type) {
            if (null == json) {
                return null;
            }
            return GsonFactory.getGson().fromJson(json, type);
        }

        public static void main(String[] args) {
            Gson gson = new Gson();
            String json = "['http://hello.com']";
//            List<String> persons =GsonUtils.toBean(json, new TypeToken<List<String>>() {}.getType());
            List<String> persons =gson.fromJson(json, new TypeToken<List<Object>>() {}.getType());

            System.out.println(persons.size());
        }
}
