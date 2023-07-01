package team.dedica.logoart.scenes

import team.dedica.logoart.scenes.forest.SceneApplet

interface Scene {
    fun setup(width: Int, height: Int)
    fun draw(sceneApplet: SceneApplet)

    fun getHorizonHeightFactor(): Float
}