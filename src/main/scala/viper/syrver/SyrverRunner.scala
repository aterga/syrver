package viper.syrver

import viper.server.vsi.{Envelope, VerificationServer}



case class VyperEnvelope(s: String) extends Envelope


object SyrverRunner extends VerificationServer {

  println(">>> Creating instance of SyrverRunner ...")

  type A = String

  def main(args: Array[String]): Unit = {
    println(">>> SyrverRunner.main")

    start(3)
    stop()
    0
  }

  def run1(psyrver: PythonSyrver, input: String): Unit = {
    println(">>> SyrverRunner.run1")

    psyrver.runVyper(input)
  }

  def run2(psyrver: PythonSyrver, input: String): Unit = {
    println(">>> SyrverRunner.run2")

    start(1)

    psyrver.runVyper(input)
  }

  def run3(psyrver: PythonSyrver, input: String): Unit = {
    println(">>> SyrverRunner.run3")

    start(2)

    psyrver.runVyper(input)
  }

  def run4(psyrver: PythonSyrver, input: String): Unit = {
    println(">>> SyrverRunner.run4")

    start(3)

    psyrver.runVyper(input)
  }

  def run5(psyrver: PythonSyrver, input: String): Unit = {
    println(">>> SyrverRunner.run5")

    start(1)
    stop()

    psyrver.runVyper(input)
  }

  def pack(s: String): Envelope = VyperEnvelope(s)

  def unpack(e: Envelope): String = e.asInstanceOf[VyperEnvelope].s
}