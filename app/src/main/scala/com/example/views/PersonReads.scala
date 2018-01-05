package com.example.views

import com.example.models.{Person, PersonAge, PersonName}
import play.api.libs.json.{Json, Reads}
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._

object PersonReads {

  import BasicReads._

  final implicit val personNameReads: Reads[PersonName] =
    (minLength[String](PersonName.MinLength) andKeep maxLength[String](PersonName.MaxLength)).map(PersonName)

  final implicit val personAgeReads: Reads[PersonAge] =
    (min[Int](PersonAge.Min) andKeep max[Int](PersonAge.Max)).map(PersonAge)

  final implicit val personReads: Reads[Person] = Json.reads[Person]

}
