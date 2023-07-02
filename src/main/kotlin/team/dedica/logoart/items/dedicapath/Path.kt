package team.dedica.logoart.items.dedicapath

import processing.core.PApplet
import processing.core.PConstants.*
import team.dedica.logoart.items.Drawable
import java.lang.IllegalStateException

private data class Coords(
    var x: Float,
    var y: Float,
    var direction: Direction,
)

const val CMD_FORWARD = 'f'
const val CMD_LEFT = 'l'
const val CMD_RIGHT = 'r'

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}


class Path(
    private val x: Float,
    private val y: Float,
    private val commands: List<Char>,
    private val direction: Direction = Direction.UP,
    private val increment: Float = 20f,
    private val bloom: Boolean = true,
) : Drawable {

    private val radius: Float = increment

    companion object {

    }

    override fun draw(applet: PApplet) {

        applet.noFill()
        applet.stroke(255f, 100f, 0f, 255f) //orange
        applet.strokeWeight(increment / 2)
        applet.ellipseMode(CENTER) // https://processing.org/reference/ellipseMode_.html
        var origin = Coords(x, y, direction)

        for (command in commands) {
            origin = this.nextCoordinates(origin, command, applet)
        }

        if (bloom) {
            drawBloom(origin, applet)
        }
    }

    private fun nextCoordinates(lastCoords: Coords, command: Char, applet: PApplet): Coords {

        return when (command) {

            CMD_FORWARD -> forward(lastCoords, applet)

            CMD_LEFT -> turnLeft(lastCoords, applet)

            CMD_RIGHT -> turnRight(lastCoords, applet)

            else -> error("Illegal command $command")
        }
    }

    private fun forward(lastCoords: Coords, applet: PApplet): Coords {

        if (lastCoords.direction == Direction.UP) {
            applet.line(lastCoords.x, lastCoords.y, lastCoords.x, lastCoords.y - increment)
            return Coords(lastCoords.x, lastCoords.y - increment, Direction.UP)
        }

        if (lastCoords.direction == Direction.DOWN) {
            applet.line(lastCoords.x, lastCoords.y, lastCoords.x, lastCoords.y + increment)
            return Coords(lastCoords.x, lastCoords.y + increment, Direction.DOWN)
        }

        if (lastCoords.direction == Direction.LEFT) {
            applet.line(lastCoords.x, lastCoords.y, lastCoords.x - increment, lastCoords.y)
            return Coords(lastCoords.x - increment, lastCoords.y, Direction.LEFT)
        }

        if (lastCoords.direction == Direction.RIGHT) {
            applet.line(lastCoords.x, lastCoords.y, lastCoords.x + increment, lastCoords.y)
            return Coords(lastCoords.x + increment, lastCoords.y, Direction.RIGHT)
        }

        throw IllegalStateException()
    }

    private fun turnLeft(lastCoords: Coords, pApplet: PApplet): Coords {

        if (lastCoords.direction == Direction.UP) {
            drawArc(pApplet, lastCoords.x - radius, lastCoords.y, 1.5f * PI)
            return Coords(lastCoords.x - increment, lastCoords.y - increment, Direction.LEFT)
        }

        if (lastCoords.direction == Direction.DOWN) {
            drawArc(pApplet, lastCoords.x + radius, lastCoords.y, HALF_PI)
            return Coords(lastCoords.x + increment, lastCoords.y + increment, Direction.RIGHT)
        }

        if (lastCoords.direction == Direction.LEFT) {
            drawArc(pApplet, lastCoords.x, lastCoords.y + radius, PI)
            return Coords(lastCoords.x - increment, lastCoords.y + increment, Direction.DOWN)
        }

        if (lastCoords.direction == Direction.RIGHT) {
            drawArc(pApplet, lastCoords.x, lastCoords.y - radius, 0 * PI)
            return Coords(lastCoords.x + increment, lastCoords.y - increment, Direction.UP)
        }

        throw IllegalStateException()
    }


    private fun turnRight(lastCoords: Coords, applet: PApplet): Coords {

        if (lastCoords.direction == Direction.UP) {
            drawArc(applet, lastCoords.x + radius, lastCoords.y, PI)
            return Coords(lastCoords.x + increment, lastCoords.y - increment, Direction.RIGHT)
        }

        if (lastCoords.direction == Direction.DOWN) {
            drawArc(applet, lastCoords.x - radius, lastCoords.y, 0 * PI)
            return Coords(lastCoords.x - increment, lastCoords.y + increment, Direction.LEFT)
        }

        if (lastCoords.direction == Direction.LEFT) {
            drawArc(applet, lastCoords.x, lastCoords.y - radius, HALF_PI)
            return Coords(lastCoords.x - increment, lastCoords.y - increment, Direction.UP)
        }

        if (lastCoords.direction == Direction.RIGHT) {
            drawArc(applet, lastCoords.x, lastCoords.y + radius, 1.5f * PI)
            return Coords(lastCoords.x + increment, lastCoords.y + increment, Direction.DOWN)
        }

        throw IllegalStateException()
    }

    /**
     * Always draws clockwise!
     */
    private fun drawArc(applet: PApplet, x: Float, y: Float, fromRadians: Float) {
        applet.arc(x, y, increment * 2, increment * 2, fromRadians, fromRadians + HALF_PI)
    }

    private fun drawBloom(origin: Coords, applet: PApplet) {
        applet.noStroke()
        applet.fill(255f, 255f, 255f, 255f) //white
        applet.circle(origin.x, origin.y, 2 * increment)
        applet.fill(255f, 100f, 0f, 255f) //orange
        applet.circle(origin.x, origin.y, increment)
    }

}