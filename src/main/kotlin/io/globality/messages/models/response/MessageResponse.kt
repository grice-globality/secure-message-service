package io.globality.messages.models.response

import io.globality.messages.models.db.Message

data class MessageResponse(val content: String, val ownerName: String, val ownerId: Long, val private: Boolean = false) {
    companion object {
        fun fromMessage(message: Message): MessageResponse = MessageResponse(
            content=message.content,
            ownerName=message.owner.name,
            ownerId=message.owner.id,
            private=message.private)
    }
}
