/*
Copyright 2016 Focus Technology, Co., Ltd. All rights reserved.
 */

package example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MiaoQiang
 * @date 2018/6/24.
 */
@Controller
@RequestMapping("/home")
public class IndexController {

    @RequestMapping("/index")
    public String index() {
        return "index";
    }
}
