package com.bsoftware.setara.getuuid

import java.util.UUID

class GetUuid {

    fun getUuidCode() : String{
        return UUID.randomUUID().toString()
    }
}