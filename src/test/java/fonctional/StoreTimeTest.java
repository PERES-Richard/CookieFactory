package fonctional;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/StoreTime.feature",
        plugin = {"pretty"}
)
public class StoreTimeTest {
}

