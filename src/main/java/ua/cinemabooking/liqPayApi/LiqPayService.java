package ua.cinemabooking.liqPayApi;

import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kobylyatskyy Alexander
 */
@Service
public class LiqPayService {


    public Map<String, String> liqPayGenerateParamForHtmlForm(String email, Integer amount, String seatId) {
        HashMap params = new HashMap();
        params.put("version", "3");
        params.put("amount", amount);
        params.put("currency", "UAH");
        try {
            params.put("description", new String("Пополнение баланса".getBytes("UTF-8"), "cp1251"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        params.put("order_id", seatId);

        // ToDo here we must put our hosting url
        params.put("server_url", "http://62.80.167.164:8087/callback");
        params.put("public_key", "i29966004221"); // it is public key which we get on the LiqPay web site

        /*
         * This means that we will not receive real money, but it will be like a test transactions
         */
        params.put("sandbox", "1");

        return new LiqPay("i29966004221", "GMQ8uT0sMqU8WRCSrrRXMaskNevyObUusSMxwPhX").generateData(params);
    }

}
