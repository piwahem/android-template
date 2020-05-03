package com.example.androidtemplate.data

import com.example.androidtemplate.data.local.SourceLocalDTO
import com.example.androidtemplate.data.remote.SourceNetworkResponse
import com.example.androidtemplate.domain.entity.SourceDomainEntity
import com.example.androidtemplate.extension.mapList

class SourceMapper {

    val mapNetworkToEntities: (SourceNetworkResponse) -> List<SourceDomainEntity> = {
            response ->
        mapList(response.sources, mapNetworkToEntity)
    }

    val mapNetworkToLocals: (SourceNetworkResponse) -> List<SourceLocalDTO> = {
            response ->
        mapList(response.sources, mapNetworkToLocal)
    }

    val mapLocalToEntities: (List<SourceLocalDTO>) -> List<SourceDomainEntity> = {
        mapList(it, mapLocalToEntity)
    }

    val mapEntitiesToLocal: (List<SourceDomainEntity>) -> List<SourceLocalDTO> = {
        mapList(it, mapEntityToLocal)
    }

    val mapNetworkToEntity: (SourceNetworkResponse.Source) -> SourceDomainEntity = {
            networkDTO ->
        SourceDomainEntity(
            networkDTO.category,
            networkDTO.country,
            networkDTO.language,
            networkDTO.id,
            networkDTO.name
        )
    }

    val mapNetworkToLocal: (SourceNetworkResponse.Source) -> SourceLocalDTO = {
            networkDTO ->
        SourceLocalDTO(
            networkDTO.category,
            networkDTO.country,
            networkDTO.language,
            networkDTO.id,
            networkDTO.name
        )
    }

    val mapLocalToEntity: (SourceLocalDTO) -> SourceDomainEntity = {
            localDTO ->
        SourceDomainEntity(
            localDTO.category,
            localDTO.country,
            localDTO.language,
            localDTO.id,
            localDTO.name
        )
    }

    val mapEntityToLocal: (SourceDomainEntity) -> SourceLocalDTO = {
            entity ->
        SourceLocalDTO(
            entity.category,
            entity.country,
            entity.language,
            entity.id,
            entity.name
        )
    }
}