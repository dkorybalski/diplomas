package pl.edu.amu.wmi.diplomas

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import pl.edu.amu.wmi.diplomas.dto.DiplomaAddOrUpdateDto
import pl.edu.amu.wmi.diplomas.dto.DiplomaChapterAddOrUpdateDto
import pl.edu.amu.wmi.diplomas.service.DiplomaGetService
import pl.edu.amu.wmi.diplomas.service.DiplomaUpdateService

fun Application.configureRouting() {
    val diplomaUpdateService by inject<DiplomaUpdateService>()
    val diplomaGetService by inject<DiplomaGetService>()

    routing {
        get("pri/project-diplomas") {
            call.respond(HttpStatusCode.OK, diplomaGetService.getDiplomas())
        }
        get("pri/project-diplomas/{project-id}") {
            val projectId = call.parameters["project-id"]!!.toInt()
            diplomaGetService.getDiplomaForProject(projectId)?.let {
                call.respond(HttpStatusCode.OK, it)
            } ?: call.respond(HttpStatusCode.NotFound)
        }
        get("pri/project-diplomas/export") {
            val fileContent = diplomaGetService.exportDiplomasToTxtFileContent()
            call.response.header(
                HttpHeaders.ContentDisposition,
                ContentDisposition.Attachment.toString()
            )
            call.respondText(fileContent, ContentType.Text.Plain)
        }
        put("pri/project-diplomas") {
            val body = call.receive<DiplomaAddOrUpdateDto>()
            diplomaUpdateService.insertOrUpdateDiploma(body)
            call.respond(HttpStatusCode.OK)
        }

        put("pri/project-diplomas/chapters") {
            val body = call.receive<DiplomaChapterAddOrUpdateDto>()
            diplomaUpdateService.insertOrUpdateDiplomaChapter(body)
            call.respond(HttpStatusCode.OK)
        }
    }
}