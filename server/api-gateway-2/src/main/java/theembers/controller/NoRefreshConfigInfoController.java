package theembers.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-12 11:54
 */
@RestController
@RequestMapping(value = "no-refreshed-config")
public class NoRefreshConfigInfoController {

    @Value("${api-version}")
    private String apiVersion;

    @GetMapping("api-version")
    public String getApiVersion() {
        return apiVersion;
    }
}
