package team.dedica.logo.items

import processing.core.PVector

/**
 * PlantSegment growth
 */
internal interface GrowthParameters {

    fun getVelocityReduction(): Float
    fun getBranchProbabilityFactor(): Float
    fun getNewDiameterFactor(): Float
    fun canStillGrow(plant: PlantSegment): Boolean

    /**
     * slight bump into other direction
     * @return bump vector
     */
    fun bump(): PVector
    fun getRandomVelocityFactor(plant: PlantSegment): Float
    fun isWithinBounds(location: PVector): Boolean

    fun createGrowthVector(plant: PlantSegment): PVector

    /**
     * @param segment given segment
     * @return true if it can have child branches
     */
    fun canBranch(segment: PlantSegment): Boolean
}
