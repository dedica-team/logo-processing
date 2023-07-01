package team.dedica.logo.scenes.forest

import processing.core.PApplet
import team.dedica.logo.scenes.Scene

/**
 * Tree image generator
 */
class SceneApplet : PApplet() {

    private lateinit var scene: Scene
    override fun settings() {
        size(1920, 1080, SVG, "tree.svg")
    }

    override fun setup() {
        scene = Forest()
        scene.setup(width, height)
    }

    override fun draw() {
        background(40f, 40f, 40f)
        scene.draw(this)
        exit()
    }
}

fun main(args: Array<String>) {
    PApplet.main(SceneApplet::class.java.name, args)
}