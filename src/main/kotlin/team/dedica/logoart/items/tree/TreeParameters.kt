package team.dedica.logoart.items.tree

import processing.core.PVector
import team.dedica.logoart.Util
import team.dedica.logoart.items.GrowthParameters
import team.dedica.logoart.items.PlantSegment

internal class TreeParameters : GrowthParameters {

    override fun canStillGrow(plant: PlantSegment): Boolean {
        return plant.diameter > MIN_DIAMETER_TO_GROW
    }

    /**
     * slight bump into other direction
     *
     * @return bump vector
     */
    override fun bump(): PVector {
        val xDirection = 1.95f
        val yDirection = 0.95f
        val bump = PVector(Util.random(-xDirection, xDirection), Util.random(-yDirection, yDirection))
        bump.mult(0.6f)
        return bump
    }

    override fun getRandomVelocityFactor(plant: PlantSegment): Float {
        return Util.random(30f, 40f) * plant.scaleFactor
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
        const val velocityReduction = 0.65f
        const val MAX_STEM_DIAMETER = 20
        const val MIN_ITERATION_TO_BRANCH = 6
    }
}
