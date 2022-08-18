package io.globality.messages.models.db

import com.fasterxml.jackson.annotation.JsonManagedReference
import javax.persistence.*

@Entity
data class Owner(@Id @GeneratedValue(strategy = GenerationType.AUTO)
                 val id: Long = 0,
                 @Column(unique=true)
                 val name: String,
                 @JsonManagedReference
                 @OneToMany(cascade = [CascadeType.ALL], fetch= FetchType.LAZY, mappedBy= "owner")
                 var messages: List<Message>? = emptyList()
                 )
