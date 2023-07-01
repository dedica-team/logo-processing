package team.dedica.logoart.items.sun

import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder

const val DIAMETER = 50

class SunSeeder : Seeder {
    override fun getSeed(origin: PVector, scaleRatio: Float): Drawable {
        return Sun(origin.x, origin.y, DIAMETER * scaleRatio)
    }
}