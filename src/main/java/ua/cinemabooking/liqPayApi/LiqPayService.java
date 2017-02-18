package ua.cinemabooking.liqPayApi;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kobylyatskyy Alexander
 */
@Service
public class LiqPayService {

    private final String PUBLIC_KEY = "i29966004221";
    private final String PRIVATE_KEY = "GMQ8uT0sMqU8WRCSrrRXMaskNevyObUusSMxwPhX";

    public Map<String, String> liqPayGenerateParamForHtmlForm(Long orderId, Integer amount) {

        HashMap<String, String> params = new HashMap<>();
        params.put("version", "3");
        params.put("amount", String.valueOf(amount));
        params.put("currency", "UAH");
        params.put("description", "Booking");
        params.put("order_id", String.valueOf(orderId));

        // ToDo here we must put our hosting url
        params.put("server_url", "http://62.80.167.164:8087/callback");
        params.put("public_key", PUBLIC_KEY); // it is public key which we get on the LiqPay web site

        /*
         * This means that we will not receive real money, but it will be like a test transactions
         */
        params.put("sandbox", "1");

        return new LiqPay(PUBLIC_KEY, PRIVATE_KEY).generateData(params);
    }

    public String getPUBLIC_KEY() {
        return PUBLIC_KEY;
    }

    public String getPRIVATE_KEY() {
        return PRIVATE_KEY;
    }
}
