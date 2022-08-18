package io.globality.messages

import io.globality.messages.models.db.Message
import org.springframework.data.repository.CrudRepository

interface MessageRepository: CrudRepository<Message, Long> {
    fun findByOwner(id: Long): List<Message>
}