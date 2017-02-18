package ua.cinemabooking.controllers;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.cinemabooking.liqPayApi.LiqPayService;
import ua.cinemabooking.model.Seans;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Kobylyatskyy Alexander
 */
@Controller
@RequestMapping("/api/rest/liqpay")
public class LiqPayRestController {

    private final LiqPayService liqPayService;

    @Autowired
    public LiqPayRestController(LiqPayService liqPayService) {
        this.liqPayService = liqPayService;
    }

    @RequestMapping(value = "/account/getLiqPayParam", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String[] getLiqPayParam(@RequestParam("email") String email, @RequestParam("amount") Integer amount) {

        if (email == null || amount == null) return null;

        Map<String, String> result = liqPayService.liqPayGenerateParamForHtmlForm(email, amount);

        return new String[]{result.get("data"), result.get("signature")};
    }


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