package io.globality.messages

import io.globality.messages.models.db.Message
import io.globality.messages.models.db.Owner
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MessageAndOwnerDataLoader(var messageRepository: MessageRepository, var ownerRepository: OwnerRepository) {

    @PostConstruct
    fun loadData() {
        ownerRepository.save(defaultOwner)
        messageRepository.saveAll(listOf(
            Message(content="First Ever Message!", owner=defaultOwner)
        ))
    }

    companion object {
        val defaultOwner: Owner = Owner(name="default")
    }

}