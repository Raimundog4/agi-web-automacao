package runners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.CucumberOptions.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"classpath:Features"},
        glue = {"steps"},
        plugin = {
                "pretty",
                "json:target/cucumber.json",
                "html:target/cucumber-reports/cucumber.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@PesquisaArtigos",
        monochrome = true,
        snippets = SnippetType.CAMELCASE,
        dryRun = false
)
public class WebTestRunner {
}