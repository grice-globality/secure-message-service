package io.globality.messages.models.response

data class MessageListResponse(val ownerId: Long, val ownerName: String, val messages: List<String>)
