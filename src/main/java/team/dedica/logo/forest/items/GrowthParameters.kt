package team.dedica.logo.forest.items

import processing.core.PVector

interface GrowthParameters {

    fun getVelocityReduction(): Float
    fun getBranchProbabilityFactor(): Float
    fun getNewDiameterFactor(): Float
    fun getSeed(origin: PVector, scaleRatio: Float): PlantSegment
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
