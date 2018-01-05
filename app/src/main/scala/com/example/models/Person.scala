package com.example.models

case class PersonName(value: String) extends AnyVal with ValueClass[String]

object PersonName extends (String => PersonName) {
  final val MinLength = 1
  final val MaxLength = 100
}

case class PersonAge(value: Int) extends AnyVal with ValueClass[Int]

object PersonAge extends (Int => PersonAge) {
  final val Min = 0
  final val Max = 150
}


case class Person(
  personId: Id[Person],
  name: PersonName,
  age: PersonAge,
  gender: Gender,
  note: Option[String] = None
)

