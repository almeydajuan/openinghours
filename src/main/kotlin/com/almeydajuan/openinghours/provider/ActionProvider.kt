package com.almeydajuan.openinghours.provider

class ActionProvider {
    companion object {
        fun containsAction(actionName: String) = Action.values().map { it.input }.contains(actionName)
    }
}

enum class Action(val input: String) {
    OPEN("open"),
    CLOSE("close")
}