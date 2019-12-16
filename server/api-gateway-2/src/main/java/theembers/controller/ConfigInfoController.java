package theembers.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-11 11:35
 */

@RefreshScope
@RestController
@RequestMapping(value = "config")
public class ConfigInfoController {

    @Value("${api-version}")
    private String apiVersion;

    @GetMapping("/api-version")
    public String getApiVersion() {
        return apiVersion;
    }

}
