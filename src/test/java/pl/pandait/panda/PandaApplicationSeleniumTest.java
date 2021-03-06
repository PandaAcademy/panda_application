package pl.pandait.panda;

import java.net.URL;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.Assert.assertEquals;

@SpringBootTest(classes = {PandaApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PandaApplicationSeleniumTest {

    private static WebDriver driver;

     @LocalServerPort
     private int port;

    @BeforeEach
    public void startup() throws InterruptedException, MalformedURLException {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        capabilities.setPlatform(Platform.LINUX);

        // Odwołujemy się do zdalnego silnika Firefox z Selenium Grid
        driver = new RemoteWebDriver(new URL("http://192.168.44.44:4444/wd/hub"), capabilities);

        // Pamiętaj, że aplikacja Spring musi działać!
        driver.get(String.format("http://ubuntucompose:%s/", port));
        
        // Alternatywnie, gdyby DNS dockera nie komunikował się poprawnie
        // driver.get(String.format("http://192.168.44.44:%s", port));

        //Czekamy 2 sekundy
        Thread.sleep(2000);
    }

    @Test
    public void greetings_shouldOpenMainPageThenReturnWelcomeText() {
        System.out.println("Uruchamiam test 1: Sprawdzenie napisu na stronie głównej");
        WebElement greetingElement = driver.findElement(By.xpath("//p"));
        String greetingText = greetingElement.getText().trim();
        assertEquals("Get your greeting here", greetingText);
    }

    @Test
    public void greetings_shouldOpenSubpageThenReturnGreetingsText() {
        System.out.println("Uruchamiam test 2: Sprawdzenie napisu na podstronie");
        WebElement greetingElement = driver.findElement(By.xpath("//p"));
        WebElement linkToGreetings = greetingElement.findElement(By.xpath("./a"));
        linkToGreetings.click();
        WebElement helloWorldString = driver.findElement(By.xpath("//p"));
        String newPageString = helloWorldString.getText().trim();
        assertEquals("Hello, World!", newPageString);
    }

    @AfterEach
    public void after() {
        driver.quit();
    }
}
