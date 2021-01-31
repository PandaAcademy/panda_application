package pl.pandait.panda;


import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import static org.junit.Assert.assertEquals;

public class PandaApplicationSeleniumTest {
    private static WebDriver driver;

    @BeforeAll
    public static void startup() throws InterruptedException {
        //Driver znajduje się w resource
        System.setProperty("webdriver.gecko.driver", "src/resources/geckodriver");
        //Ścieżka do Firefoxa - jeżeli nie działa trzeba sprawdzić, gdzie FF jest zainstalowany!
        System.setProperty("webdriver.firefox.bin", "/usr/bin/firefox");
        FirefoxOptions capabilities = new FirefoxOptions();
        capabilities.setCapability("marionette", true);

        // Tworzymy nową instancję Firefoxa
        driver = new FirefoxDriver(capabilities);
        // Otwieramy stronę
        // Pamiętaj, że aplikacja Spring musi działać! To znaczy też musi być włączona.
        driver.get("http://localhost:8080/");
        // Wyświetlamy informacje, że udało się otwozyć stronę
        System.out.println("Successfully opened the website");
        //Czekamy 2 sekundy
        Thread.sleep(2000);
    }

    @Test
    public void checkIfApplicationRunCorrectly(){
        System.out.println("Uruchamiam test 1: Sprawdzenie napisu na stronie głównej");
        WebElement greetingString = driver.findElement(By.xpath("//p"));
        String napis = greetingString.getText().trim();
        assertEquals("Get your greeting here", napis);

        WebElement linkToGreetings = greetingString.findElement(By.xpath("./a"));
        linkToGreetings.click();

        WebElement helloWorldString = driver.findElement(By.xpath("//p"));
        String newPageString = helloWorldString.getText().trim();
        assertEquals("Hello, World!", newPageString);
    }

    @AfterAll
    public static void after(){
        driver.quit();
    }
}
