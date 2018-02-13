package uk.gov.hmcts.dm.it

import io.restassured.response.Response
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import uk.gov.hmcts.dm.it.utilities.Classifications
import uk.gov.hmcts.dm.it.utilities.V1MediaTypes
import uk.gov.hmcts.dm.it.utilities.V1MimeTypes

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

import static org.hamcrest.Matchers.contains
import static org.hamcrest.Matchers.containsString
import static org.hamcrest.Matchers.equalTo

/**
 * Created by pawel on 13/10/2017.
 */
@RunWith(SpringRunner.class)
class CreateDocumentIT extends BaseIT {

    @Before
    public void setup() throws Exception {
        createUser CITIZEN

    }


    @Test
    void "CD1 (R1) As authenticated user upload 2 files with correct classification and some roles set"() {
        Response response = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_5), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_6), MediaType.IMAGE_GIF_VALUE)
            .multiPart("files", file(ATTACHMENT_7), MediaType.IMAGE_PNG_VALUE)
            .multiPart("files", file(ATTACHMENT_8), V1MimeTypes.IMAGE_TIF_VALUE)
            .multiPart("files", file(ATTACHMENT_9), MediaType.IMAGE_JPEG_VALUE)
            .multiPart("files", file(ATTACHMENT_4), MediaType.APPLICATION_PDF_VALUE)
            .multiPart("files", file(ATTACHMENT_11), V1MimeTypes.APPLICATION_RTF_VALUE)
            .multiPart("files", file(ATTACHMENT_12), V1MimeTypes.APPLICATION_DOCX_VALUE)
            .multiPart("files", file(ATTACHMENT_13), V1MimeTypes.APPLICATION_PPT_VALUE)
            .multiPart("files", file(ATTACHMENT_14), V1MimeTypes.APPLICATION_XLS_VALUE)
            .multiPart("files", file(ATTACHMENT_15), V1MimeTypes.APPLICATION_ODT_VALUE)
            .multiPart("files", file(ATTACHMENT_16), V1MimeTypes.APPLICATION_ODS_VALUE)
            .multiPart("files", file(ATTACHMENT_17), V1MimeTypes.APPLICATION_ODP_VALUE)
            .multiPart("files", file(ATTACHMENT_19), V1MimeTypes.AUDIO_WAV_VALUE)
            .multiPart("files", file(ATTACHMENT_20), V1MimeTypes.AUDIO_MIDI_VALUE)
            .multiPart("files", file(ATTACHMENT_21), V1MimeTypes.AUDIO_MPEG_VALUE)
            .multiPart("files", file(ATTACHMENT_22), V1MimeTypes.AUDIO_WEBM_VALUE)
            .multiPart("files", file(ATTACHMENT_23), V1MimeTypes.AUDIO_OGG_VALUE)
            .multiPart("files", file(ATTACHMENT_22), V1MimeTypes.VIDEO_WEBM_VALUE)
            .multiPart("files", file(ATTACHMENT_23), V1MimeTypes.VIDEO_OGG_VALUE)
            .multiPart("files", file(ATTACHMENT_24), V1MimeTypes.VIDEO_MPEG_VALUE)
            .multiPart("files", file(ATTACHMENT_25), V1MimeTypes.IMAGE_TIF_VALUE)
            .multiPart("files", file(ATTACHMENT_26), V1MimeTypes.IMAGE_BMP_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
            .multiPart("ttl", "2018-10-31T10:10:10+0000")
        .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_1))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0].roles[0]", equalTo("caseworker"))
            .body("_embedded.documents[0].roles[1]", equalTo("citizen"))
            .body("_embedded.documents[0].ttl", equalTo("2018-10-31T10:10:10.000+0000"))
            .body("_embedded.documents[1].originalDocumentName", equalTo(ATTACHMENT_5))
            .body("_embedded.documents[1].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[1].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[1].roles[0]", equalTo("caseworker"))
            .body("_embedded.documents[1].roles[1]", equalTo("citizen"))
            .body("_embedded.documents[1].ttl", equalTo("2018-10-31T10:10:10.000+0000"))
            .body("_embedded.documents[2].originalDocumentName", equalTo(ATTACHMENT_6))
            .body("_embedded.documents[2].mimeType", equalTo(MediaType.IMAGE_GIF_VALUE))
            .body("_embedded.documents[2].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[2].roles[0]", equalTo("caseworker"))
            .body("_embedded.documents[2].roles[1]", equalTo("citizen"))
            .body("_embedded.documents[3].originalDocumentName", equalTo(ATTACHMENT_7))
            .body("_embedded.documents[3].mimeType", equalTo(MediaType.IMAGE_PNG_VALUE))
            .body("_embedded.documents[4].originalDocumentName", equalTo(ATTACHMENT_8))
            .body("_embedded.documents[4].mimeType", equalTo(V1MimeTypes.IMAGE_TIF_VALUE))
            .body("_embedded.documents[5].originalDocumentName", equalTo(ATTACHMENT_9))
            .body("_embedded.documents[5].mimeType", equalTo(MediaType.IMAGE_JPEG_VALUE))
            .body("_embedded.documents[6].originalDocumentName", equalTo(ATTACHMENT_4))
            .body("_embedded.documents[6].mimeType", equalTo(MediaType.APPLICATION_PDF_VALUE))
            .body("_embedded.documents[7].originalDocumentName", equalTo(ATTACHMENT_11))
            .body("_embedded.documents[7].mimeType", equalTo(V1MimeTypes.APPLICATION_RTF_VALUE))
            .body("_embedded.documents[8].originalDocumentName", equalTo(ATTACHMENT_12))
            .body("_embedded.documents[8].mimeType", equalTo(V1MimeTypes.APPLICATION_DOCX_VALUE))
            .body("_embedded.documents[9].originalDocumentName", equalTo(ATTACHMENT_13))
            .body("_embedded.documents[9].mimeType", equalTo(V1MimeTypes.APPLICATION_PPT_VALUE))
            .body("_embedded.documents[10].originalDocumentName", equalTo(ATTACHMENT_14))
            .body("_embedded.documents[10].mimeType", equalTo(V1MimeTypes.APPLICATION_XLS_VALUE))
            .body("_embedded.documents[11].originalDocumentName", equalTo(ATTACHMENT_15))
            .body("_embedded.documents[11].mimeType", equalTo(V1MimeTypes.APPLICATION_ODT_VALUE))
            .body("_embedded.documents[12].originalDocumentName", equalTo(ATTACHMENT_16))
            .body("_embedded.documents[12].mimeType", equalTo(V1MimeTypes.APPLICATION_ODS_VALUE))
            .body("_embedded.documents[13].originalDocumentName", equalTo(ATTACHMENT_17))
            .body("_embedded.documents[13].mimeType", equalTo(V1MimeTypes.APPLICATION_ODP_VALUE))
            .body("_embedded.documents[14].originalDocumentName", equalTo(ATTACHMENT_19))
            .body("_embedded.documents[14].mimeType", equalTo(V1MimeTypes.AUDIO_WAV_VALUE))
            .body("_embedded.documents[15].originalDocumentName", equalTo(ATTACHMENT_20))
            .body("_embedded.documents[15].mimeType", equalTo(V1MimeTypes.AUDIO_MIDI_VALUE))
            .body("_embedded.documents[16].originalDocumentName", equalTo(ATTACHMENT_21))
            .body("_embedded.documents[16].mimeType", equalTo(V1MimeTypes.AUDIO_MPEG_VALUE))
            .body("_embedded.documents[17].originalDocumentName", equalTo(ATTACHMENT_22))
            .body("_embedded.documents[17].mimeType", equalTo(V1MimeTypes.AUDIO_WEBM_VALUE))
            .body("_embedded.documents[18].originalDocumentName", equalTo(ATTACHMENT_23))
            .body("_embedded.documents[18].mimeType", equalTo(V1MimeTypes.AUDIO_OGG_VALUE))
            .body("_embedded.documents[19].originalDocumentName", equalTo(ATTACHMENT_22))
            .body("_embedded.documents[19].mimeType", equalTo(V1MimeTypes.VIDEO_WEBM_VALUE))
            .body("_embedded.documents[20].originalDocumentName", equalTo(ATTACHMENT_23))
            .body("_embedded.documents[20].mimeType", equalTo(V1MimeTypes.VIDEO_OGG_VALUE))
            .body("_embedded.documents[21].originalDocumentName", equalTo(ATTACHMENT_24))
            .body("_embedded.documents[21].mimeType", equalTo(V1MimeTypes.VIDEO_MPEG_VALUE))
            .body("_embedded.documents[22].originalDocumentName", equalTo(ATTACHMENT_25))
            .body("_embedded.documents[22].mimeType", equalTo(V1MimeTypes.IMAGE_TIF_VALUE))
            .body("_embedded.documents[23].originalDocumentName", equalTo(ATTACHMENT_26))
            .body("_embedded.documents[23].mimeType", equalTo(V1MimeTypes.IMAGE_BMP_VALUE))
        .when()
            .post("/documents")

        String documentUrl1 = response.path("_embedded.documents[0]._links.self.href")
        String documentContentUrl1 = response.path("_embedded.documents[0]._links.binary.href")
        String document1Size = response.path("_embedded.documents[0].size")

        givenRequest(CITIZEN)
        .expect()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_MEDIA_TYPE_VALUE)
            .body("originalDocumentName", equalTo(ATTACHMENT_1))
            .body("classification", equalTo(Classifications.PUBLIC as String))
            .body("roles[0]", equalTo("caseworker"))
            .body("roles[1]", equalTo("citizen"))
        .when()
            .get(documentUrl1)

        givenRequest(CITIZEN)
        .expect()
            .statusCode(200)
            .contentType(containsString(MediaType.TEXT_PLAIN_VALUE))
            .header("OriginalFileName", ATTACHMENT_1)
            .header("Content-Length", equalTo(document1Size))
            .body(equalTo("Attachment File 1 for test"))
        .when()
            .get(documentContentUrl1)

    }

    @Test
    void "CD2 As unauthenticated user I fail (401) to upload files"() {
        givenRequest()
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_2), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "caseworker")
            .multiPart("roles", "citizen")
        .expect()
            .statusCode(401)
        .when()
            .post("/documents")
    }

    @Test
    void "CD3 As authenticated user upload a file without classification"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_2), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
        .expect()
            .statusCode(422)
        .when()
            .post("/documents")
    }

    @Test
    void "CD4 As authenticated user I upload files with incorrect classification"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_2), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("classification", "XYZ")
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
        .expect()
            .statusCode(422)
        .when()
            .post("/documents")
    }

    @Test
    void "CD5 As authenticated user I upload no files"() {
        givenRequest(CITIZEN)
            .multiPart("classification", Classifications.RESTRICTED)
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
        .expect().log().all()
            .statusCode(422)
        .when()
            .post("/documents")
    }

    @Test
    void "CD6 As authenticated user I upload file with name containing illegal characters"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ILLEGAL_CHAR_FILE), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ILLEGAL_CHAR_FILE1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ILLEGAL_CHAR_FILE2), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
        .expect()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo("uploadFile.txt"))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0].roles[0]", equalTo("caseworker"))
            .body("_embedded.documents[1].originalDocumentName", equalTo("uploadFile_-.txt"))
            .body("_embedded.documents[2].originalDocumentName", equalTo("uploadFile9 _-.txt"))
        .when()
            .post("/documents")
    }

    // This tests will also verify that that there are no restrictions of the file type on EM service end.
    // It should be applied by the services consuming our API
    @Test
    void "CD7 As authenticated user I can upload files of different format"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_4), MediaType.APPLICATION_PDF_VALUE)
            .multiPart("classification", Classifications.PRIVATE as String)
        .expect()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_1))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PRIVATE as String))
            .body("_embedded.documents[0].roles", equalTo(null))
            .body("_embedded.documents[1].originalDocumentName", equalTo(ATTACHMENT_4))
            .body("_embedded.documents[1].mimeType", equalTo(MediaType.APPLICATION_PDF_VALUE))
            .body("_embedded.documents[1].classification", equalTo(Classifications.PRIVATE as String))
            .body("_embedded.documents[1].roles", equalTo(null))
        .when()
            .post("/documents")
    }

    // This tests will also verify that that there are no restrictions of the file type on EM service end.
    // It should be applied by the services consuming our API
    @Test
    void "CD8 As authenticated user I can not upload files of different format if not on the whitelist (.exe)"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_4), MediaType.APPLICATION_PDF_VALUE)
            .multiPart("files", file(BAD_ATTACHMENT_1), MediaType.ALL_VALUE)
            .multiPart("classification", Classifications.PRIVATE as String)
        .expect()
            .statusCode(422)
        .when()
            .post("/documents")
    }

    @Test
    void "CD9 As authenticated user I cannot upload xml file"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_18), MediaType.APPLICATION_XML_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "caseworker")
            .multiPart("roles", "citizen")
            .expect()
            .statusCode(422)
            .when()
            .post("/documents")
    }

    @Test
    void "CD10 As authenticated user I cannot upload svg file"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_10), V1MimeTypes.IMAGE_SVG_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "caseworker")
            .multiPart("roles", "citizen")
            .expect()
            .statusCode(422)
            .when()
            .post("/documents")
    }

    @Test
    void "CD11 (R1) As authenticated when i upload a file only first TTL will be taken into consideration"() {
        givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
            .multiPart("ttl", "2018-10-31T10:10:10+0000")
            .multiPart("ttl", "2018-01-31T10:10:10+0000")
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_1))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0].roles[0]", equalTo("caseworker"))
            .body("_embedded.documents[0].ttl", equalTo("2018-10-31T10:10:10.000+0000"))
        .when()
        .post("/documents")
    }

    @Test
    void "CD12 (R1) As a user, when i upload a file with a TTL, file will be removed by background process once TTL is complete"() {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_ZONED_DATE_TIME
        def ttlDate = OffsetDateTime.now().minusMinutes(2).format(dtf).toString().substring(0, 19) + "+0000"
        def url = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .multiPart("roles", "caseworker")
            .multiPart("ttl", ttlDate)
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_1))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0].roles[0]", equalTo("caseworker"))
            .body("_embedded.documents[0].ttl", equalTo(ttlDate.replace("+", ".000+")))
            .when()
            .post("/documents")
            .path("_embedded.documents[0]._links.self.href")

            sleep(80000)

        givenRequest(CITIZEN)
            .expect()
            .statusCode(404)
            .when()
            .get(url)
    }

    @Test
    void "CD13 (R1) As authenticated when i upload a text file or Tiff I get an icon in return"() {
        def response = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_1), MediaType.TEXT_PLAIN_VALUE)
            .multiPart("files", file(ATTACHMENT_25), V1MimeTypes.IMAGE_TIF_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_1))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.TEXT_PLAIN_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0]._links.thumbnail.href", containsString("thumbnail"))
            .when()
            .post("/documents")
            .andReturn()
            //.path("_embedded.documents[0]._links.thumbnail.href")

        def notepadUrl = response.path("_embedded.documents[0]._links.thumbnail.href")
        def tiffUrl = response.path("_embedded.documents[1]._links.thumbnail.href")

        def notepadByteArray =  givenRequest(CITIZEN)
        .get(notepadUrl).asByteArray()

        def tiffByteArray =  givenRequest(CITIZEN)
            .get(tiffUrl).asByteArray()

        def file = file("ThumbnailNPad.jpg").getBytes()

        Assert.assertTrue(Arrays.equals(notepadByteArray, file))
        Assert.assertTrue(Arrays.equals(tiffByteArray, file))
    }

    @Test
    void "CD14 (R1) As authenticated user when i upload a JPEG, it gets a thumbnail"() {
        def url = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_9), MediaType.IMAGE_JPEG_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_9))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.IMAGE_JPEG_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0]._links.thumbnail.href", containsString("thumbnail"))
            .when()
            .post("/documents")
            .path("_embedded.documents[0]._links.thumbnail.href")

        def downloadedFileByteArray =  givenRequest(CITIZEN)
            .get(url).asByteArray()

        def file = file("ThumbnailJPG.jpg").getBytes()
        Assert.assertTrue(Arrays.equals(downloadedFileByteArray, file))
    }

    @Test
    void "CD15 (R1) As authenticated user when I upload a pdf, I can get the thumbnail of that pdf"() {
        def url = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_4), MediaType.APPLICATION_PDF_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_4))
            .body("_embedded.documents[0].mimeType", equalTo(MediaType.APPLICATION_PDF_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0]._links.thumbnail.href", containsString("thumbnail"))
            .when()
            .post("/documents")
            .path("_embedded.documents[0]._links.thumbnail.href")

        def downloadedFileByteArray =  givenRequest(CITIZEN)
            .get(url).asByteArray()

        def file = file(THUMBNAIL_PDF).readBytes()
        Assert.assertTrue(Arrays.equals(downloadedFileByteArray, file))
    }

    @Test
    void "CD16 (R1) As authenticated user when I upload a bmp, I can get the thumbnail of that bmp"() {
        def url = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_26), V1MimeTypes.IMAGE_BMP_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_26))
            .body("_embedded.documents[0].mimeType", equalTo(V1MimeTypes.IMAGE_BMP_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0]._links.thumbnail.href", containsString("thumbnail"))
            .when()
            .post("/documents")
            .path("_embedded.documents[0]._links.thumbnail.href")

        def downloadedFileByteArray =  givenRequest(CITIZEN)
            .get(url).asByteArray()

        def file = file(THUMBNAIL_BMP).readBytes()
        Assert.assertTrue(Arrays.equals(downloadedFileByteArray, file))
    }

    @Test
    void "CD17 (R1) As authenticated user when I upload a gif, I can get the thumbnail of that gif"() {

        def url = givenRequest(CITIZEN)
            .multiPart("files", file(ATTACHMENT_6), V1MimeTypes.IMAGE_GIF_VALUE)
            .multiPart("classification", Classifications.PUBLIC as String)
            .multiPart("roles", "citizen")
            .expect().log().all()
            .statusCode(200)
            .contentType(V1MediaTypes.V1_HAL_DOCUMENT_COLLECTION_MEDIA_TYPE_VALUE)
            .body("_embedded.documents[0].originalDocumentName", equalTo(ATTACHMENT_6))
            .body("_embedded.documents[0].mimeType", equalTo(V1MimeTypes.IMAGE_GIF_VALUE))
            .body("_embedded.documents[0].classification", equalTo(Classifications.PUBLIC as String))
            .body("_embedded.documents[0]._links.thumbnail.href", containsString("thumbnail"))
            .when()
            .post("/documents")
            .path("_embedded.documents[0]._links.thumbnail.href")

        def downloadedFileByteArray =  givenRequest(CITIZEN)
            .get(url).asByteArray()

        def file = file(THUMBNAIL_GIF).readBytes() //.getBytes()
        Assert.assertTrue(Arrays.equals(downloadedFileByteArray, file))
    }

//    @Test
//    void "CD9 As authenticated user I can not upload files that are larger than 100MB"() {
//        givenRequest(CITIZEN)
//                .accept(MediaType.TEXT_HTML_VALUE)
//                .multiPart("files", file(TOO_LARGE_ATTACHMENT), MediaType.TEXT_PLAIN_VALUE)
//                .multiPart("classification", Classifications.PRIVATE as String)
//                .expect().log().all()
//                .statusCode(413)
//                .when()
//                .post("/documents")
//    }
//
//    @Test
//    void "CD10 As authenticated user I can upload files of upto 90MB in size"() {
//        givenRequest(CITIZEN)
//                .accept(MediaType.TEXT_HTML_VALUE)
//                .multiPart("files", file(MAX_SIZE_ALLOWED_ATTACHMENT), MediaType.TEXT_PLAIN_VALUE)
//                .multiPart("classification", Classifications.PRIVATE as String)
//                .expect().log().all()
//                .statusCode(200)
//                .when()
//                .post("/documents")
//    }
}
