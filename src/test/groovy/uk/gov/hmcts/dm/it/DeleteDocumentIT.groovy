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
    private citizenDocumentUrl
    private caseWorkierDocumentUrl

    @Before
    public void setup() throws Exception {
        createUser CITIZEN
        createUser CITIZEN_2
        createUser CASE_WORKER

        this.citizenDocumentUrl = createDocumentAndGetUrlAs CITIZEN
        this.caseWorkierDocumentUrl = createDocumentAndGetUrlAs CASE_WORKER
    }

    @Test
    void "D1 Unauthenticated user cannot delete"() {


        givenRequest()
            .expect()
            .statusCode(401)
            .when()
            .delete(citizenDocumentUrl)
    }

    @Test
    void "D2 Authenticated can delete their own documents"() {
        givenRequest(CITIZEN)
            .expect()
            .statusCode(204)
            .when()
            .delete(citizenDocumentUrl)

        givenRequest(CITIZEN)
            .expect()
            .statusCode(404)
            .when()
            .get(citizenDocumentUrl)
    }

    @Test
    void "D3 Authenticated cannot delete other users' documents"() {
        givenRequest(CITIZEN_2)
                .expect()
                .statusCode(403)
                .when()
                .delete(citizenDocumentUrl)
    }

    @Test
    void "D4 Case worker cannot delete other users' documents"() {
        givenRequest(CASE_WORKER)
            .expect()
            .statusCode(403)
            .when()
            .delete(citizenDocumentUrl)
    }

    @Test
    void "D5 Case worker can delete their own document"() {
        givenRequest(CASE_WORKER)
            .expect()
            .statusCode(204)
            .when()
            .delete(caseWorkierDocumentUrl)

        givenRequest(CASE_WORKER)
            .expect()
            .statusCode(404)
            .when()
            .get(caseWorkierDocumentUrl)
    }

    @Test
    void "D6 Case worker can hard delete their own document"() {
        givenRequest(CASE_WORKER)
            .expect()
            .statusCode(204)
            .when()
            .delete(caseWorkierDocumentUrl + "?permanent=true")

        givenRequest(CASE_WORKER)
            .expect()
            .statusCode(404)
            .when()
            .get(caseWorkierDocumentUrl)
    }

}
