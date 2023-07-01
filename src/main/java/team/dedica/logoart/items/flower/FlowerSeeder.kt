package team.dedica.logoart.items.flower

import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder
import team.dedica.logoart.Util
import team.dedica.logoart.items.PlantSegment

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
