package com.zyf.bings.bingos;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.zyf.bings.bingos.address.http.AddressStates;
import com.zyf.bings.bingos.utils.CommonUtils;
import com.zyf.bings.bingos_libnet.bean.BingResponse;
import com.zyf.bings.bingos_libnet.utils.GsonFactory;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public static final String JSON = "{\n" +
            "    \"returnCode\": \"0000000\",\n" +
            "    \"returnMsg\": \"成功\",\n" +
            "    \"responseBizData\": {\n" +
            "        \"bannerList\": [\n" +
            "            {\n" +
            "                \"imgUrl\": \"/homeImge/2017080313/1501738771182.jpg\",\n" +
            "                \"num_id\": \"5\",\n" +
            "                \"videoUrl\": \"\",\n" +
            "                \"goods_id\": \"286\",\n" +
            "                \"home_id\": \"54\"\n" +
            "            }\n" +
            "        ]\n" +
            "    },\n" +
            "    \"returnName\": \"SUCCESS\"\n" +
            "}";

    @Test
    public void test() {
        Gson gson = new Gson();
        BingResponse bingResponse = (BingResponse) gson.fromJson(JSON, BingResponse.class);
        System.out.println(bingResponse);
    }

    @Test
    public void testJSon() {
        ArrayList<String> addressIds = new ArrayList<>();
        addressIds.add("127");
        addressIds.add("128");
        Map<String, String> params = new ArrayMap<String, String>();
        params.put(AddressStates.ADDRESSLIST, addressIds.toString());
        params.put(AddressStates.MEMBER_ID, "15869153840");
        params.put(AddressStates.MAC_ID, CommonUtils.getWifiMac());
        System.out.println(GsonFactory.map2Json(params));
    }


    @Test
    public void testJSon2() {
        LinkedList<String> mGoodsIdList = new LinkedList<>();
        mGoodsIdList.add("127");
        mGoodsIdList.add("128");
        Map<String, Object> params = new ArrayMap<String, Object>();
        params.put(AddressStates.ADDRESSLIST, mGoodsIdList.toString());
        params.put(AddressStates.MEMBER_ID, "15869153840");
        params.put(AddressStates.MAC_ID, CommonUtils.getWifiMac());
        System.out.println(GsonFactory.getGson().toJson(params));
    }
}