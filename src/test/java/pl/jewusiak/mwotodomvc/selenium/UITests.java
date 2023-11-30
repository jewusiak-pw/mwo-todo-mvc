package pl.jewusiak.mwotodomvc.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UITests {

    private static WebDriver wd;


    @Value("${api.url}")
    private String apiUrl;

    @BeforeEach
    public void beforeAll() throws InterruptedException, IOException {
        if (wd == null) {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                var req = new HttpDelete(apiUrl + "/purge");
                var resp = httpClient.execute(req);
                assert resp.getStatusLine().getStatusCode() == 200;
            }

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--headless");
            wd = new ChromeDriver(options);
            wd.get("http://localhost:8080");

        }
    }


    @Test
    @Order(1)
    void createList() throws InterruptedException {
        wd.findElement(By.id("newListInput")).sendKeys("test list");
        wd.findElement(By.id("newListSubmit")).click();
        var lists = wd.findElements(By.xpath("/html/body/div/div/h3"));
        assertThat(lists.size()).isEqualTo(1);
        assertThat(lists.get(0).getText()).isEqualTo("test list");
    }
    
    @Test
    @Order(2)
    void createItem() {
        var itemNames = List.of("test item", "test item2", "test item3");
        itemNames.forEach(item -> {
            wd.findElement(By.id("newItemInput")).sendKeys(item);
            wd.findElement(By.id("newItemSubmit")).click();
        });

        
        var items = wd.findElements(By.xpath("/html/body/div/table/tbody/tr[position()>1]/td[1]"));
        assertThat(items.size()).isEqualTo(itemNames.size());
        assertThat(items.stream().map(WebElement::getText).toList()).usingRecursiveComparison().isEqualTo(itemNames);
    }  
    
    @Test
    @Order(3)
    void updateItemStatus() {
        wd.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[3]/form[1]/input[3]")).click(); // 1st item
        wd.findElement(By.xpath("/html/body/div/table/tbody/tr[4]/td[3]/form[1]/input[3]")).click(); // 3rd item
        
        var items = wd.findElements(By.xpath("/html/body/div/table/tbody/tr[position()>1]/td[2]"));
        assertThat(items.size()).isEqualTo(3);
        assertThat(items.stream().map(WebElement::getText).toList()).usingRecursiveComparison().isEqualTo(List.of("done", "to-do", "done"));
    }
    
    @Test
    @Order(4)
    void deleteItem() {
        wd.findElement(By.xpath("/html/body/div/table/tbody/tr[4]/td[3]/form[2]/input[2]")).click();// 3rd item
        wd.findElement(By.xpath("/html/body/div/table/tbody/tr[2]/td[3]/form[2]/input[2]")).click();// 1st item
        
        //one item left
        var items = wd.findElements(By.xpath("/html/body/div/table/tbody/tr[position()>1]/td[1]"));
        assertThat(items.size()).isEqualTo(1);
        assertThat(items.stream().map(WebElement::getText).toList()).usingRecursiveComparison().isEqualTo(List.of("test item2"));
    }
    
    @Test
    @Order(5)
    void deleteListWithItems() {
        wd.findElement(By.xpath("/html/body/div/form[2]/input[2]")).click();
        
        var lists = wd.findElements(By.xpath("/html/body/div/div/h3"));
        assertThat(lists.size()).isEqualTo(0);
    }
    
    @Test
    @Order(6)
    void deleteListWithoutItems() {
        wd.findElement(By.id("newListInput")).sendKeys("test list");
        wd.findElement(By.id("newListSubmit")).click();
        wd.findElement(By.xpath("/html/body/div/form[2]/input[2]")).click();
        
        var lists = wd.findElements(By.xpath("/html/body/div/div/h3"));
        assertThat(lists.size()).isEqualTo(0);
    }
    
    @Test
    @Order(7)
    void deleteOneOfTheLists(){
        wd.findElement(By.id("newListInput")).sendKeys("test list");
        wd.findElement(By.id("newListSubmit")).click();
        wd.findElement(By.id("newListInput")).sendKeys("test list2");
        wd.findElement(By.id("newListSubmit")).click();
        wd.findElement(By.id("newListInput")).sendKeys("test list3");
        wd.findElement(By.id("newListSubmit")).click();
        
        wd.findElement(By.xpath("/html/body/div[2]/form[2]/input[2]")).click(); //2nd list
        
        var lists = wd.findElements(By.xpath("/html/body/div/div/h3"));
        assertThat(lists.size()).isEqualTo(2);
        assertThat(lists.stream().map(WebElement::getText).toList()).usingRecursiveComparison().isEqualTo(List.of("test list", "test list3"));
    }
    
    @Test
    @Order(8)
    void deliberatelyFailSometimes() {
        wd.findElement(By.id("przyciskNieistniejący")).click();
    }
}
