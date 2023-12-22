package com.example.chatapp.ArrayList

class Message {
    var message: String? = null
    var uid: String? = null

    constructor()
    constructor(message: String?, uid: String?) {
        this.message = message
        this.uid = uid
    }
}