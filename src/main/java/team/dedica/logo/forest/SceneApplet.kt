package team.dedica.logo.forest

import processing.core.PApplet
import team.dedica.logo.forest.items.Forest

/**
 * Tree image generator
 */
class SceneApplet : PApplet() {

    private lateinit var forest: Forest
    override fun settings() {
        size(1920, 1080, SVG, "tree.svg")
    }

    override fun setup() {
        forest = Forest(width, height)
        forest.setup()
    }

    override fun draw() {
        background(40f, 40f, 40f)
        forest.draw(this)
        exit()
    }
}

fun main(args: Array<String>) {
    PApplet.main(SceneApplet::class.java.name, args)
}