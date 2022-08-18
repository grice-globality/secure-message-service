package io.globality.messages.models.response

import io.globality.messages.models.db.Owner

data class OwnerResponse(val id: Long, val name: String) {
    companion object {
        fun fromOwner(owner: Owner): OwnerResponse = OwnerResponse(id = owner.id, name = owner.name)
    }
}
