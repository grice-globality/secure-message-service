package io.globality.messages

import io.globality.messages.models.db.Owner
import org.springframework.data.repository.CrudRepository

interface OwnerRepository: CrudRepository<Owner, Long> {
    fun findByName(name: String): Owner
    fun existsByName(name: String): Boolean
}