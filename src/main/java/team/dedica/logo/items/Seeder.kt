package team.dedica.logo.items

import processing.core.PVector

/**
 * Responsible to initialise a Drawable at a given location.
 */
interface Seeder {
    fun getSeed(origin: PVector, scaleRatio: Float): Drawable
}
