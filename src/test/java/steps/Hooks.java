package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import static utils.Utils.*;


public class Hooks {

	@Before
	public void setUp() {
		iniciarDriver();
		getDriver().get(URL_BASE);
	}

	@After
	public void tearDown(Scenario cenario) {
		capturarTela(cenario);
		finalizarDriver();
	}

}
