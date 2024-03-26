package pl.edu.amu.wmi.diplomas.data.entity

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object DiplomaChapterEntity : IntIdTable(name = "diplomas_chapters") {
    val title: Column<String> = text("title")
    val description: Column<String> = text("description")
    val studentIndex: Column<String> = text("student_index")
    val diplomaId: Column<Int> = integer("diploma_id")
}