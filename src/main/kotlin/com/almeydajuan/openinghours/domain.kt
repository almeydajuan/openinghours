package com.almeydajuan.openinghours

import com.almeydajuan.openinghours.provider.Action

typealias RestaurantTransitions = List<Transition>

fun RestaurantTransitions.areOpeningAndClosingTimesInConsistentOrder() =
    this.zipWithNext().filter { it.first.isOpen() && it.second.isClose() }.size != this.size / 2

fun RestaurantTransitions.isAmountOfOpeningAndClosingActionsConsistent() =
    this.count { it.isOpen() } != this.count { it.isClose() }

fun RestaurantTransitions.areSorted(): Boolean =
    this.map { it.timestamp }.sorted() == this.map { it.timestamp }

data class Transition(val action: String, val timestamp: Long) {
    fun isOpen() = action == Action.OPEN.input
    fun isClose() = action == Action.CLOSE.input
}

data class WorkingDay(val day: String, val transitions: RestaurantTransitions) {
    fun isComplete() = transitions.isNotEmpty() && transitions.last().action == Action.CLOSE.input
}

typealias WorkingWeek = List<WorkingDay>

fun WorkingWeek.areDaysRepeated() = this.map { it.day }.toSet().size != this.size