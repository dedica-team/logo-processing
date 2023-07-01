package team.dedica.logo.forest.items

import processing.core.PVector

internal class FlowerParams : GrowthParameters {

    override fun getSeed(origin: PVector, scaleFactor: Float): PlantSegment {
        return PlantSegment(
            origin,
            PVector(0f, Util.random(5f, 10f) * -1 * scaleFactor),
            Util.random(5f, MAX_STEM_DIAMETER.toFloat()) * scaleFactor,
            scaleFactor,
            0,
            this
        )
    }

    override fun canStillGrow(plant: PlantSegment): Boolean {
        return plant.diameter > MIN_DIAMETER_TO_GROW
    }

    override fun getVelocityReduction(): Float {
        return VELOCITY_REDUCTION
    }

    /**
     * slight bump into other direction
     *
     * @return bump vector
     */
    override fun bump(): PVector {
        val upwards = -0.5f
        val downwards = 0.5f
        val bump = PVector(Util.random(-1f, 1f), Util.random(upwards, downwards))
        bump.mult(0.2f)
        return bump
    }

    override fun getRandomVelocityFactor(plant: PlantSegment): Float {
        return Util.random(20f, 40f) * plant.scaleFactor
    }

    override fun isWithinBounds(location: PVector): Boolean {
        return location.x > 0 && location.y > 0
    }

    override fun getBranchProbabilityFactor(): Float {
        return BRANCH_PROBABILITY_FACTOR
    }

    override fun getNewDiameterFactor(): Float {
        return Util.random(0.7f, 0.9f)
    }

    override fun canBranch(segment: PlantSegment): Boolean {
        //first segment cannot branch
        return if (segment.iteration < MIN_ITERATION_TO_BRANCH && segment.children.size > 1) false else segment.branchProbability > MIN_BRANCH_PROBABILITY
    }

    override fun createGrowthVector(plant: PlantSegment): PVector {
        val growthVector = PVector(plant.velocity.x, plant.velocity.y)
        growthVector.normalize()
        growthVector.add(bump())
        growthVector.normalize()
        growthVector.mult(getVelocityReduction()) //slower growth
        growthVector.mult(getRandomVelocityFactor(plant) * plant.scaleFactor)
        return growthVector
    }

    companion object {
        const val MIN_DIAMETER_TO_GROW = 0.5
        const val MIN_BRANCH_PROBABILITY = 0.55

        /**
         * branch probability is multiplied with this factor each turn
         * influences how many child branches a segment can have (the higher the more)
         */
        const val BRANCH_PROBABILITY_FACTOR = 0.3f

        /**
         * branch growth velocity reduction per cycle
         */
        const val VELOCITY_REDUCTION = 0.75f
        const val MAX_STEM_DIAMETER = 10
        const val MIN_ITERATION_TO_BRANCH = 6
    }
}
