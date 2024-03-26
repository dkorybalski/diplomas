package pl.edu.amu.wmi.diplomas.dto

data class DiplomaDto(
    val titleEn: String,
    val titlePl: String,
    val description: String,
    val projectId: Int,
    val chapters: List<DiplomaChapterDto>
)
