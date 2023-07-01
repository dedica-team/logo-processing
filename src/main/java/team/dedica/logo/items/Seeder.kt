package team.dedica.logo.items

import processing.core.PVector
import team.dedica.logo.items.Drawable

interface Seeder {
    fun getSeed(origin: PVector, scaleRatio: Float): Drawable
}
