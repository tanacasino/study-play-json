package com.example.models

import com.example.libs.Prism

abstract class Enum[A] {
  def code: A
}

abstract class EnumCompanion[A, B <: Enum[A]] {
  def values: Seq[B]
  def valueOf(code: A): Option[B] = values.find(_.code == code)

  implicit def enumPrism: Prism[A, B] = new Prism[A, B] {
    override def toOpt(a: A): Option[B] = valueOf(a)
    override def from(b: B): A = b.code
  }

}
