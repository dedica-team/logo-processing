package team.dedica.logo.scenes

import team.dedica.logo.scenes.forest.SceneApplet

interface Scene {
    fun setup(width: Int, height: Int)
    fun draw(sceneApplet: SceneApplet)
}