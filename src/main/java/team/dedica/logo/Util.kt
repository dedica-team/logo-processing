package team.dedica.logo

import java.util.*

internal object Util {

    private val r = Random()

    fun random(min: Float, max: Float): Float {
        return min + r.nextFloat() * (max - min)
    }
}
