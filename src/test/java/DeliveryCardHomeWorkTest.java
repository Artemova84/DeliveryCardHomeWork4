
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class DeliveryCardHomeWorkTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }

    String planningDate = generateDate(4, "dd.MM.yyyy");

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--disable-dev-shm-usage");
        options.addArguments(
                "--no-sandbox");
        options.addArguments(
                "--headless");
    }

    @Test
    void DeliveryCardPositiveTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void DeliveryCardNegativeCityTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Новый Оскол");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void DeliveryCardEmptyCityTest() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void DeliveryCardEmptyDateTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"),
                Duration.ofSeconds(15));
    }

    @Test
    void DeliveryCardNegativeDateTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys("25.06.2023");
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input__sub").shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"),
                Duration.ofSeconds(15));
    }

    @Test
    void DeliveryCardNegativeNameTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Artemova Oksana");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."),
                Duration.ofSeconds(15));
    }

    @Test
    void DeliveryCardEmptyNameTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=phone] input").setValue("+79103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(15));
    }

    @Test
    void DeliveryCardNegativePhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("89103696853");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                Duration.ofSeconds(15));
    }

    @Test
    void DeliveryCardEmptyPhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(15));
    }

    @Test
    void bankCardNoClickTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Артемова Оксана");
        $("[data-test-id=phone] input").setValue("+79103696853");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldBe(visible).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"),
                Duration.ofSeconds(15));
    }
}






