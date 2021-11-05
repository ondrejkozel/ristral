package cz.okozel.ristral;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@Theme(value = "ristral")
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class RistralAplikace  extends SpringBootServletInitializer implements AppShellConfigurator {
    public static void main(String[] args) {
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(RistralAplikace.class, args));
    }
}
