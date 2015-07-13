package api;

import com.jayway.restassured.RestAssured;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import play.api.test.TestServer;
import play.db.jpa.JPA;
import utils.dev.InitialDataManager;
import utils.settings.PredictorSettings;
import utils.settings.TestSettingsProvider;

import static play.test.Helpers.testServer;

/**
 * Base class for api testing.
 *
 * @author Mateusz Urbanski <matek2305@gmail.com>
 */
public abstract class PredictorApiTestCase {

    private static final int PORT = 3333;
    private static final TestServer SERVER = testServer(PORT);

    private final InitialDataManager initialDataManager = new InitialDataManager();

    @BeforeClass
    public static void setUpClass() {
        PredictorSettings.setSettingsProvider(new TestSettingsProvider());
        RestAssured.port = PORT;
        SERVER.start();
    }

    @AfterClass
    public static void tearDownClass() {
        SERVER.stop();
    }

    @Before
    public void setUp() {
        JPA.withTransaction(() -> {
            initialDataManager.dropData();
            initialDataManager.inserData();
        });
    }

    @After
    public void tearDown() {
        JPA.withTransaction(() -> initialDataManager.dropData());
    }
}
