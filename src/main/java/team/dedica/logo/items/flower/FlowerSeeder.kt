package team.dedica.logo.items.flower

import processing.core.PVector
import team.dedica.logo.items.Drawable
import team.dedica.logo.items.Seeder
import team.dedica.logo.Util
import team.dedica.logo.items.PlantSegment

class FlowerSeeder : Seeder {
    override fun getSeed(origin: PVector, scaleRatio: Float): Drawable {
        return PlantSegment(
            origin,
            PVector(0f, Util.random(5f, 10f) * -1 * scaleRatio),
            Util.random(5f, FlowerParams.MAX_STEM_DIAMETER.toFloat()) * scaleRatio,
            scaleRatio,
            0,
            FlowerParams()
        )
    }
}
