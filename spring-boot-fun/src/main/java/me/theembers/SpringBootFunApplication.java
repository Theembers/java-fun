package me.theembers;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author TheEmbers Guo
 * createTime 2019-10-09 14:56
 */
@SpringBootApplication(scanBasePackages = "me.theembers.bean")
public class SpringBootFunApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .banner(new TheEmbersBanner()).bannerMode(Banner.Mode.LOG)
                .sources(SpringBootFunApplication.class)
                .run(args);
    }
}
