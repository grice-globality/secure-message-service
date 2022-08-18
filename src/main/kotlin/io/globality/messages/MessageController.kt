package io.globality.messages

import io.globality.messages.models.db.Message
import io.globality.messages.models.db.Owner
import io.globality.messages.models.response.MessageResponse
import io.globality.messages.models.response.OwnerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MessageController(private val messageRepository: MessageRepository, private val ownerRepository: OwnerRepository) {

    @GetMapping("/messages")
    fun getAllMessages(): ResponseEntity<List<MessageResponse>> {
        val messages = messageRepository.findAll().toList().map { message ->
            MessageResponse.fromMessage(message)
        }
        return ResponseEntity(messages, HttpStatus.OK)
    }

    @GetMapping("/owners/{ownerName}/messages")
    fun getOwnerMessages(@PathVariable ownerName: String): ResponseEntity<List<MessageResponse>> {
        if (ownerRepository.existsByName(ownerName)) {
            val ownerMessagesList = ownerRepository.findByName(ownerName).messages ?: emptyList()
            return ResponseEntity(ownerMessagesList.map{ MessageResponse.fromMessage (it)}, HttpStatus.OK)
        } else {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/messages/{id}")
    fun getMessage(@PathVariable id: Long): ResponseEntity<MessageResponse> {
        return if (messageRepository.existsById(id)) {
            val message = messageRepository.findById(id).get()
            ResponseEntity(MessageResponse.fromMessage(message), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/owners")
    fun getMessageOwners(): ResponseEntity<List<OwnerResponse>> {
        val ownersResponse = ownerRepository.findAll().toList().map {owner ->
            OwnerResponse.fromOwner(owner)
        }
        return ResponseEntity(ownersResponse, HttpStatus.OK)
    }

    @PostMapping("/owners")
    fun addOwner(@RequestBody ownerName: String): ResponseEntity<OwnerResponse> {
        val owner = ownerRepository.save(Owner(name = ownerName))
        return ResponseEntity(OwnerResponse(id = owner.id, name = owner.name), HttpStatus.CREATED)
    }

    @GetMapping("/owners/{ownerName}")
    fun getOwnerByName(@PathVariable ownerName: String): ResponseEntity<OwnerResponse> {
        return if (ownerRepository.existsByName(ownerName)) {
            val owner = ownerRepository.findByName(ownerName)
            val ownerResponse = OwnerResponse(name = owner.name, id = owner.id)
            ResponseEntity(ownerResponse, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping("/owners/{ownerName}/messages")
    fun sendMessageToOwnerByName(@RequestBody content: String,
                                 @PathVariable ownerName: String,
                                 @RequestParam private: Boolean = false): ResponseEntity<MessageResponse> {
        return if (ownerRepository.existsByName(ownerName)) {
            val owner = ownerRepository.findByName(ownerName)
            val message = messageRepository.save(Message(content = content, owner = owner, private = private))
            ResponseEntity(
                MessageResponse.fromMessage(message),
                HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


}