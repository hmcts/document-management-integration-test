package uk.gov.hmcts.dm.it

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

/**
 * Created by pawel on 13/10/2017.
 */
@RunWith(SpringRunner.class)
class DeleteDocumentIT extends BaseIT {

    @Before
    public void setup() throws Exception {
        createUser CITIZEN
        createUser CITIZEN_2
        createUser CASE_WORKER
    }

    @Test
    void "D1 For all users delete is not allowed"() {

        def documentUrl = createDocumentAndGetUrlAs CITIZEN

        def documentUrl1 = createDocumentAndGetUrlAs CASE_WORKER

        givenRequest()
                .expect()
                .statusCode(401)
                .when()
                .delete(documentUrl)

        givenRequest(CITIZEN)
            .expect()
                .statusCode(405)
            .when()
                .delete(documentUrl)

        givenRequest(CITIZEN_2)
                .expect()
                .statusCode(405)
                .when()
                .delete(documentUrl)

        givenRequest(CASE_WORKER)
                .expect()
                .statusCode(405)
                .when()
                .delete(documentUrl)

        givenRequest(CASE_WORKER)
                .expect()
                .statusCode(405)
                .when()
                .delete(documentUrl1)

    }


}
