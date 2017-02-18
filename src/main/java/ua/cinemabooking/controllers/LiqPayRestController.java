package ua.cinemabooking.controllers;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Kobylyatskyy Alexander
 */
@Controller
@RequestMapping("/api/rest/liqpay")
public class LiqPayRestController {



    /**
     * Catch callback from bank
     */
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public ResponseEntity<Void> callback(@RequestParam(value = "signature", required = false, defaultValue = "") String signature,
                         @RequestParam(value = "data", required = false, defaultValue = "") String data,
                         @RequestBody(required = false) String body) throws Exception {

        String json = new String(DatatypeConverter.parseBase64Binary(data));
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jsonObj = (JSONObject) obj;


        /*
         * В jsonObj.get("order_id") будет лежать id заказа. По этому ID нужно найти order в базе, проверить какой статус пришёл от банка, и если всё ОК,
         * то мы переводим order в статус Оплачено и сохраняем его.
         */

        // ЭТО ПРИМЕР
//        externalRepository.transferOnPersonalAccount(Math.round(((Double)jsonObj.get("amount"))*100), ((String) jsonObj.get("order_id")).substring(8), 1, (String) jsonObj.get("liqpay_order_id"), "success");

        return new ResponseEntity<>(HttpStatus.OK);
    }

}