package team.dedica.logoart.items.dedicapath

import processing.core.PVector
import team.dedica.logoart.items.Drawable
import team.dedica.logoart.items.Seeder

class PathSeeder(val commands: List<Char>) : Seeder {
    override fun getSeed(origin: PVector, scaleRatio: Float): Drawable {
        return Path(
            origin.x,
            origin.y,
            commands,
        )
    }
}
