import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConsumerTest {
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp(){
        wireMockServer = new WireMockServer(wireMockConfig().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
    }

    @AfterEach
    public void endPoint(){
        wireMockServer.stop();
    }

    @Test
    public void testRegisterNewSensor(){
        String sensorName = "Sensor";

        stubFor(post(urlEqualTo("/sensors/registration")).willReturn(aResponse().withStatus(200)));
        assertDoesNotThrow(() -> {Consumer.registerNewSensor(sensorName);});
        verify(postRequestedFor(urlEqualTo("/sensors/registration"))
                .withRequestBody(matchingJsonPath("$.name", equalTo(sensorName))));
    }

    @Test
    public void testAddNewMeasurement(){
        double value = -20.0;
        boolean raining = true;
        String sensorName = "Sensor";

        stubFor(post(urlEqualTo("/measurements/add")).willReturn(aResponse().withStatus(200)));
        assertDoesNotThrow(() -> {Consumer.addNewMeasurement(value, raining, sensorName);});
        verify(postRequestedFor(urlEqualTo("/measurements/add"))
                .withRequestBody(matchingJsonPath("$.value", equalTo(String.valueOf(value))))
                .withRequestBody(matchingJsonPath("$.raining", equalTo(String.valueOf(raining))))
                .withRequestBody(matchingJsonPath("$.sensor.name", equalTo(sensorName))));
    }
}
