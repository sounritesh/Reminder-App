package com.curiositymeetsminds.doreminder

/**
 * definition for the object that will contain sql data to be supplied to the adapter
 */

class TaskData (var taskId: Long = 0, var taskName: String = "", var taskDescription: String = "", var taskType: String = "") {
    override fun toString(): String {
        return """
            taskId: $taskId
            taskName: $taskName
            taskDescription: $taskDescription
            taskType: $taskType
        """.trimIndent()
    }
}