package pl.edu.amu.wmi.plugins.dto

data class DiplomaDTO(
    val titleEn: String,
    val titlePl: String,
    val description: String,
    val projectId: Int,
    val chapters: List<DiplomaChapterDTO>
)
