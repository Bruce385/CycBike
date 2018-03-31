package cyc3253.xmlg.cycbike.callback;

public class AllInterface {

    /**
     * 菜单滑动事件监听.
     */
    public  interface OnMenuSlideListener{
        void onMenuSlide(float offset);
    }

    /**
     * 单车解锁监听器.
     */
    public  interface IUnlock{
        void onUnlock();
    }

    /**
     * 单车位移事件监听.
     */
    public  interface IUpdateLocation{
        void updateLocation(String totalTime, String totalDistance);
        void endLocation();
    }


}
