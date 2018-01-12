package uk.gov.hmcts.dm.it

import io.restassured.RestAssured
import net.jcip.annotations.NotThreadSafe
import org.apache.commons.lang3.RandomStringUtils
import org.junit.After
import org.junit.Before
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import uk.gov.hmcts.dm.it.utilities.FileUtils
import uk.gov.hmcts.dm.it.utilities.V1MediaTypes

import javax.annotation.PostConstruct
import uk.gov.hmcts.dm.it.config.TestContextConfiguration

import static io.restassured.RestAssured.given
import static io.restassured.RestAssured.expect

/**
 * Created by pawel on 16/10/2017.
 */
@ContextConfiguration(classes = TestContextConfiguration)
@NotThreadSafe
class BaseIT {

    @Autowired
    AuthTokenProvider authTokenProvider

    FileUtils fileUtils = new FileUtils()

    @Value('${base-urls.em-api}')
    String emBaseUri

    @Value('${base-urls.idam-user}')
    String idamUserBaseUri

    final String PASSWORD = '123'

    String CITIZEN = 'test12@test.com'

    String CITIZEN_2 = 'test2@test.com'

    String CASE_WORKER = 'test3@test.com'

    final String NOBODY = null

    final String FILES_FOLDER = 'files/'
    final String ATTACHMENT_1 = 'Attachment1.txt'
    final String ATTACHMENT_2 = 'Attachment2.txt'
    final String ATTACHMENT_3 = 'Attachment3.txt'
    final String ATTACHMENT_4 = '1MB.PDF'
    final String ATTACHMENT_5 = 'Attachment1.csv'
    final String ATTACHMENT_6 = 'marbles.gif'
    final String ATTACHMENT_7 = 'png.png'
    final String ATTACHMENT_8 = 'tif.tif'
    final String ATTACHMENT_9 = 'jpg.jpg'
    final String ATTACHMENT_10 = 'Attachment1.txt'
    final String ATTACHMENT_11 = 'rtf.rtf'
    final String ATTACHMENT_12 = 'docx.docx'
    final String ATTACHMENT_13 = 'pptx.pptx'
    final String ATTACHMENT_14 = 'xlsx.xlsx'
    final String ATTACHMENT_15 = 'odt.odt'
    final String ATTACHMENT_16 = 'ods.ods'
    final String ATTACHMENT_17 = 'odp.odp'
    final String ATTACHMENT_18 = 'Attachment1.txt'
    final String ATTACHMENT_19 = 'wav.wav'
    final String ATTACHMENT_20 = 'mid.mid'
    final String ATTACHMENT_21 = 'mp3.mp3'
    final String ATTACHMENT_22 = 'webm.webm'
    final String ATTACHMENT_23 = 'ogg.ogg'
    final String ATTACHMENT_24 = 'mp4.mp4'


    final String BAD_ATTACHMENT_1 = '1MB.exe'
    final String BAD_ATTACHMENT_2 = 'Attachment3.zip'
    final String MAX_SIZE_ALLOWED_ATTACHMENT = '90MB.pdf'
    final String TOO_LARGE_ATTACHMENT = '100MB.pdf'
    final String ILLEGAL_CHAR_FILE = 'uploadFile~@$!.txt'
    final String ILLEGAL_CHAR_FILE1 = 'uploadFile~`\';][{}!@£$%^&()}{_-.txt'
    final String ILLEGAL_CHAR_FILE2 = 'uploadFile9 @_-.txt'
    final String VALID_CHAR_FILE1= 'uploadFile 9.txt'

    @PostConstruct
    void init() {
        RestAssured.baseURI = emBaseUri

    }


    @Before
    void masterBefore() {
        CITIZEN = "${RandomStringUtils.randomAlphabetic(10)}@test.com"
        CITIZEN_2 = "${RandomStringUtils.randomAlphabetic(10)}@test.com"
        CASE_WORKER = "${RandomStringUtils.randomAlphabetic(10)}@test.com"
        deleteAllUsers()
    }

    @After
    void masterAfterTest() {
        deleteAllUsers()
    }

    def givenRequest(username = null) {

        def request = given().log().all()

        if (username) {
            request = request.header("Authorization", authToken(username))
        }

        request

    }

    def expectRequest() {
        expect().log().all()
    }

    def file(fileName) {
        fileUtils.getResourceFile(FILES_FOLDER+fileName)
    }

    def authToken(username) {
        authTokenProvider.getTokens(username, PASSWORD).getUserToken()
    }

    def createDocument(username,  filename = null, classification = null, roles = null, metadata = null) {
        def request = givenRequest(username)
                        .multiPart("files", file( filename ?: "Attachment1.txt"), MediaType.TEXT_PLAIN_VALUE)
                        .multiPart("classification", classification ?: "PUBLIC")

        roles?.each { role ->
            request.multiPart("roles", role)
        }

        if (metadata) {
            request.accept(V1MediaTypes.V1_HAL_DOCUMENT_AND_METADATA_COLLECTION_MEDIA_TYPE_VALUE)
            metadata?.each { key, value ->
                request.multiPart("metadata[${key}]", value)
            }
        }


        request
            .expect()
                .statusCode(200)
            .when()
                .post("/documents")
    }

    def createDocumentAndGetUrlAs(username, filename = null, classification = null, roles = null, metadata = null) {
        createDocument(username, filename, classification, roles, metadata)
            .path("_embedded.documents[0]._links.self.href")
    }

    def createDocumentAndGetBinaryUrlAs(username,  filename = null, classification = null, roles = null) {
        createDocument(username, filename, classification, roles)
            .path("_embedded.documents[0]._links.binary.href")
    }

    def createDocumentContentVersion(documentUrl, username, filename = null) {
        givenRequest(username)
            .multiPart("file", file( filename ?: ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .expect()
                .statusCode(201)
            .when()
                .post(documentUrl)
    }

    def createDocumentContentVersionAndGetUrlAs(documentUrl, username, filename = null) {
        createDocumentContentVersion(documentUrl, username, filename).path('_links.self.href')
    }

    def createDocumentContentVersionAndGetBinaryUrlAs(documentUrl, username, filename = null) {
        createDocumentContentVersion(documentUrl, username, filename).path('_links.binary.href')
    }

    def createUser(username, role = null) {
        authTokenProvider.createIdamUser(username, PASSWORD, role ? Optional.of(role) : Optional.empty())
    }

    def createCaseWorker(username, role = 'caseworker-probate') {
        createUser(username, role)
    }

    def createCaseWorkerCMC(username, role = 'caseworker-cmc') {
        createUser(username, role)
    }

    def createCaseWorkerSSCS(username, role = 'caseworker-sscs') {
        createUser(username, role)
    }

    def createCaseWorkerDivorce(username, role = 'caseworker-sscs') {
        createUser(username, role)
    }

    def deleteUser(username) {
        authTokenProvider.deleteUser username
    }

    def deleteAllUsers() {
        [CITIZEN, CITIZEN_2, CASE_WORKER].each { u ->
            deleteUser u
        }
    }



}
