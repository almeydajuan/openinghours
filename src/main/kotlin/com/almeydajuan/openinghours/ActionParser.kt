package com.almeydajuan.openinghours

class ActionParser {
    fun containsAction(actionName: String) = Action.values().map { it.input }.contains(actionName)
    fun parseAction(actionName: String) = Action.values().first { it.input == actionName }.output
}

private enum class Action(val input: String, val output: String) {
    OPEN("open", "from"),
    CLOSE("close", "to")
}