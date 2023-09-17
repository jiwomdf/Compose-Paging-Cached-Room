package com.katilijiwoadiwiyono.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ArtworkResponse(
    @SerializedName("pagination") val paginationResponse: PaginationResponse,
    @SerializedName("data") val artworkResponse: List<ArtworkDataResponse>,
    @SerializedName("info") val infoResponse: InfoResponse,
    @SerializedName("config") val configResponse: ConfigResponse,
) {

    data class ArtworkDataResponse(
        @SerializedName("alt_artist_ids") val altArtistIds: List<String>?,
        @SerializedName("alt_classification_ids") val altClassificationIds: List<String>?,
        @SerializedName("alt_image_ids") val altImageIds: List<String>?,
        @SerializedName("alt_material_ids") val altMaterialIds: List<String>?,
        @SerializedName("alt_style_ids") val altStyleIds: List<String>?,
        @SerializedName("alt_subject_ids") val altSubjectIds: List<String>?,
        @SerializedName("alt_technique_ids") val altTechniqueIds: List<String>?,
        @SerializedName("alt_titles") val altTitles: List<String>?,
        @SerializedName("api_link") val apiLink: String?,
        @SerializedName("api_model") val apiModel: String?,
        @SerializedName("artist_display") val artistDisplay: String?,
        @SerializedName("artist_id") val artistId: Double?,
        @SerializedName("artist_ids") val artistIds: List<Double>?,
        @SerializedName("artist_title") val artistTitle: String?,
        @SerializedName("artist_titles") val artistTitles: List<String>?,
        @SerializedName("artwork_type_id") val artworkTypeId: Double?,
        @SerializedName("artwork_type_title") val artworkTypeTitle: String?,
        @SerializedName("boost_rank") val boostRank: String?,
        @SerializedName("catalogue_display") val catalogueDisplay: String?,
        @SerializedName("category_ids") val categoryIds: List<String>?,
        @SerializedName("category_titles") val categoryTitles: List<String>?,
        @SerializedName("classification_id") val classificationId: String?,
        @SerializedName("classification_ids") val classificationIds: List<String>?,
        @SerializedName("classification_title") val classificationTitle: String?,
        @SerializedName("classification_titles") val classificationTitles: List<String>?,
        @SerializedName("color") val colorResponse: ColorResponse?,
        @SerializedName("colorfulness") val colorfulness: Double?,
        @SerializedName("copyright_notice") val copyrightNotice: String?,
        @SerializedName("credit_line") val creditLine: String?,
        @SerializedName("date_display") val dateDisplay: String?,
        @SerializedName("date_end") val dateEnd: Double?,
        @SerializedName("date_qualifier_id") val dateQualifierId: String?,
        @SerializedName("date_qualifier_title") val dateQualifierTitle: String?,
        @SerializedName("date_start") val dateStart: Double?,
        @SerializedName("department_id") val departmentId: String?,
        @SerializedName("department_title") val departmentTitle: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("dimensions") val dimensions: String?,
        @SerializedName("dimensions_detail") val dimensionsDetailResponse: List<DimensionsDetailResponse>?,
        @SerializedName("document_ids") val documentIds: List<String>?,
        @SerializedName("edition") val edition: String?,
        @SerializedName("exhibition_history") val exhibitionHistory: String?,
        @SerializedName("fiscal_year") val fiscalYear: Double?,
        @SerializedName("fiscal_year_deaccession") val fiscalYearDeaccession: String?,
        @SerializedName("gallery_id") val galleryId: String?,
        @SerializedName("gallery_title") val galleryTitle: String?,
        @SerializedName("has_advanced_imaging") val hasAdvancedImaging: Boolean?,
        @SerializedName("has_educational_resources") val hasEducationalResources: Boolean?,
        @SerializedName("has_multimedia_resources") val hasMultimediaResources: Boolean?,
        @SerializedName("has_not_been_viewed_much") val hasNotBeenViewedMuch: Boolean?,
        @SerializedName("id") val id: Double?,
        @SerializedName("image_id") val imageId: String?,
        @SerializedName("inscriptions") val inscriptions: String?,
        @SerializedName("internal_department_id") val internalDepartmentId: Double?,
        @SerializedName("is_boosted") val isBoosted: Boolean?,
        @SerializedName("is_on_view") val isOnView: Boolean?,
        @SerializedName("is_public_domain") val isPublicDomain: Boolean?,
        @SerializedName("is_zoomable") val isZoomable: Boolean?,
        @SerializedName("latitude") val latitude: String?,
        @SerializedName("latlon") val latlon: String?,
        @SerializedName("longitude") val longitude: String?,
        @SerializedName("main_reference_number") val mainReferenceNumber: String?,
        @SerializedName("material_id") val materialId: String?,
        @SerializedName("material_ids") val materialIds: List<String>?,
        @SerializedName("material_titles") val materialTitles: List<String>?,
        @SerializedName("max_zoom_window_size") val maxZoomWindowSize: Double?,
        @SerializedName("medium_display") val mediumDisplay: String?,
        @SerializedName("nomisma_id") val nomismaId: String?,
        @SerializedName("on_loan_display") val onLoanDisplay: String?,
        @SerializedName("place_of_origin") val placeOfOrigin: String?,
        @SerializedName("provenance_text") val provenanceText: String?,
        @SerializedName("publication_history") val publicationHistory: String?,
        @SerializedName("publishing_verification_level") val publishingVerificationLevel: String?,
        @SerializedName("section_ids") val sectionIds: List<String>?,
        @SerializedName("section_titles") val sectionTitles: List<String>?,
        @SerializedName("site_ids") val siteIds: List<String>?,
        @SerializedName("sound_ids") val soundIds: List<String>?,
        @SerializedName("source_updated_at") val sourceUpdatedAt: String?,
        @SerializedName("style_id") val styleId: String?,
        @SerializedName("style_ids") val styleIds: List<String>?,
        @SerializedName("style_title") val styleTitle: String?,
        @SerializedName("style_titles") val styleTitles: List<String>?,
        @SerializedName("subject_id") val subjectId: String?,
        @SerializedName("subject_ids") val subjectIds: List<String>?,
        @SerializedName("subject_titles") val subjectTitles: List<String>?,
        @SerializedName("suggest_autocomplete_all") val suggestAutocompleteAllResponse: List<SuggestAutocompleteAllResponse>?,
        @SerializedName("technique_id") val techniqueId: String?,
        @SerializedName("technique_ids") val techniqueIds: List<String>?,
        @SerializedName("technique_titles") val techniqueTitles: List<String>?,
        @SerializedName("term_titles") val termTitles: List<String>?,
        @SerializedName("text_ids") val textIds: List<String>?,
        @SerializedName("theme_titles") val themeTitles: List<String>?,
        @SerializedName("thumbnail") val thumbnailResponse: ThumbnailResponse?,
        @SerializedName("timestamp") val timestamp: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("updated_at") val updatedAt: String?,
        @SerializedName("video_ids") val videoIds: List<String?>
    ) {
        data class ColorResponse(
            @SerializedName("h") val h: Double?,
            @SerializedName("l") val l: Double?,
            @SerializedName("percentage") val percentage: Double?,
            @SerializedName("population") val population: Double?,
            @SerializedName("s") val s: Double?
        )

        data class SuggestAutocompleteAllResponse(
            @SerializedName("contexts") val contextsResponse: ContextsResponse?,
            @SerializedName("input") val input: List<String>?,
            @SerializedName("weight") val weight: Double?
        ) {
            data class ContextsResponse(
                @SerializedName("groupings") val groupings: List<String>?
            )
        }

        data class DimensionsDetailResponse(
            @SerializedName("clarification") val clarification: String?,
            @SerializedName("depth_cm") val depthCm: Double?,
            @SerializedName("depth_in") val depthIn: Double?,
            @SerializedName("diameter_cm") val diameterCm: Double?,
            @SerializedName("diameter_in") val diameterIn: Double?,
            @SerializedName("height_cm") val heightCm: Double?,
            @SerializedName("height_in") val heightIn: Double?,
            @SerializedName("width_cm") val widthCm: Double?,
            @SerializedName("width_in") val widthIn: Double?
        )

        data class ThumbnailResponse(
            @SerializedName("alt_text") val altText: String?,
            @SerializedName("height") val height: Double?,
            @SerializedName("lqip") val lqip: String?,
            @SerializedName("width") val width: Double?
        )
    }

    data class PaginationResponse(
        @SerializedName("total") val total: Double?,
        @SerializedName("limit") val limit: Double?,
        @SerializedName("offset") val offset: Double?,
        @SerializedName("total_pages") val totalPages: Double?,
        @SerializedName("current_page") val currentPage: Int?,
        @SerializedName("prev_url") val prevUrl: String?,
        @SerializedName("next_url") val nextUrl: String?,
    )
    data class InfoResponse(
        @SerializedName("license_text") val licenseText: String?,
        @SerializedName("license_links") val licenseLinks: List<String>?,
        @SerializedName("version") val version: String?,
    )

    data class ConfigResponse(
        @SerializedName("iiif_url") val iiifUrl: String?,
        @SerializedName("website_url") val websiteUrl: String?,
    )
}