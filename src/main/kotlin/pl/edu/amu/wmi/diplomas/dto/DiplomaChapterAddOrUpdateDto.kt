package pl.edu.amu.wmi.diplomas.dto

data class DiplomaChapterAddOrUpdateDto(
    val title: String,
    val description: String,
    val projectId: Int,
    val studentIndex: String
)