package com.flepper.therapeutic.data.repositories

import com.flepper.therapeutic.data.models.FeaturedContent
import com.flepper.therapeutic.data.models.WorldWideEvent
import com.flepper.therapeutic.data.models.WorldWideEventDTO
import kotlinx.coroutines.flow.Flow


/**
 This repository hosts calls that are generic and can be accessed at different layers at the presentation level*/
interface EventsRepository {
    suspend fun getFeaturedContent(): Flow<List<FeaturedContent>>
    suspend fun getWorldEvents():Flow<List<WorldWideEventDTO>>
    suspend fun saveWorldWideEvent(worldWideEvent: WorldWideEvent)
    suspend fun getWorldEvent(eventId:String):Flow<WorldWideEvent>
}