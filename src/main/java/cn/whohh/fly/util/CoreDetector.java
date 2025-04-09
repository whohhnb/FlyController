package cn.whohh.fly.util;

public class CoreDetector {
    private static final boolean IS_FOLIA;
    
    static {
        boolean foliaDetected;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            foliaDetected = true;
        } catch (ClassNotFoundException e) {
            foliaDetected = false;
        }
        IS_FOLIA = foliaDetected;
    }
    
    public static boolean isFolia() {
        return IS_FOLIA;
    }
}