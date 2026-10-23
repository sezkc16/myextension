package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class KatDramaProvider : MainAPI() {
    override var mainUrl = "https://new.katdrama.my"
    override var name = "KatDrama"
    override var supportedTypes = setOf(TvType.AsianDrama)

    override var hasMainPage = true

    // 1. Fetch titles from homepage
    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val document = app.get(mainUrl).document
        val items = document.select("a[href*=/series/], a[href*=/drama/]").mapNotNull { element ->
            val title = element.text().trim()
            val href = element.attr("href")
            val poster = element.select("img").attr("src")
            
            if (title.isEmpty()) return@mapNotNull null
            newAnimeSearchResponse(title, href, TvType.AsianDrama) {
                this.posterUrl = poster
            }
        }
        return newHomePageResponse(request.name, items)
    }

    // 2. Search functionality
    override suspend fun search(query: String): List<SearchResponse> {
        val document = app.get("$mainUrl/?s=$query").document
        return document.select("a[href*=/series/], a[href*=/drama/]").mapNotNull { element ->
            val title = element.text().trim()
            val href = element.attr("href")
            if (title.isEmpty()) return@mapNotNull null
            newAnimeSearchResponse(title, href, TvType.AsianDrama)
        }
    }
}

