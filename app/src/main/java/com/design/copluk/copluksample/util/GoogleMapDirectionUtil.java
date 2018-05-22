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
     * 使用限制        -> https://developers.google.com/maps/documentation/directions/usage-limits?hl=zh-tw
     *                -> https://developers.google.com/maps/pricing-and-plans/?hl=zh-tw
     * 懶人包 免費一天2500次使用，付費一天100,000，收費標準為超過2500，每1000筆收0.5美元
     *
     * document       -> https://developers.google.com/maps/documentation/directions/intro?hl=zh-tw
     * about language -> https://developers.google.com/maps/faq?hl=zh-tw#languagesupport
     *
     * @param origin        起點
     * @param destination   終點
     * @param mode          移動方式 預設為開車，可設定開車、步行、單車、大眾交通
     * @param alternatives  是否支援多筆路線
     * @param language      使用語言
     *
     * 其實你還可以設...
     * waypoints        指定途經地點的陣列。
     *                      緯度/經度座標 :
     *                      編碼折線 : (enc:CODE:)
     *                      地點 ID : (place_id:)
     * arrival_time     針對大眾運輸路線規劃指定想要的抵達時間（秒）
     * departure_time   指定想要的出發時間
     * region           指定地區代碼 避免地點在兩國間
     * transit_mode     偏好的大眾運輸方式 bus、subway、train、tram、
     *                  rail (等於 transit_mode=train|tram|subway )
     * 之類的
     *
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
