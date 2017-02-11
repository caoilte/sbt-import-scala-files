
import org.caoilte.MetaBuildConstants

object Main extends App {
  println("Test is running")
  val test = s"hello ${MetaBuildConstants.property}"
  if (test != "hello world")
    throw new RuntimeException(s"'$test' should have been 'hello world'")
  println("Test succeeded")
}