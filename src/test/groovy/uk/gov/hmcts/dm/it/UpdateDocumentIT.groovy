package uk.gov.hmcts.dm.it

import io.restassured.http.ContentType
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

import static org.hamcrest.Matchers.equalTo

/**
 * Created by pawel on 25/01/2018.
 */
@RunWith(SpringRunner.class)
class UpdateDocumentIT  extends BaseIT {

    @Before
    void setup() {
        createUser CITIZEN
        createUser CITIZEN_2
    }

    @Test
    void "UD1 update TTL for a Document"() {

        def documentUrl = createDocumentAndGetUrlAs CITIZEN

        givenRequest(CITIZEN)
            .body([ttl: "3000-10-31T10:10:10+0000"])
            .contentType(ContentType.JSON)
            .expect()
                .statusCode(200)
                .body("ttl", equalTo("3000-10-31T10:10:10.000+0000"))
            .when()
                .patch(documentUrl)

    }

//    @Test
//    void "UD2 update TTL for a Document that I don't own"() {
//
//        def documentUrl = createDocumentAndGetUrlAs CITIZEN
//
//        givenRequest(CITIZEN_2)
//            .body([ttl: "3000-10-31T10:10:10+0000"])
//            .contentType(ContentType.JSON)
//            .expect()
//                .statusCode(403)
//                .body("ttl", equalTo("3000-10-31T10:10:10.000+0000"))
//            .when()
//                .patch(documentUrl)
//
//    }


}
