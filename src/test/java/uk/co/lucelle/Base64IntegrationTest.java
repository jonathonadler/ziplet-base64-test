package uk.co.lucelle;


import com.jayway.restassured.RestAssured;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static com.jayway.restassured.config.RestAssuredConfig.newConfig;
import static com.jayway.restassured.config.EncoderConfig.encoderConfig;
import static com.jayway.restassured.config.DecoderConfig.decoderConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
//@WebIntegrationTest(randomPort = true)
public class Base64IntegrationTest {
    public static final String TEST_DATA = "Hello World!";

    @Value("${local.server.port}")
    private int serverPort;

    private String encodedTestData;

    @Before
    public void setUp() {
        RestAssured.port = serverPort;
        encodedTestData = new String(Base64.encodeBase64(TEST_DATA.getBytes()));
    }

    @Test
    public void noBase64Specified() {
        given()
                .body(TEST_DATA)
                .when()
                .post("/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(equalTo(TEST_DATA));
    }

    @Test
    public void base64AcceptSpecified() {


        given()
                .config(newConfig().decoderConfig(decoderConfig().noContentDecoders()))
                .header("Accept-Encoding", "base64")
                .and()
                .body(TEST_DATA)
                .when()
                .post("/")
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(equalTo(encodedTestData));
    }
}
