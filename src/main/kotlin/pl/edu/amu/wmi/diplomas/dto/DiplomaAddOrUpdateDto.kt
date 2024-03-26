package pl.edu.amu.wmi.diplomas.dto

data class DiplomaAddOrUpdateDto(
    val titleEn: String,
    val titlePl: String,
    val description: String,
    val projectId: Int
)