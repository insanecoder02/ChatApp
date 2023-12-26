package com.example.chatapp.ArrayList

import java.sql.Timestamp

class Message {
    var message: String? = null
    var uid: String? = null
    var time: Long? = null

    constructor()
    constructor(message: String?, uid: String?, time: Long?) {
        this.message = message
        this.uid = uid
        this.time = time
    }
}