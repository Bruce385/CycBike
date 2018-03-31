package cyc3253.xmlg.cycbike.tools;

import com.baidu.mapapi.model.LatLng;


public class LocationManager {

    private LatLng currentLL;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getCurrentLL() {
        return currentLL;
    }

    public void setCurrentLL(LatLng currentLL) {
        this.currentLL = currentLL;
    }

    public static LocationManager getInstance() {
        return SingletonFactory.singletonInstance;
    }

    private static class SingletonFactory {
        private static LocationManager singletonInstance = new LocationManager();
    }
}
