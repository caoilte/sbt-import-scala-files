
import org.caoilte.V

object Main extends App {
  println("Test is running")
  val test = s"I added version ${V.scalafmt} as a dependency"
  if (V.scalafmt != "0.5.5")
    throw new RuntimeException(s"'${V.scalafmt}' should have been '0.5.5'")
  println(test + " Test succeeded!")
}