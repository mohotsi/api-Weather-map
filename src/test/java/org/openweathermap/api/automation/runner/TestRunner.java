package org.openweathermap.api.automation.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/java/org/openweathermap/api/automation/feature",glue={"org.openweathermap.api.automation.definition","org.openweathermap.api.automation.config"},
        monochrome = true,
        plugin={"pretty",
                "json:target/output/HtmlReports.json","html:target/output/HtmlReports.html"})
public class TestRunner  extends AbstractTestNGCucumberTests {

}
