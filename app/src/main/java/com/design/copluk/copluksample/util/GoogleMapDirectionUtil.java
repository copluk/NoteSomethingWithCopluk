package com.design.copluk.copluksample.util;

import android.content.Context;

import com.design.copluk.copluksample.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by copluk on 2018/5/21.
 */

public class GoogleMapDirectionUtil {
    public static String MODE_DRIVING = "driving";
    public static String MODE_WALKING = "walking";
    public static String MODE_BICYCLING = "bicycling";
    public static String MODE_TRANSIT = "transit";

    public static String LANGUAGE_TW = "zh-TW";
    public static String LANGUAGE_CN = "zh-CN";
    public static String LANGUAGE_ENG = "en";
    public static String LANGUAGE_JP = "ja";


    /**
     * document       -> https://developers.google.com/maps/documentation/directions/intro?hl=zh-tw
     * about language -> https://developers.google.com/maps/faq?hl=zh-tw#languagesupport
     *
     * @param origin 起點
     * @param destination 終點
     * @param mode 移動方式
     * @param alternatives 是否支援多筆
     * @param language 語言
     *
     * 其實你還可以設...
     * arrival_time  針對大眾運輸路線規劃指定想要的抵達時間（秒）
     * departure_time 指定想要的出發時間
     * 之類的
     *
     * 還有幾項請去看document好嗎
     */
    public static String directionSetting(Context context, String origin, String destination,
                                          String mode, boolean alternatives, String language) {
        String[] strings = new String[]
                {
                        "origin=", origin,
                        "&destination=", destination,
                        "&mode=", mode,
                        "&alternatives=", String.valueOf(alternatives),
                        "&language" , language,
                        "&key=", context.getString(R.string.google_directions_key)
                };

        StringBuilder directionUrl = new StringBuilder("https://maps.googleapis.com/maps/api/directions/json?");
        for (String s : strings)
            directionUrl.append(s);

        return directionUrl.toString();
    }

    public static List<LatLng> decodePolyLines(String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d,
                    lng / 100000d
            ));
        }
        return decoded;
    }
}
