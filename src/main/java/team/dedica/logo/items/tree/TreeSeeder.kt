package team.dedica.logo.items.tree

import processing.core.PVector
import team.dedica.logo.items.Drawable
import team.dedica.logo.items.Seeder
import team.dedica.logo.Util
import team.dedica.logo.items.PlantSegment

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