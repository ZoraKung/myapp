package com.demo.idea16.web;

import com.demo.idea16.service.TestService;
import com.demo.idea16.vo.TestVo;
import com.wjxinfo.core.base.utils.json.ObjectMapper;
import com.wjxinfo.core.base.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by GHL on 2016/10/17.
 */
@Controller
@RequestMapping(value = "/test")
public class MainController extends BaseController {

    @Autowired
    @Qualifier("testService")
    private TestService testService;

    @RequestMapping(value = "/index")
    public String index(Model model) {
        String i = "ooo";
        model.addAttribute("i", i);
        return "home";
    }

    @ResponseBody
    @RequestMapping(value = "/find/{id}")
    public String find(@PathVariable String id) {
        TestVo vo = testService.findTestVoById(id);
        return ObjectMapper.writeJsonString(vo);
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
