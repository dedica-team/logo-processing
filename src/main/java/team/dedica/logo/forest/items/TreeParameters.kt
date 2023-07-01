package team.dedica.logo.forest.items

import processing.core.PVector

internal class TreeParameters : GrowthParameters {
    override fun getSeed(origin: PVector, scaleRatio: Float): PlantSegment {
        return PlantSegment(
            origin,
            PVector(0f, Util.random(9f, 12f) * -1 * scaleRatio),
            Util.random(15f, MAX_STEM_DIAMETER.toFloat()) * scaleRatio,
            scaleRatio,
            0,
            this
        )
    }

    override fun canStillGrow(plant: PlantSegment): Boolean {
        return plant.diameter > MIN_DIAMETER_TO_GROW
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
        bump.mult(0.5f)
        return bump
    }

    override fun getRandomVelocityFactor(plant: PlantSegment): Float {
        return Util.random(20f, 40f) * plant.scaleFactor
    }

    override fun isWithinBounds(location: PVector): Boolean {
        return location.x > 0 && location.y > 0
    }

    override fun getVelocityReduction(): Float {
        return velocityReduction
    }

    override fun getBranchProbabilityFactor(): Float {
        return branchProbabilityFactor
    }

    override fun getNewDiameterFactor(): Float = Util.random(0.7f, 0.9f)

    override fun canBranch(segment: PlantSegment): Boolean {
        //first segment cannot branch
        return if (segment.iteration < MIN_ITERATION_TO_BRANCH && segment.children.size > 1) false
        else segment.branchProbability > MIN_BRANCH_PROBABILITY
    }

    override fun createGrowthVector(plant: PlantSegment): PVector {
        val growthVector = PVector(plant.velocity.x, plant.velocity.y)
        growthVector.normalize()
        growthVector.add(bump())
        growthVector.normalize()
        growthVector.mult(velocityReduction) //slower growth
        growthVector.mult(getRandomVelocityFactor(plant) * plant.scaleFactor)
        return growthVector
    }

    companion object {
        const val MIN_DIAMETER_TO_GROW = 0.5
        const val MIN_BRANCH_PROBABILITY = 0.25

        /**
         * branch probability is multiplied with this factor each turn
         * influences how many child branches a segment can have (the higher the more)
         */
        const val branchProbabilityFactor = 0.4f

        /**
         * branch growth velocity reduction per cycle
         */
        const val velocityReduction = 0.75f
        const val MAX_STEM_DIAMETER = 20
        const val MIN_ITERATION_TO_BRANCH = 6
    }
}
