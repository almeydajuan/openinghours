package com.almeydajuan.openinghours

class ActionProvider {
    companion object {
        fun containsAction(actionName: String) = Action.values().map { it.input }.contains(actionName)
        fun parseAction(actionName: String) = Action.values().first { it.input == actionName }.output
    }
}

enum class Action(val input: String, val output: String) {
    OPEN("open", ""),
    CLOSE("close", "-")
}