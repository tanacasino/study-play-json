package com.example.models

sealed abstract class Gender(val code: String) extends Enum[String]

object Gender extends EnumCompanion[String, Gender] {
  case object Male extends Gender("M")
  case object Female extends Gender("F")

  override def values: Seq[Gender] = Seq(Male, Female)

}

