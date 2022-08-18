package io.globality.messages.models.db

import com.fasterxml.jackson.annotation.JsonBackReference
import javax.persistence.*


@Entity
data class Message(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Lob
    val content: String,
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    val owner: Owner,
    val private: Boolean = false,
) {
    // Just returns owner ID, not the whole darn object
    fun getOwnerId(): Long = owner.id
}