package pl.edu.amu.wmi.diplomas.service

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import pl.edu.amu.wmi.diplomas.data.entity.DiplomaChapterEntity
import pl.edu.amu.wmi.diplomas.data.entity.DiplomaEntity
import pl.edu.amu.wmi.diplomas.dto.DiplomaChapterDto
import pl.edu.amu.wmi.diplomas.dto.DiplomaDto

class DiplomaGetService {
    fun getDiplomaForProject(projectId: Int): DiplomaDto? {
        return transaction {
            val diploma = DiplomaEntity.selectAll()
                .where { DiplomaEntity.projectId eq projectId }
                .singleOrNull() ?: return@transaction null

            val chapters = DiplomaChapterEntity.selectAll()
                .where { DiplomaChapterEntity.diplomaId eq diploma[DiplomaEntity.id].value }
                .map {
                    DiplomaChapterDto(
                        title = it[DiplomaChapterEntity.title],
                        description = it[DiplomaChapterEntity.description],
                        studentIndex = it[DiplomaChapterEntity.studentIndex]
                    )
                }
                .toList()

            DiplomaDto(
                titleEn = diploma[DiplomaEntity.titleEn],
                titlePl = diploma[DiplomaEntity.titlePl],
                description = diploma[DiplomaEntity.description],
                projectId = diploma[DiplomaEntity.projectId],
                chapters = chapters
            )
        }
    }

    fun getDiplomas(): List<DiplomaDto> {
        return transaction {
            val result = DiplomaEntity.selectAll()
                .map {
                    val chapters = DiplomaChapterEntity.selectAll()
                        .where { DiplomaChapterEntity.diplomaId eq it[DiplomaEntity.id].value }
                        .map { chapter ->
                            DiplomaChapterDto(
                                title = chapter[DiplomaChapterEntity.title],
                                description = chapter[DiplomaChapterEntity.description],
                                studentIndex = chapter[DiplomaChapterEntity.studentIndex]
                            )
                        }
                        .toList()

                    DiplomaDto(
                        titleEn = it[DiplomaEntity.titleEn],
                        titlePl = it[DiplomaEntity.titlePl],
                        description = it[DiplomaEntity.description],
                        projectId = it[DiplomaEntity.projectId],
                        chapters = chapters
                    )
                }
                .toList()
            result
        }
    }

    fun exportDiplomasToTxtFileContent(): String {
        val diplomas: List<DiplomaDto> = getDiplomas()
        return generateTxtContent(diplomas)
    }

    private fun generateTxtContent(diplomas: List<DiplomaDto>): String {
        val stringBuilder = StringBuilder()

        diplomas.forEachIndexed { index, diplomaDto ->
            stringBuilder.append("Diploma ${index + 1}\n")
            stringBuilder.append("\tTitle EN: ${diplomaDto.titleEn}\n")
            stringBuilder.append("\tTitle PL: ${diplomaDto.titlePl}\n")
            stringBuilder.append("\tDescription: ${diplomaDto.description}\n")
            stringBuilder.append("\tChapters:\n")
            diplomaDto.chapters.forEachIndexed { chapterIndex, chapter ->
                stringBuilder.append("\t\tChapter ${chapterIndex + 1}\n")
                stringBuilder.append("\t\tTitle: ${chapter.title}\n")
                stringBuilder.append("\t\tDescription: ${chapter.description}\n")
                stringBuilder.append("\t\tStudent Index: ${chapter.studentIndex}\n")
            }
            stringBuilder.append("\n")
        }
        return stringBuilder.toString()
    }
}