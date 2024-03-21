package pl.edu.amu.wmi.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import pl.edu.amu.wmi.plugins.dto.DiplomaChapterDTO
import pl.edu.amu.wmi.plugins.dto.DiplomaDTO

fun Application.configureRouting() {
    routing {
        get("pri/project-diplomas") {
            call.respond(
                HttpStatusCode.OK, listOf(
                    DiplomaDTO(
                        "Title en", "Title pl", "DESC", 1,
                        listOf(DiplomaChapterDTO("Chapter 1", "DESC", "student1"))
                    ),
                    DiplomaDTO(
                        "Title en 2", "Title pl 2", "DESC 2", 1,
                        listOf(DiplomaChapterDTO("Chapter 1 --", "DESC --", "student1"))
                    )
                )
            )
        }
    }
}