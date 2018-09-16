package com.sudansh.player.util

import com.sudansh.player.data.AppExecutors
import java.util.concurrent.Executor

class InstantAppExecutors : AppExecutors(instant, instant) {
    companion object {
        private val instant = Executor { it.run() }
    }
}