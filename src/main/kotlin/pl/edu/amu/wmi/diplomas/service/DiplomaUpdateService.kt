package pl.edu.amu.wmi.diplomas.service

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import pl.edu.amu.wmi.diplomas.data.entity.DiplomaChapterEntity
import pl.edu.amu.wmi.diplomas.data.entity.DiplomaEntity
import pl.edu.amu.wmi.diplomas.dto.DiplomaAddOrUpdateDto
import pl.edu.amu.wmi.diplomas.dto.DiplomaChapterAddOrUpdateDto

class DiplomaUpdateService {
    fun insertOrUpdateDiploma(body: DiplomaAddOrUpdateDto) {
        return transaction {
            val result = DiplomaEntity.selectAll()
                .where { DiplomaEntity.projectId eq body.projectId }
                .singleOrNull()

            if (result == null) {
                DiplomaEntity.insert {
                    it[titleEn] = body.titleEn
                    it[titlePl] = body.titlePl
                    it[description] = body.description
                    it[projectId] = body.projectId
                }
            } else {
                DiplomaEntity.update {
                    it[id] = result[id]
                    it[titleEn] = body.titleEn
                    it[titlePl] = body.titlePl
                    it[description] = body.description
                }
            }
        }
    }

    fun insertOrUpdateDiplomaChapter(body: DiplomaChapterAddOrUpdateDto) {
        return transaction {
            val diploma = DiplomaEntity.selectAll()
                .where { DiplomaEntity.projectId eq body.projectId }
                .singleOrNull() ?: return@transaction

            val diplomaChapter = DiplomaChapterEntity.selectAll()
                .where { DiplomaChapterEntity.diplomaId eq diploma[DiplomaEntity.id].value }
                .andWhere { DiplomaChapterEntity.studentIndex eq body.studentIndex }
                .singleOrNull()

            if (diplomaChapter == null) {
                DiplomaChapterEntity.insert {
                    it[title] = body.title
                    it[description] = body.description
                    it[diplomaId] = diploma[DiplomaEntity.id].value
                    it[studentIndex] = body.studentIndex
                }
            } else {
                DiplomaChapterEntity.update {
                    it[id] = diplomaChapter[id]
                    it[title] = body.title
                    it[description] = body.description
                }
            }
        }
    }
}