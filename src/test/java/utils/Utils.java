package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Utils {

	private static WebDriver driver;
	public static final String URL_BASE = "https://blog.agibank.com.br";

    public static WebDriver iniciarDriver() {
    	/*
    	 * Utilizo o WebDriverManager para evitar versionar o driver do navegador no projeto
    	 * e reduzir problemas de compatibilidade entre a versão do browser e a do driver.
    	 */
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        options.addArguments("--high-dpi-support=1");
        
        if ("true".equals(System.getenv("CI"))) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().deleteAllCookies();

        return driver;
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void finalizarDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
    
    public static void abrirHome() {
        getDriver().get(URL_BASE);
        esperarPaginaCarregar();
    }
    
    public static void esperarPaginaCarregar() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));
    }
    
    public static void esperarPorElemento(WebElement elemento) {
    	WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(elemento));
	}
    
    public static boolean paginaContemTexto(String texto) {
        try {
            return getDriver().getPageSource().contains(texto);
        } catch (Exception e) {
            return false;
        }
    }
    
    public static boolean paginaComErro429() {
        return paginaContemTexto("Too Many Requests");
    }
    
    public static void refreshComEspera() {
        getDriver().navigate().refresh();
        esperarPaginaCarregar();
    }
    
    public static boolean elementoVisivel(WebElement elemento) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOf(elemento)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    public static void esperarPorElementoClicavel(WebElement elemento) {
    	WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    	wait.until(ExpectedConditions.elementToBeClickable(elemento));
    }
    
    public static boolean elementoVisivel(WebElement elemento, int segundos) {
    	try {
    		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(segundos));
    		wait.until(ExpectedConditions.visibilityOf(elemento));
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }
    
    public static void capturarTela(Scenario cenario) {
    	/*
    	 * Utilizo esse método para adicionar a screenshot ao final de cada cenário no relatório.
    	 */
	    WebDriver driver = getDriver();

	    if (driver instanceof TakesScreenshot) {
	        final byte[] screenshot = ((TakesScreenshot) driver)
	                .getScreenshotAs(OutputType.BYTES);

	        cenario.attach(screenshot, "image/png", "Screenshot");
	    } else {
	        System.out.println("Driver não suporta captura de tela.");
	    }
	}
    
    public static void descerScroll(WebElement elemento) {
        try {
            ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});",
                elemento
            );
        } catch (Exception e) {
            System.out.println("Erro ao realizar scroll até o elemento: " + e.getMessage());
        }
    }
    
    public static void validarCarregamentoEVisibilidade(List<WebElement> lista, int segundos) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(segundos));

        wait.withMessage("A lista de resultados não foi carregada no DOM.")
            .until(driver -> lista != null && !lista.isEmpty());

        wait.withMessage("Os itens foram carregados no DOM, mas não chegaram ao estado visual final.")
            .until(driver -> lista.stream().anyMatch(elemento -> {
                String opacity = elemento.getCssValue("opacity");
                return elemento.isDisplayed() && "1".equals(opacity);
            }));
    }
}
