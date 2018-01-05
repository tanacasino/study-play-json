package com.example.libs

trait Prism[A, B] {
  def toOpt(a: A): Option[B]
  def from(b: B): A
}
