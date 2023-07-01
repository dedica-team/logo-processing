package team.dedica.logoart.items.tree

import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder
import team.dedica.logoart.Util
import team.dedica.logoart.items.PlantSegment

class TreeSeeder : Seeder {

    override fun getSeed(origin: PVector, scaleRatio: Float): Drawable {
        return PlantSegment(
            origin,
            PVector(0f, Util.random(9f, 12f) * -1 * scaleRatio),
            Util.random(15f, TreeParameters.MAX_STEM_DIAMETER.toFloat()) * scaleRatio,
            scaleRatio,
            0,
            TreeParameters()
        )
    }
}