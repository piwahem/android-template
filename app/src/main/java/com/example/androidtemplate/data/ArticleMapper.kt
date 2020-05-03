package com.example.androidtemplate.data

import com.example.androidtemplate.data.local.ArticleLocalDTO
import com.example.androidtemplate.data.remote.ArticleNetworkResponse
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.extension.mapList

class ArticleMapper {

    val mapNetworkToEntities: (ArticleNetworkResponse) -> List<ArticleDomainEntity> = {
        response ->
        mapList(response.articles, mapNetworkToEntity)
    }

    val mapNetworkToLocals: (ArticleNetworkResponse) -> List<ArticleLocalDTO> = {
        response ->
        mapList(response.articles, mapNetworkToLocal)
    }

    val mapLocalToEntities: (List<ArticleLocalDTO>) -> List<ArticleDomainEntity> = {
        mapList(it, mapLocalToEntity)
    }

    val mapEntitiesToLocal: (List<ArticleDomainEntity>) -> List<ArticleLocalDTO> = {
        mapList(it, mapEntityToLocal)
    }

    val mapNetworkToEntity: (ArticleNetworkResponse.Article) -> ArticleDomainEntity = {
            networkDTO ->
        ArticleDomainEntity(
            networkDTO.title,
            networkDTO.author?:"EMPTY_AUTHOR",
            networkDTO.publishedAt,
            networkDTO.urlToImage?:"",
            networkDTO.url,
            networkDTO.source.id?:"EMPTY_SOURCE_ID",
            false
        )
    }

    val mapNetworkToLocal: (ArticleNetworkResponse.Article) -> ArticleLocalDTO = {
        networkDTO ->
        ArticleLocalDTO(
            networkDTO.title,
            networkDTO.author?:"EMPTY_AUTHOR",
            networkDTO.publishedAt,
            networkDTO.urlToImage?:"",
            networkDTO.url,
            networkDTO.source.id?:"EMPTY_SOURCE_ID",
            false
        )
    }

    val mapLocalToEntity: (ArticleLocalDTO) -> ArticleDomainEntity = {
        localDTO ->
        ArticleDomainEntity(
            localDTO.title,
            localDTO.author,
            localDTO.publishedAt,
            localDTO.urlToImage,
            localDTO.url,
            localDTO.sourceId,
            false
        )
    }

    val mapEntityToLocal: (ArticleDomainEntity) -> ArticleLocalDTO = {
            entity ->
        ArticleLocalDTO(
            entity.title,
            entity.author,
            entity.publishedAt,
            entity.urlToImage,
            entity.url,
            entity.sourceId,
            false
        )
    }
}