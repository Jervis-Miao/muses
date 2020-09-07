/*
 * Copyright All rights reserved.
 */

package cn.muses.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.muses.utils.DownloadUtils;
import cn.muses.web.dto.PdfInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.muses.web.service.IndexService;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Jervis
 * @date 2018/6/24.
 */
@Controller
@RequestMapping("/home")
public class HomeController {
    private static final Log logger = LogFactory.getLog(HomeController.class);

    @Autowired
    private IndexService indexService;

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/index1")
    public String index1() {
        return "forward:/home/json";
    }

    @RequestMapping(value = "/json")
    public void json(HttpServletResponse repsonse) {
        PrintWriter writer = null;
        try {
            writer = repsonse.getWriter();
            writer.write("test!!!");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

    /**
     * 下载附件
     *
     * @param appId
     * @return
     */
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void ajaxDownloadApp(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) {
        PdfInfo appPdf = indexService.getAppPdf(id);
        String name = "投保单模板_" + id + ".pdf";
        byte[] content = appPdf.getContent();
        DownloadUtils.down(name, content, request, response);
    }
}
