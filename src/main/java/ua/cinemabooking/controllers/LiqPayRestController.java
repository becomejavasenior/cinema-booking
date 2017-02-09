package ua.cinemabooking.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.cinemabooking.liqPayApi.LiqPayService;

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


    @RequestMapping(value = "/account/getLiqPayParam", method = RequestMethod.POST)
    @ResponseBody
    public String[] getLiqPayParam(@RequestParam("email") String email, @RequestParam("amount") Integer amount, @RequestParam("seatId") String seatId) {

        Map<String, String> result = liqPayService.liqPayGenerateParamForHtmlForm(email, amount, seatId);
        return new String[]{result.get("data"), result.get("signature")};
    }

}