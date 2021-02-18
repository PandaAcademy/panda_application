package pl.pandait.panda;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
    public void startup() throws InterruptedException {

        //Driver znajduje się w resource
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver");
        //Ścieżka do Firefoxa - jeżeli nie działa trzeba sprawdzić, gdzie FF jest zainstalowany!
        System.setProperty("webdriver.firefox.bin", "/usr/lib/firefox/firefox");

        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);

        // Pamiętaj, że aplikacja Spring musi działać!
        driver.get(String.format("http://192.168.44.44:%s", port));

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
