import org.junit.runner.RunWith;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Quando;
import io.cucumber.java.pt.Entao;
import io.cucumber.junit.Cucumber;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    WebDriver driver;

    @Dado("que eu esteja na tela de login da plataforma")
    public void que_eu_esteja_na_tela_de_login_da_plataforma() {
        // Inicializar o WebDriver e navegar até a tela de login
        driver = new ChromeDriver();
        driver.get("URL_DA_TELA_DE_LOGIN"); // Substitua com a URL real da tela de login
    }

    @Dado("informe o usuário {string}")
    public void informe_o_usuario(String usuario) {
        // Localizar o campo de usuário e inserir o nome de usuário
        WebElement campoUsuario = driver.findElement(By.id("id_do_campo_usuario")); // Substitua com o ID real do campo de usuário
        campoUsuario.sendKeys(usuario);
    }

    @Dado("informe a senha {string}")
    public void informe_a_senha(String senha) {
        // Localizar o campo de senha e inserir a senha
        WebElement campoSenha = driver.findElement(By.id("id_do_campo_senha")); // Substitua com o ID real do campo de senha
        campoSenha.sendKeys(senha);
    }

    @Quando("eu clicar no botão {string}")
    public void eu_clicar_no_botao(String botao) {
        // Localizar e clicar no botão de login
        WebElement botaoLogin = driver.findElement(By.id("id_do_botao_entrar")); // Substitua com o ID real do botão
        botaoLogin.click();
    }

    @Entao("eu devo ser encaminhado para a home da plataforma")
    public void eu_devo_ser_encaminhado_para_a_home_da_plataforma() {
        // Verificar se a navegação para a home ocorreu
        String urlEsperada = "URL_DA_HOME"; // Substitua com a URL real da página home
        String urlAtual = driver.getCurrentUrl();
        assert urlAtual.equals(urlEsperada); // Esta é uma simples asserção para verificar a URL

        // Fechar o navegador
        driver.quit();
    }
}