package team.dedica.logoart.items

import processing.core.PApplet
import processing.core.PVector
import team.dedica.logoart.Util

internal class PlantSegment(
    private val origin: PVector,
    val velocity: PVector,
    val diameter: Float,
    val scaleFactor: Float,
    val iteration: Int,
    private val growthParameters: GrowthParameters
) : Drawable {

    private lateinit var targetLocation: PVector
    private var isFinished = false
    private var bloom = false

    //paint only once
    private var hasDrawnTwig = false
    private var hasDrawnLeave = false

    /**
     * Probability of branching, decreased with every iteration
     */
    var branchProbability = 1f

    /**
     * child branches
     */
    var children: MutableList<PlantSegment> = ArrayList()
    private var isBranchingFinished = false

    init {
        grow()
        branch()
    }

    /**
     * computes the growing destination
     */
    private fun grow() {

        targetLocation = PVector(origin.x, origin.y)
        if (!growthParameters.isWithinBounds(targetLocation)) {
            //out of bounds
            isFinished = true
            return
        }

        if (growthParameters.canStillGrow(this)) {
            targetLocation.add(growthParameters.createGrowthVector(this))
        } else {
            //too small
            isFinished = true
            bloom = true
        }
    }

    /**
     * creates child branches
     */
    private fun branch() {
        if (isFinished || isBranchingFinished) return
        while (growthParameters.canBranch(this)) { // control length
            if (Util.random(0f, 1f) < branchProbability) {
                children.add(
                    PlantSegment(
                        PVector(targetLocation.x, targetLocation.y),
                        velocity,
                        diameter * growthParameters.getNewDiameterFactor(),
                        scaleFactor,
                        iteration + 1,
                        growthParameters
                    )
                )
            }
            branchProbability *= growthParameters.getBranchProbabilityFactor()
        }
        isBranchingFinished = true
    }

    override fun draw(applet: PApplet) {
        drawTwig(applet)
        drawLeave(applet)
    }

    /**
     * recursive drawing of children
     */
    private fun drawTwig(applet: PApplet) {
        for (i in children.indices) {
            children[i].drawTwig(applet)
        }
        if (hasDrawnTwig) {
            return
        }
        applet.stroke(255f, 100f, 0f, 255f) //orange
        applet.strokeWeight(diameter)
        applet.line(origin.x, origin.y, targetLocation.x, targetLocation.y)
        hasDrawnTwig = true
    }

    private fun drawLeave(applet: PApplet) {
        for (i in children.indices) {
            children[i].drawLeave(applet)
        }
        if (hasDrawnLeave) {
            return
        }
        if (isFinished && bloom) {
            applet.noStroke()
            val alpha = 0 + 255 * scaleFactor
            val alphaBloom = 155 + 100 * scaleFactor
            applet.fill(255f, 255f, 255f, alphaBloom)
            applet.ellipse(targetLocation.x, targetLocation.y, 15 * scaleFactor, 15 * scaleFactor)
            //orange
            applet.fill(255f, 100f, 0f, alphaBloom)
            applet.ellipse(targetLocation.x, targetLocation.y, 7 * scaleFactor, 7 * scaleFactor)
            //orange
            applet.stroke(255f, 100f, 0f, alpha)
        }
        hasDrawnLeave = true
    }
}