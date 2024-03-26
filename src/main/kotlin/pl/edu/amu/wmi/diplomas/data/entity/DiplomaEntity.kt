package pl.edu.amu.wmi.diplomas.data.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object DiplomaEntity : IntIdTable(name = "diplomas") {
    val titlePl: Column<String> = text("title_pl")
    val titleEn: Column<String> = text("title_en")
    val description: Column<String> = text("description")
    val projectId: Column<Int> = integer("project_id")
}