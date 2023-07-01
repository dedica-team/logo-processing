package team.dedica.logoart.items.sky

import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder

class SkySeeder : Seeder {
    override fun getSeed(origin: PVector, scaleRatio: Float): Drawable {
        return Sky(origin.x, origin.y)
    }
}