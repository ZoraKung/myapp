package com.demo.idea16.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by GHL on 2016/10/17.
 */
@Controller
@RequestMapping(value = "/test")
public class MainController {
    @RequestMapping(value = "/index")
    public String index(Model model) {
        String i = "ooo";
        model.addAttribute("i", i);
        return "home";
    }
//        return "{\n" +
//                "  \"status\": \"ok\",\n" +
//                "  \"error\": \"error message\",\n" +
//                "  \"usr_id\": \"Requested learner login id\"" +
////                    ",\n" +
////                    "  \"data\": [\n" +
////                    "    {\n" +
////                    "      \"usr_id\": \"learner login id\",\n" +
////                    "      \"update_date\": \"record update date\"\n" +
////                    "    },\n" +
////                    "    {\n" +
////                    "      \"usr_id\": \"learner login id\",\n" +
////                    "      \"update_date\": \"record update date\"\n" +
////                    "    }\n" +
////                    "  ]\n" +
//                "}\n";
//    }
}
