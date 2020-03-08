import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonFactory {

    private static class GsonHolder {
//        private static Type tradeInfoListType = new TypeToken<List<TradeInfo>>(){}.getType();

//        private static ExclusionStrategy caibaoExclusionStrategy = new ExclusionStrategy() {
//            @Override
//            public boolean shouldSkipField(FieldAttributes f) {
//                return f.getName().startsWith("caibao");
//            }
//
//            @Override
//            public boolean shouldSkipClass(Class<?> clazz) {
//                return false;
//            }
//        };

        private static Gson gson = new GsonBuilder()
                .create();
    }

    public static Gson getGson() {
        return GsonHolder.gson;
    }
}
